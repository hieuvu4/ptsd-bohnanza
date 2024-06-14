package phases;

import game.*;
import game.cards.BlaueBohne;
import game.cards.Brechbohne;
import game.phases.Phase;
import game.phases.PhaseCultivating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhasePlantingTradedTest {

    private Player player;
    private Pile pile;
    private GameField gameField;
    private TradingArea tradingArea;
    private Phase phase;

    @BeforeEach
    public void setUp() {
        tradingArea = new TradingArea(gameField);
        gameField = mock(GameField.class);
        pile = new Pile(gameField);
        when(gameField.getPile()).thenReturn(pile);
        when(gameField.getTradingArea()).thenReturn(tradingArea);
        player = new Player("Test", gameField);
        phase = new PhaseCultivating();
        player.setPhase(phase);
    }

    @Test
    public void testPlantTradedCards() throws IllegalMoveException {
        player.getTradedCards().add(new Brechbohne());
        player.getTradedCards().add(new BlaueBohne());
        player.plant(0, player.getTradedCards().getFirst());
        player.plant(1, player.getTradedCards().getFirst());

        Assertions.assertEquals(new Brechbohne(), player.getField(0).getCardType());
        Assertions.assertEquals(new BlaueBohne(), player.getField(1).getCardType());
        Assertions.assertEquals(new ArrayList<>(), player.getTradedCards());
    }

    @Test
    public void testPlantTradedCardsNotPlanted() {
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
        player.getTradedCards().add(new Brechbohne());
        player.getTradedCards().add(new BlaueBohne());
        player.plant(0, player.getTradedCards().getFirst());

        Assertions.assertEquals(new Brechbohne(), player.getField(0).getCardType());
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
    public void testTakeDiscoverCardsWrongPhase() {Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.takeDiscoverCards(0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Unable to perform this action in the current phase.", exception.getMessage());
    }
}
