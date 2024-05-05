import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class HandTest {
    Hand hand = new Hand();

    @Test
    public void testAddCard() {
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
