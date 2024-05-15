import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.IntStream;

public class PileTest {

    Pile pile;

    @BeforeEach
    public void setUp() {
        pile = new Pile();
    }

    @Test
    public void drawCardOne() {
        int amountCardsBeforeDraw = pile.getCards().size();
        Card firstCard = pile.getCards().getFirst();

        Card drawedCard = pile.drawCard();

        Assertions.assertEquals(amountCardsBeforeDraw - 1, pile.getCards().size());
        Assertions.assertEquals(firstCard, drawedCard);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5})
    public void drawCardMoreThanOne(final int amount) {
        int amountCardsBeforeDraw = pile.getCards().size();
        Card card = pile.getCards().get(amount);
        IntStream.range(0, amount).forEach(i -> pile.drawCard());

        Assertions.assertEquals(amountCardsBeforeDraw - amount, pile.getCards().size());
        Assertions.assertEquals(card, pile.getCards().getFirst());
    }
}
