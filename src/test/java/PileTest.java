import game.GameField;
import game.cards.Card;
import game.cards.CardType;
import game.Pile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.IntStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PileTest {

    Pile pile;
    GameField gameField;

    @BeforeEach
    public void setUp() {
        gameField = mock(GameField.class);
        when(gameField.getExtension()).thenReturn(false);
        pile = new Pile(gameField);
    }

    @Test
    public void testDrawCardAfterRefill() {
        pile.getDiscardPile().addAll(pile.getCards());
        pile.getCards().clear();
        pile.drawCard();

        int amountCardsBeforeDraw = pile.getCards().size();
        Card firstCard = pile.getCards().getFirst();
        Card drawedCard = pile.drawCard();

        Assertions.assertEquals(amountCardsBeforeDraw - 1, pile.getCards().size());
        Assertions.assertEquals(firstCard, drawedCard);
    }

    @Test
    public void testDrawCardOne() {
        int amountCardsBeforeDraw = pile.getCards().size();
        Card firstCard = pile.getCards().getFirst();

        Card drawedCard = pile.drawCard();

        Assertions.assertEquals(amountCardsBeforeDraw - 1, pile.getCards().size());
        Assertions.assertEquals(firstCard, drawedCard);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5})
    public void testDrawCardMoreThanOne(final int amount) {
        int amountCardsBeforeDraw = pile.getCards().size();
        Card card = pile.getCards().get(amount);
        IntStream.range(0, amount).forEach(i -> pile.drawCard());

        Assertions.assertEquals(amountCardsBeforeDraw - amount, pile.getCards().size());
        Assertions.assertEquals(card, pile.getCards().getFirst());
    }
}
