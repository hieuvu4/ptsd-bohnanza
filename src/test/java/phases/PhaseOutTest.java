package phases;

import game.*;
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
        pile = new Pile();
        tradingArea = new TradingArea(gameField);
        gameField = mock(GameField.class);
        when(gameField.getPile()).thenReturn(pile);
        when(gameField.getTradingArea()).thenReturn(tradingArea);
        player = new Player("Test", gameField);
        phase = new PhaseOut();
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
        cards.add(Card.AUGENBOHNE);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            player.offerCards(cards, 0);
        });
        Assertions.assertEquals("Player " + player.getName() + " has doesn't have an offered card.",
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
        player.getHand().addCard(Card.AUGENBOHNE);
        List<Card> cards = new ArrayList<>();
        cards.add(Card.AUGENBOHNE);
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
    public void testTakeTradingCardsWrongPhase() {
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.takeTradingCards(0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Unable to perform this action in the current phase.", exception.getMessage());
    }
}
