package phases;

import game.*;
import game.cards.Card;
import game.cards.CardType;
import game.phases.Phase;
import game.phases.PhaseOut;
import game.phases.PhaseTrading;
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
        when(gameField.getExtension()).thenReturn(false);
        tradingArea = new TradingArea(gameField);
        pile = new Pile(gameField);
        when(gameField.getPile()).thenReturn(pile);
        when(gameField.getTradingArea()).thenReturn(tradingArea);
        player = new Player("Test", gameField);
        phase = new PhaseTrading();
        player.setPhase(phase);
    }

    @Test
    public void testPlantWrongPhase() {
        Card card = new Card(CardType.BRECHBOHNE);
        player.getHand().addCard(card);
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.plant(0, card);
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
            var card = new Card(CardType.BRECHBOHNE);
            other.getHand().addCard(card);
            cards.add(card);
        }
        tradingArea.getTradingCards()[0] = new Card(CardType.BLAUE_BOHNE);
        other.offerCards(cards, 0);

        player.acceptOffer(other, 0);

        Assertions.assertEquals(cards, player.getTradedCards());
        Assertions.assertNull(tradingArea.getTradingCards()[0]);
    }

    @Test
    public void testTakeTradingCards() throws IllegalMoveException {
        Card card = new Card(CardType.BLAUE_BOHNE);
        tradingArea.getTradingCards()[0] = card;

        player.takeTradingCards(0);

        Assertions.assertNull(tradingArea.getTradingCards()[0]);
        Assertions.assertEquals(new ArrayList<>(List.of(card)), player.getTradedCards());
    }
}
