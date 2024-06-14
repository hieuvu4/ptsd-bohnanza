package game;

import game.cards.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pile {
    private final List<Card> cards;
    private final List<Card> discardPile;
    private int timesRefillCards;

    public Pile(GameField gameField) {
        cards = new ArrayList<>();
        discardPile = new ArrayList<>();
        timesRefillCards = 2;

        if(gameField.getExtension()) {
            Card[] alCabohneCards = {new BlaueBohne(), new Brechbohne(), new Feuerbohne(),
                    new Kidneybohne(), new Puffbohne(), new Saubohne(), new Stangenbohne()};
            for(Card card : alCabohneCards) {
                for(int i = 0; i < card.getOverallAmount(); i++) cards.add(card);
            }
        } else {
            Card[] standardCards = {new Gartenbohne(), new RoteBohne(), new Augenbohne(),
                    new Sojabohne(), new Brechbohne(), new Saubohne(), new Feuerbohne(),
                    new BlaueBohne()};

            for(Card card : standardCards) {
                for(int i = 0; i < card.getOverallAmount(); i++) cards.add(card);
            }
        }

        // shuffle cards
        Collections.shuffle(cards);
//        for(bohnanza.Card card : cards) System.out.println(card.getName());

    }

    /**
     * Draws the top card from pile. If the pile is empty and  times to refill cards is 0, the game is over. If the
     * pile is empty and times to refill cards bigger than 0, cards gets refilled with the cards of the discard pile
     * and cards gets shuffled. The times for refilling cards is decreased by 1.
     * @return the first card from the pile
     */
    public Card drawCard() { // impl auto reshuffle
        if(cards.isEmpty() && timesRefillCards == 0) {
            return null; // TODO: game over
        }
        if(cards.isEmpty() && timesRefillCards > 0) {
            cards.addAll(discardPile);
            discardPile.clear();
            Collections.shuffle(cards);
            timesRefillCards--;
        }
        Card drawedCard = cards.getFirst();
        cards.removeFirst();
        return drawedCard;
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<Card> getDiscardPile() {
        return discardPile;
    }
}
