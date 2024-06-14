package phases;

import game.*;
import game.cards.Brechbohne;
import game.cards.Card;
import game.phases.Phase;
import game.phases.PhasePlanting;
import game.phases.PhaseDrawing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhaseDrawingTest {

    private Player player;
    private Pile pile;
    private GameField gameField;
    private DiscoverArea discoverArea;
    private Phase phase;

    @BeforeEach
    public void setUp() {
        pile = new Pile(gameField);
        discoverArea = new DiscoverArea(gameField);
        gameField = mock(GameField.class);
        when(gameField.getPile()).thenReturn(pile);
        when(gameField.getExtension()).thenReturn(false);
        when(gameField.getDiscoverArea()).thenReturn(discoverArea);
        player = new Player("Test", gameField);
        phase = new PhaseDrawing();
        player.setPhase(phase);
    }

    @Test
    public void testPlantWrongPhase() {
        player.getHand().addCard(new Brechbohne());

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.plant(0, new Brechbohne());
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Unable to perform this action in the current phase.", exception.getMessage());
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
    public void testDrawCards() throws IllegalMoveException {
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
    public void testTakeDiscoverCardsWrongPhase() {
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.takeDiscoverCards(0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Unable to perform this action in the current phase.", exception.getMessage());
    }
}
