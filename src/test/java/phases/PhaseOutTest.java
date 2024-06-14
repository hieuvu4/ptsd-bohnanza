package phases;

import game.*;
import game.cards.Brechbohne;
import game.cards.Card;
import game.phases.Phase;
import game.phases.PhaseOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhaseOutTest {

    private Player player;
    private Pile pile;
    private GameField gameField;
    private TradingArea tradingArea;
    private Phase phase;

    @BeforeEach
    public void setUp() {
        gameField = mock(GameField.class);
        when(gameField.getExtension()).thenReturn(false);
        pile = new Pile(gameField);
        tradingArea = new TradingArea(gameField);
        when(gameField.getPile()).thenReturn(pile);
        when(gameField.getTradingArea()).thenReturn(tradingArea);
        player = new Player("Test", gameField);
        phase = new PhaseOut();
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
    public void testDrawCardsWrongPhase() {
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.drawCards(pile);
        });

        Assertions.assertEquals("Player " + player.getName()
                        + ": Unable to perform this action in the current phase.",
                exception.getMessage());
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
