import game.cards.BlaueBohne;
import game.cards.Brechbohne;
import game.cards.Card;
import game.Hand;
import game.cards.Saubohne;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;


public class HandTest {
    Hand hand;

    @BeforeEach
    public void setUp() {
        hand = new Hand();
    }

    @Test
    public void testPopTopCardNone() {
        Assertions.assertThrows(NoSuchElementException.class, () -> hand.popTopCard());
    }

    @Test
    public void testPopTopCardOne() {
        hand.addCard(new Brechbohne());

        Card card = hand.popTopCard();

        Assertions.assertEquals(new Brechbohne().getName(), card.getName());
    }

    @Test
    public void testPopTopCardMoreThanOne() {
        hand.addCard(new Brechbohne());
        hand.addCard(new Brechbohne());
        hand.addCard(new BlaueBohne());

        Card card = hand.popTopCard();

        Assertions.assertEquals(new Brechbohne().getName(), card.getName());
    }

    @Test
    public void testAddCardOne() {
        hand.addCard(new Brechbohne());

        Assertions.assertEquals(1, hand.getHandPile().size());
    }


    @Test
    public void testAddCardThree() {
        hand.addCard(new Brechbohne());
        hand.addCard(new Brechbohne());
        hand.addCard(new BlaueBohne());

        Assertions.assertEquals(3, hand.getHandPile().size());
    }


    @Test
    public void testRemoveCard() {
        hand.addCard(new BlaueBohne());
        hand.addCard(new Saubohne());
        hand.addCard(new Brechbohne());

        hand.removeCard(2);

        Assertions.assertEquals(2, hand.getHandPile().size());
    }
}
