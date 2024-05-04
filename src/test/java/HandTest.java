import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class HandTest {
    Hand hand = new Hand();

    @Test
    public void testAddCard() {
        hand.addCard(Card.RED);
        hand.addCard(Card.RED);
        hand.addCard(Card.RED);

        Assertions.assertEquals(3, hand.getHandPile().size());
    }


    @Test
    public void testRemoveCard() {
        hand.addCard(Card.RED);
        hand.addCard(Card.RED);
        hand.addCard(Card.RED);

        hand.removeCard(2);

        Assertions.assertEquals(2, hand.getHandPile().size());
    }
}
