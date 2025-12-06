public class Player {
    private final String name;
    private int tricksWon;
    private boolean calledTrump;
    private final Hand hand;

    public Player(String name) {
        this.name = name;
        this.tricksWon = 0;
        this.hand = new Hand();
        this.calledTrump = false;
    }

    public void addTrick() {
        this.tricksWon++;
    }

    public void resetTricks() {
        this.tricksWon = 0;
    }

    public void setCalledTrump(boolean calledTrump) {
        this.calledTrump = calledTrump;
    }

    public int getTricksWon() {
        return tricksWon;
    }

    public boolean getCalledTrump() {
        return calledTrump;
    }

    public String getName() {
        return name;
    }

    public Hand getHand() { return hand; }

}
