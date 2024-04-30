import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FieldTest {


    private Card card = new Card("Rote Bohne", new int[]{2, 3, 4, 5}, new int[]{1, 2, 3, 5});

    private Field field = new Field(1);

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
        field.addCardToField();
        field.addCardToField();
        field.addCardToField();
        field.addCardToField();
        int result = field.harvest();
        Assertions.assertEquals(3, result);
        Assertions.assertTrue(field.isEmpty());
        Assertions.assertEquals(0, field.getCardAmount());
    }
}
