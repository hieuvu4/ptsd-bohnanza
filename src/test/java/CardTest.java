import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CardTest {

    @Test
    public void coinValueTest(){
        Assertions.assertEquals(4, Card.AUGENBOHNE.coinValue(6));
        Assertions.assertEquals(1, Card.AUGENBOHNE.coinValue(3));
        Assertions.assertThrows(Throwable.class, () -> Card.AUGENBOHNE.coinValue(0));
    }
}
