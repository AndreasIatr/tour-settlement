import java.math.BigDecimal;

class Player implements Participant {

    private final String name;
    @Override
    public String getName() {
        return name;
    }

    private int score;
    @Override
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    private BigDecimal prize;
    @Override
    public BigDecimal getPrize() {
        return prize;
    }
    @Override
    public void setPrize(BigDecimal prize) {
        this.prize = prize;
    }

    // score is included in constructor for convenience
    Player(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
