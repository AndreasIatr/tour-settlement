import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

class Tournament implements PrizeAllocator {

    private static final int QUOTIENT_SCALE = 2;

    @Override
    public void awardPrizes(List<Participant> participants, Map<Integer, BigDecimal> prizes) {
        // assume original map should/could not be mutated
        prizes = new HashMap<>(prizes);

        TreeMap<Integer, List<Participant>> participantsByScoreSorted = getParticipantsByScoreSortedDesc(participants);

        int topmostPosition = 1;
        for (List<Participant> tiedParticipants : participantsByScoreSorted.values()) {

            BigDecimal sharedPrizePool = getSharedPrizePool(prizes, tiedParticipants, topmostPosition);

            BigDecimal actualPrize =
                    sharedPrizePool.divide(BigDecimal.valueOf(tiedParticipants.size()), QUOTIENT_SCALE, RoundingMode.FLOOR);

            tiedParticipants.forEach(participant -> participant.setPrize(actualPrize));

            // shift position to the next participant in the leader-board
            topmostPosition += tiedParticipants.size();
        }
    }

    private BigDecimal getSharedPrizePool(
            Map<Integer, BigDecimal> prizes,
            List<Participant> tiedParticipants,
            int topmostPosition) {

        List<BigDecimal> prizesToShare = new ArrayList<>();
        for (int i = topmostPosition; i < tiedParticipants.size() + topmostPosition; i++) {
            Optional.ofNullable(prizes.remove(i))
                    .ifPresent(prizesToShare::add);
        }

        return prizesToShare.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private TreeMap<Integer, List<Participant>> getParticipantsByScoreSortedDesc(List<Participant> participants) {
        return participants.stream()
                .collect(Collectors.groupingBy(
                        Participant::getScore,
                        () -> new TreeMap<Integer, List<Participant>>(Comparator.comparing(Integer::intValue).reversed()),
                        Collectors.toList()));
    }
}
