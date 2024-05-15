import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CardTest {

    @Test
    public void testGetCoinValue() {
        Assertions.assertEquals(4, Card.AUGENBOHNE.getCoinValue(6));
        Assertions.assertEquals(1, Card.AUGENBOHNE.getCoinValue(3));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -2, -4})
    public void testGetCoinValueBelowOne(final int amount) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Card.AUGENBOHNE.getCoinValue(amount));
    }
}
