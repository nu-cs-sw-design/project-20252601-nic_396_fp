import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();


    public Deck() {
        this.shuffle();
    }

    public void shuffle() {
        deck.clear();
        for (CardSuit suit : CardSuit.values()) {
            for (CardValue value : CardValue.values()) {
                deck.add(new Card(value, suit));
            }
        }

        Collections.shuffle(deck);
    }

    public Card draw() {
        return deck.remove(0);
    }
}
