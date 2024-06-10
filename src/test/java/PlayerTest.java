import game.*;
import game.phases.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerTest {

    private Player player;
    private Pile pile;
    private GameField gameField;
    private TradingArea tradingArea;

    @BeforeEach
    public void setUp() {
        pile = new Pile();
        tradingArea = new TradingArea(gameField);
        gameField = mock(GameField.class);
        when(gameField.getPile()).thenReturn(pile);
        when(gameField.getTradingArea()).thenReturn(tradingArea);
        player = new Player("Test", gameField);
    }

    @Test
    public void testPlantNoCards() {
        player.setPhase(new PhasePlanting());

        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            player.plant(0, Card.BRECHBOHNE);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": There are no cards in the hand.", exception.getMessage());
    }

    @Test
    public void testPlantOne() throws IllegalMoveException {
        player.setPhase(new PhasePlanting());
        player.getHand().addCard(Card.BRECHBOHNE);
        player.plant(0, Card.BRECHBOHNE);

        Assertions.assertEquals(Card.BRECHBOHNE, player.getField(0).getCardType());
        Assertions.assertEquals(1, player.getField(0).getCardAmount());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, 12})
    public void testPlantMoreThanOne(int amount) throws IllegalMoveException {
        player.setPhase(new PhasePlanting());
        for(int i = 0; i < amount; i++) {
            player.getHand().addCard(Card.BRECHBOHNE);
            player.plant(0, Card.BRECHBOHNE);
        }

        Assertions.assertEquals(Card.BRECHBOHNE, player.getField(0).getCardType());
        Assertions.assertEquals(amount, player.getField(0).getCardAmount());
    }

    @Test
    public void testPlantWrongCard() {
        player.setPhase(new PhasePlanting());
        player.getHand().addCard(Card.BRECHBOHNE);
        player.getHand().addCard(Card.BLAUE_BOHNE);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.plant(0, Card.BLAUE_BOHNE);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": The given card is not the first card.", exception.getMessage());
    }

    @Test
    public void testPlantSameFieldWrongType() throws IllegalMoveException {
        player.setPhase(new PhasePlanting());
        player.getHand().addCard(Card.BRECHBOHNE);
        player.getHand().addCard(Card.BLAUE_BOHNE);
        player.plant(0, Card.BRECHBOHNE);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.plant(0, Card.BLAUE_BOHNE);
        });
        Assertions.assertEquals(Card.BRECHBOHNE, player.getField(0).getCardType());
        Assertions.assertEquals(1, player.getField(0).getCardAmount());
        Assertions.assertEquals("Player " + player.getName()
                + ": The given card type is not the same.", exception.getMessage());
    }

    @Test
    public void testPlantWrongPhase() {
        Phase[] phases = {new PhaseRevealing(), new PhaseDrawing(), new PhaseOut()};
        for(Phase phase : phases) {
            player.setPhase(phase);
            player.getHand().addCard(Card.BRECHBOHNE);

            Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
                player.plant(0, Card.BRECHBOHNE);
            });
            Assertions.assertEquals("Player " + player.getName()
                    + ": Unable to perform this action in the current phase.", exception.getMessage());
        }
    }

    @Test
    public void testPlantTradedCards() throws IllegalMoveException {
        player.setPhase(new PhaseCultivating());
        player.getTradedCards().add(Card.BRECHBOHNE);
        player.getTradedCards().add(Card.BLAUE_BOHNE);
        player.plant( 0, player.getTradedCards().getFirst());
        player.plant(1, player.getTradedCards().getFirst());

        Assertions.assertEquals(Card.BRECHBOHNE, player.getField(0).getCardType());
        Assertions.assertEquals(Card.BLAUE_BOHNE, player.getField(1).getCardType());
        Assertions.assertEquals(new ArrayList<>(), player.getTradedCards());
    }

    @Test
    public void testPlantTradedCardsNotPlanted() throws IllegalMoveException {
        player.setPhase(new PhaseCultivating());
        player.getTradedCards().add(Card.BRECHBOHNE);
        player.getTradedCards().add(Card.BLAUE_BOHNE);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.nextPhase();
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Traded cards should be planted.", exception.getMessage());
    }

    @Test
    public void testPlantTradedCardsNotComplete() throws IllegalMoveException {
        player.setPhase(new PhaseCultivating());
        player.getTradedCards().add(Card.BRECHBOHNE);
        player.getTradedCards().add(Card.BLAUE_BOHNE);
        player.plant(0, player.getTradedCards().getFirst());

        Assertions.assertEquals(Card.BRECHBOHNE, player.getField(0).getCardType());
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.nextPhase();
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Traded cards should be planted.", exception.getMessage());
    }

    @Test
    public void testHarvestAllFieldsEmpty() {
        player.setPhase(new PhasePlanting());
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.harvest(0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Field cannot be harvested because a field is empty.", exception.getMessage());
    }

    @Test
    public void testHarvestOnlyOneFieldPlanted() throws IllegalMoveException {
        player.setPhase(new PhasePlanting());
        player.getHand().addCard(Card.BRECHBOHNE);
        player.plant(0, Card.BRECHBOHNE);
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.harvest(0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Field cannot be harvested because a field is empty.", exception.getMessage());
    }

    @Test
    public void testHarvestOneCardEachField() throws IllegalMoveException {
        player.setPhase(new PhasePlanting());
        player.getHand().addCard(Card.BRECHBOHNE);
        player.getHand().addCard(Card.BLAUE_BOHNE);
        player.plant(0, Card.BRECHBOHNE);
        player.plant(1, Card.BLAUE_BOHNE);
        player.harvest( 0);

        Assertions.assertNull(player.getField(0).getCardType());
        Assertions.assertEquals(Card.BLAUE_BOHNE, player.getField(1).getCardType());
    }

    @Test
    public void testHarvestEachPhase() throws IllegalMoveException {
        Phase[] phases = {new PhasePlanting(), new PhaseRevealing(), new PhaseCultivating(), new PhaseDrawing(), new PhaseOut()};

        for(Phase phase : phases) {
            player.setPhase(new PhasePlanting());
            for (int i = 0; i < 3; i++) {
                player.getHand().addCard(Card.BRECHBOHNE);
                player.getHand().addCard(Card.BLAUE_BOHNE);
                player.plant(0, Card.BRECHBOHNE);
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
        player.setPhase(new PhasePlanting());
        player.getHand().addCard(Card.BRECHBOHNE);
        player.getHand().addCard(Card.BLAUE_BOHNE);
        player.getHand().addCard(Card.BLAUE_BOHNE);
        player.plant(0, Card.BRECHBOHNE);
        player.plant(1, Card.BLAUE_BOHNE);
        player.plant(1, Card.BLAUE_BOHNE);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.harvest(0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Field cannot be harvested.", exception.getMessage());
    }

    @Test
    public void testDrawCards() throws IllegalMoveException {
        player.setPhase(new PhaseDrawing());
        player.drawCards(pile);

        Assertions.assertEquals(3, player.getHand().getHandPile().size());
    }

    @Test
    public void testDrawCardsTwoTimes() throws IllegalMoveException {
        player.setPhase(new PhaseDrawing());
        player.drawCards(pile);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.drawCards(pile);
        });

        Assertions.assertEquals("Player " + player.getName()
                + ": Already drawn three cards.", exception.getMessage());
    }

    @Test
    public void testDrawCardsWrongPhase() {
        Phase[] phases = {new PhasePlanting(), new PhaseRevealing(), new PhaseCultivating(), new PhaseOut()};
        for (Phase phase : phases) {
            player.setPhase(phase);

            Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
                player.drawCards(pile);
            });

            Assertions.assertEquals("Player " + player.getName()
                            + ": Unable to perform this action in the current phase.",
                    exception.getMessage());
        }
    }

    @Test
    public void testBuyThirdField() throws IllegalMoveException {
        for(int i = 0; i < 3; i++) player.getCoins().add(Card.BRECHBOHNE);
        player.buyThirdField();

        Assertions.assertEquals(3, player.getFields().length);
        Assertions.assertEquals(0, player.getCoins().size());
    }

    @Test
    public void testBuyThirdFieldNotEnoughCoins() {
        for(int i = 0; i < 2; i++) player.getCoins().add(Card.BRECHBOHNE);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.buyThirdField();
        });

        Assertions.assertEquals(2, player.getFields().length);
        Assertions.assertEquals("Player " + player.getName()
                + ": Not enough coins to buy a third field.", exception.getMessage());
    }

    @Test
    public void testBuyThirdFieldTwoTimes() throws IllegalMoveException {
        for(int i = 0; i < 10; i++) player.getCoins().add(Card.BRECHBOHNE);
        player.buyThirdField();

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.buyThirdField();
        });

        Assertions.assertEquals(3, player.getFields().length);
        Assertions.assertEquals("Player " + player.getName() + ": Already bought a field.",
                exception.getMessage());
    }

    @Test
    public void testOfferCardsWrongPhase() {
        Phase[] phases = {new PhasePlanting(), new PhaseRevealing(), new PhaseCultivating(), new PhaseDrawing()};
        for (Phase phase : phases) {
            player.setPhase(phase);

            Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
                player.offerCards(new ArrayList<>(), 0);
            });
            Assertions.assertEquals("Player " + player.getName()
                    + ": Unable to perform this action in the current phase.", exception.getMessage());
        }
    }

    @Test
    public void testOfferCardsWrongCards() {
        player.setPhase(new PhaseOut());
        List<Card> cards = new ArrayList<>();
        cards.add(Card.BRECHBOHNE);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            player.offerCards(cards, 0);
        });
        Assertions.assertEquals("Player " + player.getName() + " has doesn't have an offered card.",
                exception.getMessage());
    }

    @Test
    public void testOfferCardsNoCards() {
        player.setPhase(new PhaseOut());
        List<Card> cards = new ArrayList<>();

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            player.offerCards(cards, 0);
        });
        Assertions.assertEquals("Player " + player.getName() + " didn't offered any cards.",
                exception.getMessage());
    }

    @Test
    public void testOfferCards() throws IllegalMoveException {
        player.setPhase(new PhaseOut());
        player.getHand().addCard(Card.BRECHBOHNE);
        List<Card> cards = new ArrayList<>();
        cards.add(Card.BRECHBOHNE);
        player.offerCards(cards, 0);

        Assertions.assertEquals(cards, tradingArea.getOffersForTCard0().get(player));
    }

    @Test
    public void testCheckOffersWrongPhase() {
        Phase[] phases = {new PhasePlanting(), new PhaseCultivating(), new PhaseDrawing(), new PhaseOut()};

        for (Phase phase : phases) {
            player.setPhase(phase);

            Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
                player.checkOffers();
            });
            Assertions.assertEquals("Player " + player.getName()
                    + ": Unable to perform this action in the current phase.", exception.getMessage());
        }
    }

    @Test
    public void testAcceptOfferWrongPhase() {
        Phase[] phases = {new PhasePlanting(), new PhaseCultivating(), new PhaseDrawing(), new PhaseOut()};

        for (Phase phase : phases) {
            player.setPhase(phase);

            Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
                player.acceptOffer(new Player("", gameField), 0);
            });
            Assertions.assertEquals("Player " + player.getName()
                    + ": Unable to perform this action in the current phase.", exception.getMessage());
        }
    }

    @Test
    public void testAcceptOffer() throws IllegalMoveException {
        Player other = new Player("other", gameField);
        other.setPhase(new PhaseOut());
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            other.getHand().addCard(Card.BRECHBOHNE);
            cards.add(Card.BRECHBOHNE);
        }
        tradingArea.getTradingCards()[0] = Card.BLAUE_BOHNE;
        other.offerCards(cards, 0);

        player.setPhase(new PhaseRevealing());
        player.acceptOffer(other, 0);

        Assertions.assertEquals(cards, player.getTradedCards());
        Assertions.assertEquals(new ArrayList<>(Arrays.asList((Card.BLAUE_BOHNE))), other.getTradedCards());
        Assertions.assertNull(tradingArea.getTradingCards()[0]);
    }

    @Test
    public void testTakeTradingCardsWrongPhase() {
        Phase[] phases = {new PhasePlanting(), new PhaseCultivating(), new PhaseDrawing(), new PhaseOut()};

        for (Phase phase : phases) {
            player.setPhase(phase);

            Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
                player.takeTradingCards(0);
            });
            Assertions.assertEquals("Player " + player.getName()
                    + ": Unable to perform this action in the current phase.", exception.getMessage());
        }
    }

    @Test
    public void testTakeTradingCards() throws IllegalMoveException {
        tradingArea.getTradingCards()[0] = Card.BLAUE_BOHNE;

        player.setPhase(new PhaseRevealing());
        player.takeTradingCards(0);

        Assertions.assertNull(tradingArea.getTradingCards()[0]);
        Assertions.assertEquals(new ArrayList<>(Arrays.asList((Card.BLAUE_BOHNE))), player.getTradedCards());
    }
}
