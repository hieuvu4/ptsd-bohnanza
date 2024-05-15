import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.NoSuchElementException;

public class PlayerTest {

    private Player player;
    private Pile pile;

    @BeforeEach
    public void setUp() {
        player = new Player();
        pile = new Pile();
    }

    @Test
    public void testPlantNoCards() {
        player.setPhase(new Phase1());

        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            player.plant(0, Card.AUGENBOHNE);
        });
        Assertions.assertEquals("There are no cards in the hand.", exception.getMessage());
    }

    @Test
    public void testPlantOne() throws IllegalMoveException {
        player.setPhase(new Phase1());
        player.getHand().addCard(Card.AUGENBOHNE);
        player.plant(0, Card.AUGENBOHNE);

        Assertions.assertEquals(Card.AUGENBOHNE, player.getField(0).getCardType());
        Assertions.assertEquals(1, player.getField(0).getCardAmount());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, 12})
    public void testPlantMoreThanOne(int amount) throws IllegalMoveException {
        player.setPhase(new Phase1());
        for(int i = 0; i < amount; i++) {
            player.getHand().addCard(Card.AUGENBOHNE);
            player.plant(0, Card.AUGENBOHNE);
        }

        Assertions.assertEquals(Card.AUGENBOHNE, player.getField(0).getCardType());
        Assertions.assertEquals(amount, player.getField(0).getCardAmount());
    }

    @Test
    public void testPlantWrongCard() {
        player.setPhase(new Phase1());
        player.getHand().addCard(Card.AUGENBOHNE);
        player.getHand().addCard(Card.BLAUE_BOHNE);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.plant(0, Card.BLAUE_BOHNE);
        });
        Assertions.assertEquals("The given card is not the first card.", exception.getMessage());
    }

    @Test
    public void testPlantSameFieldWrongType() throws IllegalMoveException {
        player.setPhase(new Phase1());
        player.getHand().addCard(Card.AUGENBOHNE);
        player.getHand().addCard(Card.BLAUE_BOHNE);
        player.plant(0, Card.AUGENBOHNE);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.plant(0, Card.BLAUE_BOHNE);
        });
        Assertions.assertEquals(Card.AUGENBOHNE, player.getField(0).getCardType());
        Assertions.assertEquals(1, player.getField(0).getCardAmount());
        Assertions.assertEquals("The given card type is not the same.", exception.getMessage());
    }

    @Test
    public void testPlantWrongPhase() {
        Phase[] phases = {new Phase2(), new Phase4(), new PhaseOut()};
        for(Phase phase : phases) {
            player.setPhase(phase);
            player.getHand().addCard(Card.AUGENBOHNE);

            Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
                player.plant(0, Card.AUGENBOHNE);
            });
            Assertions.assertEquals("Unable to perform this action in the current phase.", exception.getMessage());
        }
    }

    @Test
    public void testPlantTradedCards() {
        //TODO
    }

    @Test
    public void testHarvestAllFieldsEmpty() {
        player.setPhase(new Phase1());
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.harvest(0);
        });
        Assertions.assertEquals("Field cannot be harvested because a field is empty.", exception.getMessage());
    }

    @Test
    public void testHarvestOnlyOneFieldPlanted() throws IllegalMoveException {
        player.setPhase(new Phase1());
        player.getHand().addCard(Card.AUGENBOHNE);
        player.plant(0, Card.AUGENBOHNE);
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.harvest(0);
        });
        Assertions.assertEquals("Field cannot be harvested because a field is empty.", exception.getMessage());
    }

    @Test
    public void testHarvestOneCardEachField() throws IllegalMoveException {
        player.setPhase(new Phase1());
        player.getHand().addCard(Card.AUGENBOHNE);
        player.getHand().addCard(Card.BLAUE_BOHNE);
        player.plant(0, Card.AUGENBOHNE);
        player.plant(1, Card.BLAUE_BOHNE);
        player.harvest(0);

        Assertions.assertNull(player.getField(0).getCardType());
        Assertions.assertEquals(Card.BLAUE_BOHNE, player.getField(1).getCardType());
    }

    @Test
    public void testHarvestEachPhase() throws IllegalMoveException {
        Phase[] phases = {new Phase1(), new Phase2(), new Phase3(), new Phase4(), new PhaseOut()};

        for(Phase phase : phases) {
            player.setPhase(new Phase1());
            for (int i = 0; i < 3; i++) {
                player.getHand().addCard(Card.AUGENBOHNE);
                player.getHand().addCard(Card.BLAUE_BOHNE);
                player.plant(0, Card.AUGENBOHNE);
                player.plant(1, Card.BLAUE_BOHNE);
            }
            player.setPhase(phase);
            player.harvest(0);

            Assertions.assertNull(player.getField(0).getCardType());
            Assertions.assertEquals(Card.BLAUE_BOHNE, player.getField(1).getCardType());
        }
    }

    @Test
    public void testHarvestWrongField() throws IllegalMoveException {
        player.setPhase(new Phase1());
        player.getHand().addCard(Card.AUGENBOHNE);
        player.getHand().addCard(Card.BLAUE_BOHNE);
        player.getHand().addCard(Card.BLAUE_BOHNE);
        player.plant(0, Card.AUGENBOHNE);
        player.plant(1, Card.BLAUE_BOHNE);
        player.plant(1, Card.BLAUE_BOHNE);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.harvest(0);
        });
        Assertions.assertEquals("Field cannot be harvested.", exception.getMessage());
    }

    @Test
    public void testDrawCards() throws IllegalMoveException {
        player.setPhase(new Phase4());
        player.drawCards(pile);

        Assertions.assertEquals(3, player.getHand().getHandPile().size());
    }

    @Test
    public void testDrawCardsTwoTimes() throws IllegalMoveException {
        player.setPhase(new Phase4());
        player.drawCards(pile);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.drawCards(pile);
        });

        Assertions.assertEquals("Player already drawn three cards.", exception.getMessage());
    }

    @Test
    public void testDrawCardsWrongPhase() {
        Phase[] phases = {new Phase1(), new Phase2(), new Phase3(), new PhaseOut()};
        for (Phase phase : phases) {
            player.setPhase(phase);

            Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
                player.drawCards(pile);
            });

            Assertions.assertEquals("Unable to perform this action in the current phase.",
                    exception.getMessage());
        }
    }

    @Test
    public void testBuyThirdField() throws IllegalMoveException {
        for(int i = 0; i < 3; i++) player.getCoins().add(Card.AUGENBOHNE);
        player.buyThirdField();

        Assertions.assertEquals(3, player.getFields().length);
    }

    @Test
    public void testBuyThirdFieldNotEnoughCoins() {
        for(int i = 0; i < 2; i++) player.getCoins().add(Card.AUGENBOHNE);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.buyThirdField();
        });

        Assertions.assertEquals(2, player.getFields().length);
        Assertions.assertEquals("Not enough coins to buy a third field.", exception.getMessage());
    }

    @Test
    public void testBuyThirdFieldTwoTimes() throws IllegalMoveException {
        for(int i = 0; i < 10; i++) player.getCoins().add(Card.AUGENBOHNE);
        player.buyThirdField();

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.buyThirdField();
        });

        Assertions.assertEquals(3, player.getFields().length);
        Assertions.assertEquals("Already bought a field.", exception.getMessage());
    }

    @Test
    public void testTrade() {

    }
}
