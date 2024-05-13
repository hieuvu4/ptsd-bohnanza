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
        hand.addCard(Card.AUGENBOHNE);

        Card card = hand.popTopCard();

        Assertions.assertEquals(Card.AUGENBOHNE, card);
    }

    @Test
    public void testPopTopCardMoreThanOne() {
        hand.addCard(Card.AUGENBOHNE);
        hand.addCard(Card.BRECHBOHNE);
        hand.addCard(Card.ROTE_BOHNE);

        Card card = hand.popTopCard();

        Assertions.assertEquals(Card.AUGENBOHNE, card);
    }

    @Test
    public void testAddCardOne() {
        hand.addCard(Card.AUGENBOHNE);

        Assertions.assertEquals(1, hand.getHandPile().size());
    }


    @Test
    public void testAddCardThree() {
        hand.addCard(Card.AUGENBOHNE);
        hand.addCard(Card.BRECHBOHNE);
        hand.addCard(Card.ROTE_BOHNE);

        Assertions.assertEquals(3, hand.getHandPile().size());
    }


    @Test
    public void testRemoveCard() {
        hand.addCard(Card.GARTENBOHNE);
        hand.addCard(Card.SAUBOHNE);
        hand.addCard(Card.BRECHBOHNE);

        hand.removeCard(2);

        Assertions.assertEquals(2, hand.getHandPile().size());
    }
}
