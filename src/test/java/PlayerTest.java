import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.IntStream;

public class PlayerTest {

    private Player player;
    private Pile pile;

    @BeforeEach
    public void setUp() {
        player = new Player();
        pile = new Pile();
    }

    @Test
    public void testPlantOne() {
        player.plant(0, Card.ACKERBOHNE);

        Assertions.assertEquals(Card.ACKERBOHNE, player.getField(0).getCardType());
        Assertions.assertEquals(1, player.getField(0).getCardAmount());

    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, 12})
    public void testPlantMoreThanOne(int amount) {
        IntStream.range(0, amount).forEach(i -> player.plant(0, Card.ACKERBOHNE));

        Assertions.assertEquals(Card.ACKERBOHNE, player.getField(0).getCardType());
        Assertions.assertEquals(amount, player.getField(0).getCardAmount());
    }

    @Test
    public void testPlantSameFieldWrongType() {
        player.plant(0, Card.ACKERBOHNE);
        Assertions.assertThrows(IllegalArgumentException.class, () -> player.plant(0, Card.AUGENBOHNE));
    }

    @Test
    public void testHarvestOneCardEachField() throws IllegalMoveException {
        player.plant(0, Card.ACKERBOHNE);
        player.plant(1, Card.AUGENBOHNE);
        player.harvest(0);

        Assertions.assertNull(player.getField(0).getCardType());
        Assertions.assertEquals(Card.AUGENBOHNE, player.getField(1).getCardType());
        Assertions.assertEquals(0, player.getField(0).getCardAmount());
    }

    @Test
    public void testHarvestWrongField() {
        player.plant(0, Card.ACKERBOHNE);
        player.plant(1, Card.AUGENBOHNE);
        player.plant(1, Card.AUGENBOHNE);

        Assertions.assertThrows(IllegalArgumentException.class, () -> player.harvest(0));
    }

    @Test
    public void testHarvestAllFieldsEmpty() {
        Assertions.assertThrows(Throwable.class, () -> player.harvest(0));
    }

    @Test
    public void testHarvestOnlyOneFieldPlanted() {
        player.plant(0, Card.ACKERBOHNE);

        Assertions.assertThrows(Throwable.class, () -> player.harvest(0));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 6})
    public void testDrawCards(int amount) {
        IntStream.range(0, amount).forEach(i ->  player.drawCards(pile));

        Assertions.assertEquals(3*amount, player.getHand().getHandPile().size());
    }

    @Test
    public void testBuyThirdField() {
        Assertions.assertThrows(Throwable.class,
                () -> player.buyThirdField(List.of(Card.ACKERBOHNE, Card.ACKERBOHNE, Card.ACKERBOHNE)));
    }

    @Test
    public void testTrade() {
        Assertions.assertThrows(Throwable.class, () -> player.tradeCards(List.of(Card.ACKERBOHNE), List.of(Card.AUGENBOHNE)));
        player.getHand().addCard(Card.ACKERBOHNE);
        Assertions.assertDoesNotThrow(() -> player.tradeCards(List.of(Card.ACKERBOHNE), List.of(Card.AUGENBOHNE)));

        Assertions.assertEquals(0, player.getHand().getHandPile().size());
    }
}
