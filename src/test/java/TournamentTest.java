import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class TournamentTest {

    private PrizeAllocator tournament = new Tournament();

    @Test
    public void test_clear_winners() {
        Participant participant1 = new Player("participant1", 77);
        Participant participant2 = new Player("participant2", 66);
        Participant participant3 = new Player("participant3", 46);
        Participant participant4 = new Player("participant4", 45);

        List<Participant> participants = Arrays.asList(
            participant1,
            participant2,
            participant3,
            participant4
        );

        HashMap<Integer, BigDecimal> prizes = new HashMap<>();
        prizes.put(1, BigDecimal.valueOf(50));
        prizes.put(2, BigDecimal.valueOf(20));
        prizes.put(3, BigDecimal.valueOf(10));
        tournament.awardPrizes(participants, Collections.unmodifiableMap(prizes));

        assertEquals(BigDecimal.valueOf(5000, 2), participant1.getPrize());
        assertEquals(BigDecimal.valueOf(2000, 2), participant2.getPrize());
        assertEquals(BigDecimal.valueOf(1000, 2), participant3.getPrize());
        assertEquals(BigDecimal.valueOf(0, 2), participant4.getPrize());
    }

    @Test
    public void test_top_two_tie() {
        Participant participant1 = new Player("participant1", 77);
        Participant participant2 = new Player("participant2", 77);
        Participant participant3 = new Player("participant3", 46);
        Participant participant4 = new Player("participant4", 45);

        List<Participant> participants = Arrays.asList(
                participant1,
                participant2,
                participant3,
                participant4
        );

        HashMap<Integer, BigDecimal> prizes = new HashMap<>();
        prizes.put(1, BigDecimal.valueOf(50));
        prizes.put(2, BigDecimal.valueOf(20));
        prizes.put(3, BigDecimal.valueOf(10));
        tournament.awardPrizes(participants, Collections.unmodifiableMap(prizes));

        assertEquals(BigDecimal.valueOf(3500, 2), participant1.getPrize());
        assertEquals(BigDecimal.valueOf(3500, 2), participant2.getPrize());
        assertEquals(BigDecimal.valueOf(1000, 2), participant3.getPrize());
        assertEquals(BigDecimal.valueOf(0, 2), participant4.getPrize());
    }

    @Test
    public void test_middle_two_tie() {
        Participant participant1 = new Player("participant1", 77);
        Participant participant2 = new Player("participant2", 46);
        Participant participant3 = new Player("participant3", 46);
        Participant participant4 = new Player("participant4", 45);

        List<Participant> participants = Arrays.asList(
                participant1,
                participant2,
                participant3,
                participant4
        );

        HashMap<Integer, BigDecimal> prizes = new HashMap<>();
        prizes.put(1, BigDecimal.valueOf(50));
        prizes.put(2, BigDecimal.valueOf(20));
        prizes.put(3, BigDecimal.valueOf(10));
        tournament.awardPrizes(participants, Collections.unmodifiableMap(prizes));

        assertEquals(BigDecimal.valueOf(5000, 2), participant1.getPrize());
        assertEquals(BigDecimal.valueOf(1500, 2), participant2.getPrize());
        assertEquals(BigDecimal.valueOf(1500, 2), participant3.getPrize());
        assertEquals(BigDecimal.valueOf(0, 2), participant4.getPrize());
    }

    @Test
    public void test_three_way_tie() {
        Participant participant1 = new Player("participant1", 77);
        Participant participant2 = new Player("participant2", 45);
        Participant participant3 = new Player("participant3", 45);
        Participant participant4 = new Player("participant4", 45);

        List<Participant> participants = Arrays.asList(
                participant1,
                participant2,
                participant3,
                participant4
        );

        HashMap<Integer, BigDecimal> prizes = new HashMap<>();
        prizes.put(1, BigDecimal.valueOf(50));
        prizes.put(2, BigDecimal.valueOf(7));
        prizes.put(3, BigDecimal.valueOf(3));
        tournament.awardPrizes(participants, Collections.unmodifiableMap(prizes));

        assertEquals(BigDecimal.valueOf(5000, 2), participant1.getPrize());
        assertEquals(BigDecimal.valueOf(333, 2), participant2.getPrize());
        assertEquals(BigDecimal.valueOf(333, 2), participant3.getPrize());
        assertEquals(BigDecimal.valueOf(333, 2), participant4.getPrize());
    }

    @Test
    public void test_pairs_tie() {
        Participant participant1 = new Player("participant1", 77);
        Participant participant2 = new Player("participant2", 77);
        Participant participant4 = new Player("participant4", 46);
        Participant participant3 = new Player("participant3", 46);

        List<Participant> participants = Arrays.asList(
                participant1,
                participant2,
                participant3,
                participant4
        );

        HashMap<Integer, BigDecimal> prizes = new HashMap<>();
        prizes.put(1, BigDecimal.valueOf(50));
        prizes.put(2, BigDecimal.valueOf(20));
        prizes.put(3, BigDecimal.valueOf(10));
        tournament.awardPrizes(participants, Collections.unmodifiableMap(prizes));

        assertEquals(BigDecimal.valueOf(3500, 2), participant1.getPrize());
        assertEquals(BigDecimal.valueOf(3500, 2), participant2.getPrize());
        assertEquals(BigDecimal.valueOf(500, 2), participant3.getPrize());
        assertEquals(BigDecimal.valueOf(500, 2), participant4.getPrize());
    }

    @Test
    public void test_top_and_bottom_pairs_tie_six_participants() {
        Participant participant1 = new Player("participant1", 77);
        Participant participant2 = new Player("participant2", 77);
        Participant participant3 = new Player("participant3", 46);
        Participant participant4 = new Player("participant4", 40);
        Participant participant5 = new Player("participant5", 36);
        Participant participant6 = new Player("participant6", 36);

        List<Participant> participants = Arrays.asList(
                participant1,
                participant2,
                participant3,
                participant4,
                participant5,
                participant6
        );

        HashMap<Integer, BigDecimal> prizes = new HashMap<>();
        prizes.put(1, BigDecimal.valueOf(50));
        prizes.put(2, BigDecimal.valueOf(20));
        prizes.put(3, BigDecimal.valueOf(10));
        prizes.put(4, BigDecimal.valueOf(7));
        prizes.put(5, BigDecimal.valueOf(5));
        tournament.awardPrizes(participants, Collections.unmodifiableMap(prizes));

        assertEquals(BigDecimal.valueOf(3500, 2), participant1.getPrize());
        assertEquals(BigDecimal.valueOf(3500, 2), participant2.getPrize());
        assertEquals(BigDecimal.valueOf(1000, 2), participant3.getPrize());
        assertEquals(BigDecimal.valueOf(700, 2), participant4.getPrize());
        assertEquals(BigDecimal.valueOf(250, 2), participant5.getPrize());
        assertEquals(BigDecimal.valueOf(250, 2), participant6.getPrize());
    }
}