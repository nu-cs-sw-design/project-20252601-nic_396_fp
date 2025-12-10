import java.util.ArrayList;
import java.util.List;

public class Trick {
    private final List<CardPlay> plays;
    private final CardSuit ledSuit;
    private final Trump trump;


    public static class CardPlay {
        private final Card card;
        private final Player player;

        public CardPlay(Card card, Player player) {
            this.card = card;
            this.player = player;
        }

        public Card getCard() {
            return card;
        }

        public Player getPlayer() {
            return player;
        }
    }

    public Trick(Trump trump) {
        this.plays = new ArrayList<>();
        this.ledSuit = null;
        this.trump = trump;
    }

    private Trick(Trump trump, CardSuit ledSuit, List<CardPlay> plays) {
        this.trump = trump;
        this.ledSuit = ledSuit;
        this.plays = plays;
    }

    public void addPlay(Card card, Player player) {
        if (plays.size() >= 4) {
            throw new IllegalStateException("Trick already has 4 cards");
        }
        plays.add(new CardPlay(card, player));
    }


    public CardSuit getLedSuit() {
        if (plays.isEmpty()) {
            return null;
        }
        // Use effective suit to handle left bower leading
        return trump.getEffectiveSuit(plays.get(0).getCard());
    }

    public boolean isComplete() {
        return plays.size() >= 3;
    }


    public Player getWinner() {
        if (!isComplete()) {
            return null;
        }

        CardPlay winningPlay = plays.get(0);

        for (int i = 1; i < plays.size(); i++) {
            CardPlay currentPlay = plays.get(i);
            if (trump.compareCards(currentPlay.getCard(), winningPlay.getCard(), getLedSuit()) > 0) {
                winningPlay = currentPlay;
            }
        }

        return winningPlay.getPlayer();
    }

    public List<CardPlay> getPlays() {
        return new ArrayList<>(plays);
    }


    public int size() {
        return plays.size();
    }


    public boolean isLegalPlay(Card card, Hand hand) {
        if (plays.isEmpty()) {
            return true; // First card, anything goes
        }

        CardSuit led = getLedSuit();

        // Must follow suit if you have it
        if (hand.hasSuit(led, trump.getSuit())) {
            return trump.getEffectiveSuit(card) == led;
        }

        // If you don't have the led suit, any card is legal
        return true;
    }

    @Override
    public String toString() {
        String result = "Trick (led: " + getLedSuit() + "):\n";
        for (CardPlay play : plays) {
            result += "  " + play.getPlayer().getName() + ": " +
                    play.getCard().getValue() + " of " +
                    play.getCard().getSuit() + "\n";
        }
        if (isComplete()) {
            result += "Winner: " + getWinner().getName();
        }
        return result;
    }
}