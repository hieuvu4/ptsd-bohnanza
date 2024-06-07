package phases;

import game.*;
import game.phases.Phase;
import game.phases.Phase3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Phase3Test {

    private Player player;
    private Pile pile;
    private GameField gameField;
    private TradingArea tradingArea;
    private Phase phase;

    @BeforeEach
    public void setUp() {
        pile = new Pile();
        tradingArea = new TradingArea(gameField);
        gameField = mock(GameField.class);
        when(gameField.getPile()).thenReturn(pile);
        when(gameField.getTradingArea()).thenReturn(tradingArea);
        player = new Player("Test", gameField);
        phase = new Phase3();
        player.setPhase(phase);
    }

    @Test
    public void testPlantTradedCards() throws IllegalMoveException {
        player.getTradedCards().add(Card.AUGENBOHNE);
        player.getTradedCards().add(Card.BLAUE_BOHNE);
        player.plant(0, player.getTradedCards().getFirst());
        player.plant(1, player.getTradedCards().getFirst());

        Assertions.assertEquals(Card.AUGENBOHNE, player.getField(0).getCardType());
        Assertions.assertEquals(Card.BLAUE_BOHNE, player.getField(1).getCardType());
        Assertions.assertEquals(new ArrayList<>(), player.getTradedCards());
    }

    @Test
    public void testPlantTradedCardsNotPlanted() {
        player.getTradedCards().add(Card.AUGENBOHNE);
        player.getTradedCards().add(Card.BLAUE_BOHNE);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.nextPhase();
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Traded cards should be planted.", exception.getMessage());
    }

    @Test
    public void testPlantTradedCardsNotComplete() throws IllegalMoveException {
        player.getTradedCards().add(Card.AUGENBOHNE);
        player.getTradedCards().add(Card.BLAUE_BOHNE);
        player.plant(0, player.getTradedCards().getFirst());

        Assertions.assertEquals(Card.AUGENBOHNE, player.getField(0).getCardType());
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.nextPhase();
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Traded cards should be planted.", exception.getMessage());
    }

    @Test
    public void testDrawCardsWrongPhase() {
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.drawCards(pile);
        });

        Assertions.assertEquals("Player " + player.getName()
                        + ": Unable to perform this action in the current phase.",
                exception.getMessage());
    }

    @Test
    public void testOfferCardsWrongPhase() {
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.offerCards(new ArrayList<>(), 0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Unable to perform this action in the current phase.", exception.getMessage());
    }

    @Test
    public void testCheckOffersWrongPhase() {
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.checkOffers();
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Unable to perform this action in the current phase.", exception.getMessage());
    }

    @Test
    public void testAcceptOffersWrongPhase() {
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.acceptOffer(new Player("", gameField), 0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Unable to perform this action in the current phase.", exception.getMessage());
    }

    @Test
    public void testTakeTradingCardsWrongPhase() {Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.takeTradingCards(0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Unable to perform this action in the current phase.", exception.getMessage());
    }
}
