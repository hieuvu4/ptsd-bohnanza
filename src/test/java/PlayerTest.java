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
    public void testPlantOne() throws IllegalMoveException {
        player.plant(0, Card.BLAUE_BOHNE);

        Assertions.assertEquals(Card.BLAUE_BOHNE, player.getField(0).getCardType());
        Assertions.assertEquals(1, player.getField(0).getCardAmount());

    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, 12})
    public void testPlantMoreThanOne(int amount) throws IllegalMoveException {
        for(int i = 0; i < amount; i++) player.plant(0, Card.BLAUE_BOHNE);

        Assertions.assertEquals(Card.BLAUE_BOHNE, player.getField(0).getCardType());
        Assertions.assertEquals(amount, player.getField(0).getCardAmount());
    }

    @Test
    public void testPlantSameFieldWrongType() throws IllegalMoveException {
        player.plant(0, Card.BLAUE_BOHNE);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () ->
                player.plant(0, Card.AUGENBOHNE));
        Assertions.assertEquals("The card type is not the same.", exception.getMessage());
    }

    @Test
    public void testHarvestOneCardEachField() throws IllegalMoveException {
        player.plant(0, Card.BLAUE_BOHNE);
        player.plant(1, Card.AUGENBOHNE);
        player.harvest(0);

        Assertions.assertNull(player.getField(0).getCardType());
        Assertions.assertEquals(Card.AUGENBOHNE, player.getField(1).getCardType());
        Assertions.assertEquals(0, player.getField(0).getCardAmount());
    }

    @Test
    public void testHarvestWrongField() throws IllegalMoveException {
        player.plant(0, Card.BLAUE_BOHNE);
        player.plant(1, Card.AUGENBOHNE);
        player.plant(1, Card.AUGENBOHNE);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> player.harvest(0));
        Assertions.assertEquals("Field cannot be harvested.", exception.getMessage());
    }

    @Test
    public void testHarvestAllFieldsEmpty() {
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> player.harvest(0));
        Assertions.assertEquals("Field cannot be harvested because a field is empty.", exception.getMessage());
    }

    @Test
    public void testHarvestOnlyOneFieldPlanted() throws IllegalMoveException {
        player.plant(0, Card.BLAUE_BOHNE);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> player.harvest(0));
        Assertions.assertEquals("Field cannot be harvested because a field is empty.", exception.getMessage());
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
                () -> player.buyThirdField(List.of(Card.BLAUE_BOHNE, Card.BLAUE_BOHNE, Card.BLAUE_BOHNE)));
    }

    @Test
    public void testTrade() {
        Assertions.assertThrows(Throwable.class, () -> player.tradeCards(List.of(Card.BLAUE_BOHNE), List.of(Card.AUGENBOHNE)));
        player.getHand().addCard(Card.BLAUE_BOHNE);
        Assertions.assertDoesNotThrow(() -> player.tradeCards(List.of(Card.BLAUE_BOHNE), List.of(Card.AUGENBOHNE)));

        Assertions.assertEquals(0, player.getHand().getHandPile().size());
    }
}
