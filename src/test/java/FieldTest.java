import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.IntStream;

public class FieldTest {


    private Card card;

    private Field field;

    @BeforeEach
    public void setUp() {
        card = Card.ROTE_BOHNE;
        field = new Field();
    }

    @Test
    public void testAddCardToFieldOne() {
        field.addCardToField();

        Assertions.assertEquals(1, field.getCardAmount());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, 12})
    public void testAddCardToFieldMoreThanOne(int amount) {
        IntStream.range(0, amount).forEach(i -> field.addCardToField());

        Assertions.assertEquals(amount, field.getCardAmount());
    }


    @Test
    public void testIsEmpty() {
        Assertions.assertTrue(field.isEmpty());
    }

    @Test
    public void testIsNotEmpty() {
        field.setCardType(card);

        Assertions.assertFalse(field.isEmpty());
        Assertions.assertEquals(card, field.getCardType());
    }

    @Test
    public void testHarvest() throws IllegalMoveException {
        field.setCardType(card);
        IntStream.range(0, 4).forEach(i -> field.addCardToField());
        int result = field.harvest();

        Assertions.assertEquals(3, result);
        Assertions.assertTrue(field.isEmpty());
        Assertions.assertEquals(0, field.getCardAmount());
    }

    @Test
    public void testHarvestEmpty() {
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> field.harvest());
        Assertions.assertEquals("Field can't be harvest because field is empty.", exception.getMessage());
    }
}
