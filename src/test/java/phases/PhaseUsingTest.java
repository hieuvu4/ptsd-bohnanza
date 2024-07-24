package phases;

import game.*;
import game.cards.CardType;
import game.phases.Phase;
import game.phases.PhaseUsing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhaseUsingTest {



    private Player player;
    private Pile pile;
    private GameField gameField;
    private DiscoverArea discoverArea;
    private Phase phase;

    @BeforeEach
    void setUp() {
        gameField = mock(GameField.class);
        when(gameField.getExtension()).thenReturn(true);
        pile = new Pile(gameField);
        discoverArea = new DiscoverArea(gameField);
        when(gameField.getPile()).thenReturn(pile);
        when(gameField.getDiscoverArea()).thenReturn(discoverArea);
        player = new Player("Test", gameField);
        when(gameField.getPlayers()).thenReturn(new ArrayList<>(List.of(player)));
        phase = new PhaseUsing();
        player.setPhase(phase);
    }


    @Test
    public void testTakeDiscoverCardsNull() {
        discoverArea.getDiscoverFields().get(0).setCardType(null);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.takeDiscoverCards(0);
        });

        Assertions.assertEquals("There is no discover card in this field.", exception.getMessage());
    }

    @Test
    public void testTakeDiscoverCardsNoSuchCardInField() {
        discoverArea.getDiscoverFields().get(0).setCardType(CardType.BRECHBOHNE);
        player.getField(0).setCardType(CardType.BLAUE_BOHNE);
        player.getField(1).setCardType(CardType.FEUERBOHNE);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.takeDiscoverCards(0);
        });

        Assertions.assertEquals("There is no such card in player " + player.getName()
                + "'s field.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 12, 50})
    public void testTakeDiscoverCards(int amount) throws IllegalMoveException {
        discoverArea.getDiscoverFields().get(0).setCardType(CardType.BRECHBOHNE);
        for(int i = 0; i < amount; i++) discoverArea.getDiscoverFields().get(0).increaseCardAmount();
        player.getField(0).setCardType(CardType.BLAUE_BOHNE);
        player.getField(1).setCardType(CardType.BRECHBOHNE);
        player.getField(0).increaseCardAmount();
        player.getField(1).increaseCardAmount();
        player.takeDiscoverCards(0);

        Assertions.assertEquals(amount+1, player.getField(1).getCardAmount());
        Assertions.assertNull(discoverArea.getDiscoverFields().get(1).getCardType());
    }


    @Test
    public void testPutDiscoverCardsToDiscard() {
        discoverArea.getDiscoverFields().get(0).setCardType(null);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.putDiscoverCardsToDiscard(0);
        });

        Assertions.assertEquals("There is no discover card in this field.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 12, 50})
    public void testPutDiscoverCardsNoSuchCardInField(int amount) throws IllegalMoveException {
        discoverArea.getDiscoverFields().get(0).setCardType(CardType.BRECHBOHNE);
        for (int i = 0; i < amount; i++) discoverArea.getDiscoverFields().get(0).increaseCardAmount();
        player.putDiscoverCardsToDiscard(0);

        Assertions.assertEquals(amount, pile.getDiscardPile().size());
        Assertions.assertNull(discoverArea.getDiscoverFields().get(0).getCardType());
    }
}
