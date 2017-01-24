import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PrizeAllocator {
    void awardPrizes(
            List<Participant> participants,
            Map<Integer, BigDecimal> prizes );
}
