import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Team teamOne;
    private final Team teamTwo;
    private final List<Player> players;
    private final Deck deck;
    private int teamOneScore;
    private int teamTwoScore;
    private int dealerIndex;
    private Trump currentTrump;
    private static final int WINNING_SCORE = 10;

    public Game(Player pOne, Player pTwo, Player pThree, Player pFour) {
        this.teamOne = new Team(pOne, pThree);
        this.teamTwo = new Team(pTwo, pFour);
        this.players = new ArrayList<>();
        players.add(pOne);
        players.add(pTwo);
        players.add(pThree);
        players.add(pFour);
        this.deck = new Deck();
        this.teamOneScore = 0;
        this.teamTwoScore = 0;
        this.dealerIndex = 0;
    }

    public void startNewRound() {
        for (Player player : players) {
            player.resetTricks();
            player.setCalledTrump(false);
            player.getHand().clear();
        }
        deal();
    }

    public void deal() {
        deck.shuffle();
        int startPlayer = (dealerIndex + 1) % 4;

        for (int i = 0; i < 4; i++) {
            int playerIndex = (startPlayer + i) % 4;
            int cardCount = (i < 2) ? 3 : 2;
            for (int j = 0; j < cardCount; j++) {
                players.get(playerIndex).getHand().addCard(deck.draw());
            }
        }

        for (int i = 0; i < 4; i++) {
            int playerIndex = (startPlayer + i) % 4;
            int cardCount = (i < 2) ? 2 : 3;
            for (int j = 0; j < cardCount; j++) {
                players.get(playerIndex).getHand().addCard(deck.draw());
            }
        }
    }

    public Card drawTurnUpCard() {
        return deck.draw();
    }

    public void setTrump(CardSuit suit, Player caller, boolean goingAlone) {
        caller.setCalledTrump(true);
        this.currentTrump = new Trump(suit, caller, goingAlone);
    }

    public Player playTrick(int startPlayerIndex) {
        Trick trick = new Trick(currentTrump);

        for (int i = 0; i < 4; i++) {
            int playerIndex = (startPlayerIndex + i) % 4;
            Player player = players.get(playerIndex);

            // Skip if partner going alone
            if (currentTrump.isGoingAlone() && isPartner(player, currentTrump.getCaller())
                    && !player.equals(currentTrump.getCaller())) {
                continue;
            }

            Card card = getFirstLegalCard(player.getHand(), trick);
            player.getHand().removeCard(card);
            trick.addPlay(card, player);
        }

        Player winner = trick.getWinner();
        winner.addTrick();
        return winner;
    }

    private Card getFirstLegalCard(Hand hand, Trick trick) {
        for (Card card : hand.getCards()) {
            if (trick.isLegalPlay(card, hand)) {
                return card;
            }
        }
        return hand.getCards().get(0);
    }

    public void updateScore() {
        Team callingTeam = teamOne.calledTrump() ? teamOne : teamTwo;
        int callingTricks = callingTeam.tricksWon();
        int points = 0;

        if (callingTricks >= 3) {
            if (callingTricks == 5) {
                points = currentTrump.isGoingAlone() ? 4 : 2;
            } else {
                points = 1;
            }
            if (teamOne.calledTrump()) {
                teamOneScore += points;
            } else {
                teamTwoScore += points;
            }
        } else {
            // Euchred
            if (teamOne.calledTrump()) {
                teamTwoScore += 2;
            } else {
                teamOneScore += 2;
            }
        }
    }

    public void nextDealer() {
        dealerIndex = (dealerIndex + 1) % 4;
    }

    public boolean isGameOver() {
        return teamOneScore >= WINNING_SCORE || teamTwoScore >= WINNING_SCORE;
    }

    private boolean isPartner(Player p1, Player p2) {
        return (teamOne.getPOne().equals(p1) && teamOne.getPTwo().equals(p2)) ||
                (teamOne.getPTwo().equals(p1) && teamOne.getPOne().equals(p2)) ||
                (teamTwo.getPOne().equals(p1) && teamTwo.getPTwo().equals(p2)) ||
                (teamTwo.getPTwo().equals(p1) && teamTwo.getPOne().equals(p2));
    }

    public Team getTeamOne() { return teamOne; }
    public Team getTeamTwo() { return teamTwo; }
    public List<Player> getPlayers() { return players; }
    public int getTeamOneScore() { return teamOneScore; }
    public int getTeamTwoScore() { return teamTwoScore; }
    public int getDealerIndex() { return dealerIndex; }
    public Player getDealer() { return players.get(dealerIndex); }
    public Trump getCurrentTrump() { return currentTrump; }
    public Team checkWin() {
        if (teamOneScore >= WINNING_SCORE) return teamOne;
        if (teamTwoScore >= WINNING_SCORE) return teamTwo;
        return null;
    }
}