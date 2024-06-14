import game.cards.Card;
import game.cards.CardType;
import game.Hand;
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
    public void testPeekTopCardNone() {
        Assertions.assertThrows(NoSuchElementException.class, () -> hand.peekTopCard());
    }

    @Test
    public void testPeekTopCardOne() {
        Card card = new Card(CardType.BRECHBOHNE);
        hand.addCard(card);

        Assertions.assertEquals(card, hand.peekTopCard());
    }

    @Test
    public void testPeekTopCardMoreThanOne() {
        Card card = new Card(CardType.BRECHBOHNE);
        hand.addCard(card);
        hand.addCard(new Card(CardType.BRECHBOHNE));
        hand.addCard(new Card(CardType.BRECHBOHNE));

        Assertions.assertEquals(card, hand.peekTopCard());
    }

    @Test
    public void testAddCardOne() {
        hand.addCard(new Card(CardType.BRECHBOHNE));

        Assertions.assertEquals(1, hand.getHandPile().size());
    }


    @Test
    public void testAddCardThree() {
        hand.addCard(new Card(CardType.BRECHBOHNE));
        hand.addCard(new Card(CardType.BRECHBOHNE));
        hand.addCard(new Card(CardType.BRECHBOHNE));

        Assertions.assertEquals(3, hand.getHandPile().size());
    }


    @Test
    public void testRemoveCard() {
        hand.addCard(new Card(CardType.BLAUE_BOHNE));
        hand.addCard(new Card(CardType.SAUBOHNE));
        hand.addCard(new Card(CardType.BRECHBOHNE));

        hand.removeCard(2);

        Assertions.assertEquals(2, hand.getHandPile().size());
    }
}
