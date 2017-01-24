import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

class PrizeAllocatorImpl implements PrizeAllocator {

    public void awardPrizes(List<Participant> participants, Map<Integer, BigDecimal> prizes) {
        // assume original map should/could not be mutated
        prizes = new HashMap<>(prizes);

        TreeMap<Integer, List<Participant>> participantsByScoreSorted = getParticipantsByScoreSortedDesc(participants);
        if (participantsByScoreSorted.isEmpty()) {
            awardPrizesToClearWinners(participants, prizes);
        } else {
            int topmostPosition = 1;
            for (List<Participant> sameScoreParticipants : participantsByScoreSorted.values()) {
                    BigDecimal actualPrize = getPrizesToShare(prizes, sameScoreParticipants, topmostPosition).stream()
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);// TODO: 24/1/2017  move reduce to method?

                for (Participant participant : sameScoreParticipants) {
                    participant.setPrize(actualPrize.divide(BigDecimal.valueOf(sameScoreParticipants.size()), 2, RoundingMode.FLOOR)); // TODO: 24/1/2017 move division to method?
                    topmostPosition++;
                }
            }
        }
    }

    private void awardPrizesToClearWinners(List<Participant> participants, Map<Integer, BigDecimal> prizes) {
        for (int i = 0; i < participants.size(); i++) {
            participants.get(i).setPrize(prizes.get(i+1));
        }
    }

    private List<BigDecimal> getPrizesToShare(
            Map<Integer, BigDecimal> prizes,
            List<Participant> sameScoreParticipants,
            int topmostPosition) {

        List<BigDecimal> prizesToShare = new ArrayList<>();
        for (int j = topmostPosition; j < sameScoreParticipants.size() + topmostPosition; j++) {
            prizesToShare.add(prizes.remove(j));
        }
        return prizesToShare;
    }

    // package-private for testing purposes
    TreeMap<Integer, List<Participant>> getParticipantsByScoreSortedDesc(List<Participant> participants) {
        return participants.stream()
                .collect(Collectors.groupingBy(
                        Participant::getScore,
                        () -> new TreeMap<Integer, List<Participant>>(Comparator.comparing(Integer::intValue).reversed()),
                        Collectors.toList()));
    }
}
