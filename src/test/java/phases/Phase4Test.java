package phases;

import game.*;
import game.phases.Phase;
import game.phases.Phase1;
import game.phases.Phase4;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Phase4Test {

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
        phase = new Phase4();
        player.setPhase(phase);
    }

    @Test
    public void testPlantWrongPhase() {
        player.getHand().addCard(Card.AUGENBOHNE);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.plant(0, Card.AUGENBOHNE);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Unable to perform this action in the current phase.", exception.getMessage());
    }

    @Test
    public void testHarvestAllFieldsEmpty() {
        player.setPhase(new Phase1());
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.harvest(0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Field cannot be harvested because a field is empty.", exception.getMessage());
    }

    @Test
    public void testHarvestOnlyOneFieldPlanted() throws IllegalMoveException {
        player.setPhase(new Phase1());
        player.getHand().addCard(Card.AUGENBOHNE);
        player.plant(0, Card.AUGENBOHNE);
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.harvest(0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Field cannot be harvested because a field is empty.", exception.getMessage());
    }

    @Test
    public void testDrawCards() throws IllegalMoveException {
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

        Assertions.assertEquals("Player " + player.getName()
                + ": Already drawn three cards.", exception.getMessage());
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
    public void testTakeTradingCardsWrongPhase() {
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.takeTradingCards(0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Unable to perform this action in the current phase.", exception.getMessage());
    }
}
