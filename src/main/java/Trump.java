public class Trump {
    private final CardSuit suit;
    private final Player caller;
    private final boolean goingAlone;

    public Trump(CardSuit suit, Player caller, boolean goingAlone) {
        this.suit = suit;
        this.caller = caller;
        this.goingAlone = goingAlone;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public Player getCaller() {
        return caller;
    }

    public boolean isGoingAlone() {
        return goingAlone;
    }

    public boolean isTrump(Card card) {
        return getEffectiveSuit(card) == suit;
    }

    public boolean isRightBower(Card card) {
        return card.getValue() == CardValue.JACK && card.getSuit() == suit;
    }

    public boolean isLeftBower(Card card) {
        if (card.getValue() != CardValue.JACK) {
            return false;
        }

        return (suit == CardSuit.HEART && card.getSuit() == CardSuit.DIAMOND) ||
                (suit == CardSuit.DIAMOND && card.getSuit() == CardSuit.HEART) ||
                (suit == CardSuit.CLUB && card.getSuit() == CardSuit.SPADE) ||
                (suit == CardSuit.SPADE && card.getSuit() == CardSuit.CLUB);
    }

    public CardSuit getEffectiveSuit(Card card) {
        if (isLeftBower(card)) {
            return suit;
        }
        return card.getSuit();
    }

    public int compareCards(Card card1, Card card2, CardSuit ledSuit) {
        // Right bower is highest
        if (isRightBower(card1)) return 1;
        if (isRightBower(card2)) return -1;

        // Left bower is second highest
        if (isLeftBower(card1)) return 1;
        if (isLeftBower(card2)) return -1;

        boolean card1IsTrump = isTrump(card1);
        boolean card2IsTrump = isTrump(card2);

        // Trump beats non-trump
        if (card1IsTrump && !card2IsTrump) return 1;
        if (card2IsTrump && !card1IsTrump) return -1;

        // Both trump (but not bowers) - compare by value
        if (card1IsTrump && card2IsTrump) {
            return card1.getValue().ordinal() - card2.getValue().ordinal();
        }

        // Neither trump - led suit wins, or compare values if both led suit
        boolean card1IsLed = card1.getSuit() == ledSuit;
        boolean card2IsLed = card2.getSuit() == ledSuit;

        if (card1IsLed && !card2IsLed) return 1;
        if (card2IsLed && !card1IsLed) return -1;

        // Both same suit - compare values
        return card1.getValue().ordinal() - card2.getValue().ordinal();
    }

    @Override
    public String toString() {
        String aloneText = goingAlone ? " (going alone)" : "";
        return suit + " is trump, called by " + caller.getName() + aloneText;
    }
}
