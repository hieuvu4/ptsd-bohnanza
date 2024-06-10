import game.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CardTest {

    @Test
    public void testGetCoinValue() {
        Assertions.assertEquals(4, Card.BRECHBOHNE.getCoinValue(10));
        Assertions.assertEquals(1, Card.BRECHBOHNE.getCoinValue(4));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -2, -4})
    public void testGetCoinValueBelowOne(final int amount) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Card.BRECHBOHNE.getCoinValue(amount));
    }
}
