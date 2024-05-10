import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {

    private final List<Card> handPile = new ArrayList<>();


    public List<Card> getHandPile() {
        return Collections.unmodifiableList(handPile);
    }

    /**
     * Returns the first card of the hand pile.
     * @return first element of the hand pile
     */
    public Card popTopCard() {
        return handPile.getFirst();
    }

    /**
     * Adds a card to the bottom of the hand pile.
     * @param card the card that is going to be added
     */
    public void addCard(Card card) {
        handPile.add(card);
    }

    /**
     * Removes a card from the hand pile with a specific index.
     * @param index the index of the card that is going to be removed
     */
    public void removeCard(int index) {
        handPile.remove(index);
    }
}

