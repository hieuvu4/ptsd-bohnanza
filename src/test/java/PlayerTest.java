import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PlayerTest {

    private Player player;
    private Pile pile;

    @BeforeEach
    public void setup() {
        player = new Player();
        pile = new Pile();
    }

    @Test
    public void plantTest() {
        player.plant(0, Card.ACKERBOHNE);
        Assertions.assertThrows(Throwable.class, () -> player.plant(0, Card.AUGENBOHNE));

        Assertions.assertEquals(Card.ACKERBOHNE, player.getField(0).getCardType());
        Assertions.assertEquals(1, player.getField(0).getCardAmount());

        player.plant(0, Card.ACKERBOHNE);
        Assertions.assertEquals(2, player.getField(0).getCardAmount());
    }

    @Test
    public void harvestTest() {
        Assertions.assertThrows(Throwable.class, () -> player.harvest(0));

        player.plant(0, Card.ACKERBOHNE);
        Assertions.assertThrows(Throwable.class, () -> player.harvest(0));

        player.plant(1, Card.AUGENBOHNE);
        Assertions.assertDoesNotThrow(() -> player.harvest(0));

        Assertions.assertNull(player.getField(0).getCardType());
        Assertions.assertEquals(0, player.getField(0).getCardAmount());
    }

    @Test
    public void drawCardsTest() {
        player.drawCards(pile);
        Assertions.assertEquals(3, player.getHand().getHandPile().size());
        player.drawCards(pile);
        Assertions.assertEquals(6, player.getHand().getHandPile().size());
    }

    @Test
    public void buyThirdFieldTest() {
        Assertions.assertThrows(Throwable.class,
                () -> player.buyThirdField(List.of(Card.ACKERBOHNE, Card.ACKERBOHNE, Card.ACKERBOHNE)));
    }

    @Test
    public void tradeTest() {
        Assertions.assertThrows(Throwable.class, () -> player.tradeCards(List.of(Card.ACKERBOHNE), List.of(Card.AUGENBOHNE)));
        player.getHand().addCard(Card.ACKERBOHNE);
        Assertions.assertDoesNotThrow(() -> player.tradeCards(List.of(Card.ACKERBOHNE), List.of(Card.AUGENBOHNE)));

        Assertions.assertEquals(0, player.getHand().getHandPile().size());
    }
}
