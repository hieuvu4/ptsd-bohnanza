import game.*;
import game.cards.BlaueBohne;
import game.cards.Brechbohne;
import game.cards.Card;
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
        gameField = mock(GameField.class);
        when(gameField.getExtension()).thenReturn(false);
        pile = new Pile(gameField);
        tradingArea = new TradingArea(gameField);
        when(gameField.getPile()).thenReturn(pile);
        when(gameField.getTradingArea()).thenReturn(tradingArea);
        player = new Player("Test", gameField);
    }

    @Test
    public void testPlantNoCards() {
        player.setPhase(new PhasePlanting());
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            player.plant(0, new Brechbohne());
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": There are no cards in the hand.", exception.getMessage());
    }

    @Test
    public void testPlantOne() throws IllegalMoveException {
        player.setPhase(new PhasePlanting());
        Card card = new Brechbohne();
        player.getHand().addCard(card);
        player.plant(0, card);

        Assertions.assertEquals(new Brechbohne().getName(), player.getField(0).getCardType().getName());
        Assertions.assertEquals(1, player.getField(0).getCardAmount());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, 12})
    public void testPlantMoreThanOne(int amount) throws IllegalMoveException {
        player.setPhase(new PhasePlanting());
        for(int i = 0; i < amount; i++) {
            Card card = new Brechbohne();
            player.getHand().addCard(card);
            player.plant(0, player.getHand().popTopCard());
        }

        Assertions.assertEquals(new Brechbohne().getName(), player.getField(0).getCardType().getName());
        Assertions.assertEquals(amount, player.getField(0).getCardAmount());
    }

    @Test
    public void testPlantWrongCard() {
        player.setPhase(new PhasePlanting());
        player.getHand().addCard(new Brechbohne());
        player.getHand().addCard(new BlaueBohne());

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.plant(0, new BlaueBohne());
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": The given card is not the first card.", exception.getMessage());
    }

    @Test
    public void testPlantSameFieldWrongType() throws IllegalMoveException {
        player.setPhase(new PhasePlanting());
        Card card = new Brechbohne();
        Card card2 = new BlaueBohne();
        player.getHand().addCard(card);
        player.getHand().addCard(card2);
        player.plant(0, card);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.plant(0, card2);
        });
        Assertions.assertEquals(new Brechbohne().getName(), player.getField(0).getCardType().getName());
        Assertions.assertEquals(1, player.getField(0).getCardAmount());
        Assertions.assertEquals("Player " + player.getName()
                + ": The given card type is not the same.", exception.getMessage());
    }

    @Test
    public void testPlantWrongPhase() {
        Phase[] phases = {new PhaseTrading(), new PhaseDrawing(), new PhaseOut()};
        for(Phase phase : phases) {
            player.setPhase(phase);
            player.getHand().addCard(new Brechbohne());

            Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
                player.plant(0, new Brechbohne());
            });
            Assertions.assertEquals("Player " + player.getName()
                    + ": Unable to perform this action in the current phase.", exception.getMessage());
        }
    }

    @Test
    public void testPlantTradedCards() throws IllegalMoveException {
        player.setPhase(new PhasePlantingTraded());
        Card card1 = new Brechbohne();
        Card card2 = new BlaueBohne();
        player.getTradedCards().add(card1);
        player.getTradedCards().add(card2);
        player.plant(0, player.getTradedCards().getFirst());
        player.plant(1, player.getTradedCards().getFirst());

        Assertions.assertEquals(new Brechbohne().getName(), player.getField(0).getCardType().getName());
        Assertions.assertEquals(new BlaueBohne().getName(), player.getField(1).getCardType().getName());
        Assertions.assertEquals(new ArrayList<>(), player.getTradedCards());
    }

    @Test
    public void testPlantTradedCardsNotPlanted() {
        player.setPhase(new PhasePlantingTraded());
        player.getTradedCards().add(new Brechbohne());
        player.getTradedCards().add(new BlaueBohne());

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.nextPhase();
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Traded cards should be planted.", exception.getMessage());
    }

    @Test
    public void testPlantTradedCardsNotComplete() throws IllegalMoveException {
        player.setPhase(new PhasePlantingTraded());
        Card card1 = new Brechbohne();
        player.getTradedCards().add(card1);
        player.getTradedCards().add(new BlaueBohne());
        player.plant(0, player.getTradedCards().getFirst());

        Assertions.assertEquals(card1, player.getField(0).getCardType());
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
        player.getHand().addCard(new Brechbohne());
        player.plant(0, new Brechbohne());
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.harvest(0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Field cannot be harvested because a field is empty.", exception.getMessage());
    }

    @Test
    public void testHarvestOneCardEachField() throws IllegalMoveException {
        player.setPhase(new PhasePlanting());
        player.getHand().addCard(new Brechbohne());
        player.getHand().addCard(new BlaueBohne());
        player.plant(0, new Brechbohne());
        player.plant(1, new BlaueBohne());
        player.harvest( 0);

        Assertions.assertNull(player.getField(0).getCardType());
        Assertions.assertEquals(new BlaueBohne().getName(), player.getField(1).getCardType().getName());
    }

    @Test
    public void testHarvestEachPhase() throws IllegalMoveException {
        Phase[] phases = {new PhasePlanting(), new PhaseTrading(), new PhasePlantingTraded(), new PhaseDrawing(), new PhaseOut()};

        for(Phase phase : phases) {
            player.setPhase(new PhasePlanting());
            for (int i = 0; i < 3; i++) {
                player.getHand().addCard(new Brechbohne());
                player.getHand().addCard(new BlaueBohne());
                player.plant(0, new Brechbohne());
                player.plant(1, new BlaueBohne());
            }
            player.setPhase(phase);
            player.harvest(0);

            Assertions.assertNull(player.getField(0).getCardType());
            Assertions.assertEquals(new BlaueBohne().getName(), player.getField(1).getCardType().getName());
        }
    }

    @Test
    public void testHarvestWrongField() throws IllegalMoveException {
        player.setPhase(new PhasePlanting());
        player.getHand().addCard(new Brechbohne());
        player.getHand().addCard(new BlaueBohne());
        player.getHand().addCard(new BlaueBohne());
        player.plant(0, new Brechbohne());
        player.plant(1, new BlaueBohne());
        player.plant(1, new BlaueBohne());

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
        Phase[] phases = {new PhasePlanting(), new PhaseTrading(), new PhasePlantingTraded(), new PhaseOut()};
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
        for(int i = 0; i < 3; i++) player.getCoins().add(new Brechbohne());
        player.buyThirdField();

        Assertions.assertEquals(3, player.getFields().length);
        Assertions.assertEquals(0, player.getCoins().size());
    }

    @Test
    public void testBuyThirdFieldNotEnoughCoins() {
        for(int i = 0; i < 2; i++) player.getCoins().add(new Brechbohne());

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.buyThirdField();
        });

        Assertions.assertEquals(2, player.getFields().length);
        Assertions.assertEquals("Player " + player.getName()
                + ": Not enough coins to buy a third field.", exception.getMessage());
    }

    @Test
    public void testBuyThirdFieldTwoTimes() throws IllegalMoveException {
        for(int i = 0; i < 10; i++) player.getCoins().add(new Brechbohne());
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
        Phase[] phases = {new PhasePlanting(), new PhaseTrading(), new PhasePlantingTraded(), new PhaseDrawing()};
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
        List<Card> cards = new ArrayList<>();
        cards.add(new Brechbohne());

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            player.offerCards(cards, 0);
        });
        Assertions.assertEquals("Player " + player.getName() + " doesn't have an offered card.",
                exception.getMessage());
    }

    @Test
    public void testOfferCardsNoCards() {
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
        Card card = new Brechbohne();
        player.getHand().addCard(card);
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        player.offerCards(cards, 0);

        Assertions.assertEquals(cards, tradingArea.getOffersForTCard0().get(player));
    }

    @Test
    public void testCheckOffersWrongPhase() {
        Phase[] phases = {new PhasePlanting(), new PhasePlantingTraded(), new PhaseDrawing(), new PhaseOut()};

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
        Phase[] phases = {new PhasePlanting(), new PhasePlantingTraded(), new PhaseDrawing(), new PhaseOut()};

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
    public void testAcceptOffers() throws IllegalMoveException {
        player.setPhase(new PhaseTrading());
        Player other = new Player("other", gameField);
        other.setPhase(new PhaseOut());
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            other.getHand().addCard(new Brechbohne());
            cards.add(new Brechbohne());
        }
        tradingArea.getTradingCards()[0] = new BlaueBohne();
        other.offerCards(cards, 0);

        player.acceptOffer(other, 0);

        Assertions.assertEquals(cards, player.getTradedCards());
        Assertions.assertNull(tradingArea.getTradingCards()[0]);
    }

    @Test
    public void testTakeTradingCards() throws IllegalMoveException {
        player.setPhase(new PhaseTrading());
        Card card = new BlaueBohne();
        tradingArea.getTradingCards()[0] = card;

        player.takeTradingCards(0);

        Assertions.assertNull(tradingArea.getTradingCards()[0]);
        Assertions.assertEquals(new ArrayList<>(Arrays.asList((card))), player.getTradedCards());
    }
    @Test
    public void testTakeTradingCardsWrongPhase() {
        Phase[] phases = {new PhasePlanting(), new PhasePlantingTraded(), new PhaseDrawing(), new PhaseOut()};

        for (Phase phase : phases) {
            player.setPhase(phase);

            Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
                player.takeTradingCards(0);
            });
            Assertions.assertEquals("Player " + player.getName()
                    + ": Unable to perform this action in the current phase.", exception.getMessage());
        }
    }
}
