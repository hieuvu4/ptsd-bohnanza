package phases;

import game.*;
import game.cards.Card;
import game.cards.CardType;
import game.phases.Phase;
import game.phases.PhasePlanting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhasePlantingTest {

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
        phase = new PhasePlanting();
        player.setPhase(phase);
    }

    @Test
    public void testPlantNoCards() {
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            player.plant(0, new Card(CardType.BRECHBOHNE));
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": There are no cards in the hand.", exception.getMessage());
    }

    @Test
    public void testPlantOne() throws IllegalMoveException {
        Card card = new Card(CardType.BRECHBOHNE);
        player.getHand().addCard(card);
        player.plant(0, card);

        Assertions.assertEquals(CardType.BRECHBOHNE, player.getField(0).getCardType());
        Assertions.assertEquals(1, player.getField(0).getCardAmount());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, 12})
    public void testPlantMoreThanOne(int amount) throws IllegalMoveException {
        for(int i = 0; i < amount; i++) {
            Card card = new Card(CardType.BRECHBOHNE);
            player.getHand().addCard(card);
            player.plant(0, player.getHand().peekTopCard());
        }

        Assertions.assertEquals(CardType.BRECHBOHNE, player.getField(0).getCardType());
        Assertions.assertEquals(amount, player.getField(0).getCardAmount());
    }

    @Test
    public void testPlantWrongCard() {
        Card blue = new Card(CardType.BLAUE_BOHNE);
        player.getHand().addCard(new Card(CardType.BRECHBOHNE));
        player.getHand().addCard(blue);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.plant(0, blue);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": The given card is not the first card.", exception.getMessage());
    }

    @Test
    public void testPlantSameFieldWrongType() throws IllegalMoveException {
        Card card = new Card(CardType.BRECHBOHNE);
        Card card2 = new Card(CardType.BLAUE_BOHNE);
        player.getHand().addCard(card);
        player.getHand().addCard(card2);
        player.plant(0, card);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.plant(0, card2);
        });
        Assertions.assertEquals(CardType.BRECHBOHNE, player.getField(0).getCardType());
        Assertions.assertEquals(1, player.getField(0).getCardAmount());
        Assertions.assertEquals("Player " + player.getName()
                + ": The given card type is not the same.", exception.getMessage());
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
    public void testTakeDiscoverCardsWrongPhase() {
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.takeDiscoverCards(0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Unable to perform this action in the current phase.", exception.getMessage());
    }
}
