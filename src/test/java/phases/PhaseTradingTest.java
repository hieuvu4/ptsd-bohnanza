package phases;

import game.*;
import game.cards.BlaueBohne;
import game.cards.Brechbohne;
import game.cards.Card;
import game.phases.Phase;
import game.phases.PhaseRevealing;
import game.phases.PhaseOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhaseTradingTest {

    private Player player;
    private Pile pile;
    private GameField gameField;
    private TradingArea tradingArea;
    private Phase phase;

    @BeforeEach
    public void setUp() {
        gameField = mock(GameField.class);
        tradingArea = new TradingArea(gameField);
        pile = new Pile(gameField);
        when(gameField.getPile()).thenReturn(pile);
        when(gameField.getTradingArea()).thenReturn(tradingArea);
        player = new Player("Test", gameField);
        phase = new PhaseRevealing();
        player.setPhase(phase);
    }

    @Test
    public void testPlantWrongPhase() {player.getHand().addCard(new Brechbohne());
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
    public void testOfferCardsWrongPhase() {
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.offerCards(new ArrayList<>(), 0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Unable to perform this action in the current phase.", exception.getMessage());
    }

    @Test
    public void testAcceptOffers() throws IllegalMoveException {
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
        Assertions.assertEquals(new ArrayList<>(Arrays.asList((new BlaueBohne()))), other.getTradedCards());
        Assertions.assertNull(tradingArea.getTradingCards()[0]);
    }

    @Test
    public void testTakeDiscoverCards() throws IllegalMoveException {
        tradingArea.getTradingCards()[0] = new BlaueBohne();

        player.setPhase(new PhaseRevealing());
        player.takeDiscoverCards(0);

        Assertions.assertNull(tradingArea.getTradingCards()[0]);
        Assertions.assertEquals(new ArrayList<>(Arrays.asList((new BlaueBohne()))), player.getTradedCards());
    }
}
