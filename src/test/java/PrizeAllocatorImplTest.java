import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class PrizeAllocatorImplTest {

    private PrizeAllocatorImpl prizeAllocator = new PrizeAllocatorImpl();

    private Participant participant1;
    private Participant participant2;
    private Participant participant3;
    private Participant participant4;

    private List<Participant> participants;

    @Test
    public void test_clear_winners() {
        participant1 = new Player("participant1", 77);
        participant2 = new Player("participant2", 66);
        participant3 = new Player("participant3", 46);
        participant4 = new Player("participant4", 45);

        participants = Arrays.asList(
            participant1,
            participant2,
            participant3,
            participant4
        );

        HashMap<Integer, BigDecimal> prizes = new HashMap<>();
        prizes.put(1, BigDecimal.valueOf(50));
        prizes.put(2, BigDecimal.valueOf(20));
        prizes.put(3, BigDecimal.valueOf(10));
        prizeAllocator.awardPrizes(participants, Collections.unmodifiableMap(prizes));
        assertEquals(BigDecimal.valueOf(5000, 2), participant1.getPrize());
        assertEquals(BigDecimal.valueOf(2000, 2), participant2.getPrize());
        assertEquals(BigDecimal.valueOf(1000, 2), participant3.getPrize());
        assertEquals(BigDecimal.valueOf(0, 2), participant4.getPrize());
    }

    @Test
    public void test_top_two_tie() {
        participant1 = new Player("participant1", 77);
        participant2 = new Player("participant2", 77);
        participant3 = new Player("participant3", 46);
        participant4 = new Player("participant4", 45);

        participants = Arrays.asList(
                participant1,
                participant2,
                participant3,
                participant4
        );

        HashMap<Integer, BigDecimal> prizes = new HashMap<>();
        prizes.put(1, BigDecimal.valueOf(50));
        prizes.put(2, BigDecimal.valueOf(20));
        prizes.put(3, BigDecimal.valueOf(10));
        prizeAllocator.awardPrizes(participants, Collections.unmodifiableMap(prizes));
        assertEquals(BigDecimal.valueOf(3500, 2), participant1.getPrize());
        assertEquals(BigDecimal.valueOf(3500, 2), participant2.getPrize());
        assertEquals(BigDecimal.valueOf(1000, 2), participant3.getPrize());
        assertEquals(BigDecimal.valueOf(0, 2), participant4.getPrize());
    }

    @Test
    public void test_middle_two_tie() {
        participant1 = new Player("participant1", 77);
        participant2 = new Player("participant2", 46);
        participant3 = new Player("participant3", 46);
        participant4 = new Player("participant4", 45);

        participants = Arrays.asList(
                participant1,
                participant2,
                participant3,
                participant4
        );

        HashMap<Integer, BigDecimal> prizes = new HashMap<>();
        prizes.put(1, BigDecimal.valueOf(50));
        prizes.put(2, BigDecimal.valueOf(20));
        prizes.put(3, BigDecimal.valueOf(10));
        prizeAllocator.awardPrizes(participants, Collections.unmodifiableMap(prizes));
        assertEquals(BigDecimal.valueOf(5000, 2), participant1.getPrize());
        assertEquals(BigDecimal.valueOf(1500, 2), participant2.getPrize());
        assertEquals(BigDecimal.valueOf(1500, 2), participant3.getPrize());
        assertEquals(BigDecimal.valueOf(0, 2), participant4.getPrize());
    }

    @Test
    public void test_three_way_tie() {
        participant1 = new Player("participant1", 77);
        participant2 = new Player("participant2", 45);
        participant3 = new Player("participant3", 45);
        participant4 = new Player("participant4", 45);

        participants = Arrays.asList(
                participant1,
                participant2,
                participant3,
                participant4
        );

        HashMap<Integer, BigDecimal> prizes = new HashMap<>();
        prizes.put(1, BigDecimal.valueOf(50));
        prizes.put(2, BigDecimal.valueOf(7));
        prizes.put(3, BigDecimal.valueOf(3));
        prizeAllocator.awardPrizes(participants, Collections.unmodifiableMap(prizes));
        assertEquals(BigDecimal.valueOf(5000, 2), participant1.getPrize());
        assertEquals(BigDecimal.valueOf(333, 2), participant2.getPrize());
        assertEquals(BigDecimal.valueOf(333, 2), participant3.getPrize());
        assertEquals(BigDecimal.valueOf(333, 2), participant4.getPrize());
    }

    @Test
    public void test_pairs_tie() {
        participant1 = new Player("participant1", 77);
        participant2 = new Player("participant2", 77);
        participant4 = new Player("participant4", 46);
        participant3 = new Player("participant3", 46);

        participants = Arrays.asList(
                participant1,
                participant2,
                participant3,
                participant4
        );

        HashMap<Integer, BigDecimal> prizes = new HashMap<>();
        prizes.put(1, BigDecimal.valueOf(50));
        prizes.put(2, BigDecimal.valueOf(20));
        prizes.put(3, BigDecimal.valueOf(10));
        prizeAllocator.awardPrizes(participants, Collections.unmodifiableMap(prizes));
        assertEquals(BigDecimal.valueOf(3500, 2), participant1.getPrize());
        assertEquals(BigDecimal.valueOf(3500, 2), participant2.getPrize());
        assertEquals(BigDecimal.valueOf(500, 2), participant3.getPrize());
        assertEquals(BigDecimal.valueOf(500, 2), participant4.getPrize());
    }

    @Test
    public void test_getParticipantsByScore() {
        participant1 = new Player("participant1", 77);
        participant2 = new Player("participant2", 66);
        participant3 = new Player("participant3", 46);
        participant4 = new Player("participant4", 45);

        // wrapped in ArrayList in order to use List#remove
        participants = new ArrayList<>(Arrays.asList(
                participant1,
                participant2,
                participant3,
                participant4
        ));

        Map<Integer, List<Participant>> sameScoreParticipants = prizeAllocator.getParticipantsByScoreSortedDesc(participants);

        for (List<Participant> participantList : sameScoreParticipants.values()) {
            assertEquals(1, participantList.size());
        }
        participants.remove(participant2);
        participant2 = new Player("participant2", participant4.getScore());
        participants.add(participant2);

        sameScoreParticipants = prizeAllocator.getParticipantsByScoreSortedDesc(participants);

        assertEquals(2, sameScoreParticipants.remove(45).size());
        for (List<Participant> participantList : sameScoreParticipants.values()) {
            assertEquals(1, participantList.size());
        }

    }

}