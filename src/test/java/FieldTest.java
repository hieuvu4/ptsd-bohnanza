import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class FieldTest {


    private Card card = Card.ROTE_BOHNE;

    private Field field = new Field();

    @Test
    public void testAddCardToField() {
        field.addCardToField();

        Assertions.assertEquals(1, field.getCardAmount());
    }

    @Test
    public void testIsEmpty() {
        Assertions.assertTrue(field.isEmpty());
    }

    @Test
    public void testHarvest() {
        field.setCardType(card);
        IntStream.range(0, 4).forEach(i -> field.addCardToField());
        int result = field.harvest();

        Assertions.assertEquals(3, result);
        Assertions.assertTrue(field.isEmpty());
        Assertions.assertEquals(0, field.getCardAmount());
    }
}
