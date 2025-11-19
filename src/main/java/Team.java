public class Team {
    private Player pOne, pTwo;

    public Team(Player pOne, Player pTwo) {
        this.pOne = pOne;
        this.pTwo = pTwo;
    }

    public Player getPOne() {
        return pOne;
    }

    public Player getPTwo() {
        return pTwo;
    }

    public boolean calledTrump() {
        return pOne.getCalledTrump() || pTwo.getCalledTrump();
    }

    public int tricksWon() {
        return pOne.getTricksWon() + pTwo.getTricksWon();
    }
}
