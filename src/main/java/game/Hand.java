package game;

import game.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class Hand {

    private final List<Card> handPile = new ArrayList<>();


    public List<Card> getHandPile() {
        return Collections.unmodifiableList(handPile);
    }

    /**
     * Returns the first card of the hand pile.
     * @return  first element of the hand pile
     */
    public Card popTopCard() {
        if (handPile.isEmpty()) throw new NoSuchElementException("Hand pile is empty.");
        return handPile.getFirst();
    }

    /**
     * Adds a card to the bottom of the hand pile.
     * @param card the card that is going to be added
     */
    public void addCard(final Card card) {
        handPile.add(card);
    }

    /**
     * Removes a card from the hand pile with a specific index.
     * @param index the index of the card that is going to be removed
     */
    public void removeCard(final int index) {
        handPile.remove(index);
    }

    /**
     * Removes a specific card from the hand pile.
     * @param card the card that should be removed
     */
    public void removeCard(final Card card) {
        handPile.remove(card);
    }
}

