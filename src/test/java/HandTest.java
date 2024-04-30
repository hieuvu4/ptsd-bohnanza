import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class HandTest {
    Hand hand = new Hand();

    @Test
    public void testAddCard() {
        hand.addCard(new Card("Test", new int[4], new int[4]));
        hand.addCard(new Card("Test", new int[4], new int[4]));
        hand.addCard(new Card("Test", new int[4], new int[4]));

        Assertions.assertEquals(3, hand.getHandPile().size());
    }


    @Test
    public void testRemoveCard() {
        hand.addCard(new Card("Test", new int[4], new int[4]));
        hand.addCard(new Card("Test", new int[4], new int[4]));
        hand.addCard(new Card("Test", new int[4], new int[4]));

        hand.removeCard(2);

        Assertions.assertEquals(2, hand.getHandPile().size());
    }
}
