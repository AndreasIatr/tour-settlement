import java.math.BigDecimal;

class Player implements Participant {

    private final String name;
    public String getName() {
        return name;
    }

    private final int score;
    public int getScore() {
        return score;
    }

    private BigDecimal prize;
    public BigDecimal getPrize() {
        return prize;
    }
    public void setPrize(BigDecimal prize) {
        this.prize = prize;
    }


    Player(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
