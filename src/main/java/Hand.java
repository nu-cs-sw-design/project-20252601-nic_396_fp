import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        if (cards.size() >= 5) {
            throw new IllegalStateException("Hand cannot hold more than 5 cards");
        }
        cards.add(card);
    }

    public Card playCard(int index) {
        if (index < 0 || index >= cards.size()) {
            throw new IllegalArgumentException("Invalid card index: " + index);
        }
        return cards.remove(index);
    }

    public boolean removeCard(Card card) {
        return cards.remove(card);
    }

    public Card getCard(int index) {
        if (index < 0 || index >= cards.size()) {
            throw new IllegalArgumentException("Invalid card index: " + index);
        }
        return cards.get(index);
    }

    public int size() {
        return cards.size();
    }


    public void clear() {
        cards.clear();
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public boolean hasSuit(CardSuit suit, CardSuit trump) {
        for (Card card : cards) {
            if (getEffectiveSuit(card, trump) == suit) {
                return true;
            }
        }
        return false;
    }


    private CardSuit getEffectiveSuit(Card card, CardSuit trump) {
        if (card.getValue() == CardValue.JACK && isLeftBower(card, trump)) {
            return trump;
        }
        return card.getSuit();
    }


    private boolean isLeftBower(Card card, CardSuit trump) {
        if (card.getValue() != CardValue.JACK) {
            return false;
        }

        return (trump == CardSuit.HEART && card.getSuit() == CardSuit.DIAMOND) ||
                (trump == CardSuit.DIAMOND && card.getSuit() == CardSuit.HEART) ||
                (trump == CardSuit.CLUB && card.getSuit() == CardSuit.SPADE) ||
                (trump == CardSuit.SPADE && card.getSuit() == CardSuit.CLUB);
    }

    public boolean canPlayCard(Card card, CardSuit ledSuit, CardSuit trump) {
        if (ledSuit == null) {
            return true; // First card of trick, any card is legal
        }

        // If we have the led suit, we must play it
        if (hasSuit(ledSuit, trump)) {
            return getEffectiveSuit(card, trump) == ledSuit;
        }

        // If we don't have the led suit, any card is legal
        return true;
    }




    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            result += i + ": " + card.getValue() + " of " + card.getSuit();
            if (i < cards.size() - 1) {
                result += "\n";
            }
        }
        return result;
    }
}