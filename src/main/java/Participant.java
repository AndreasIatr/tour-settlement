import java.math.BigDecimal;

public interface Participant {
    String getName();
    int getScore();
    // Prizes represent money using scale of 2 for cents
    BigDecimal getPrize();
    void setPrize(BigDecimal prize);

}