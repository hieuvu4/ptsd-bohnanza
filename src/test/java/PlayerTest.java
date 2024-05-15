import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.IntStream;

public class PlayerTest {

    private Player player;
    private Pile pile;

    @BeforeEach
    public void setUp() {
        player = new Player();
        pile = new Pile();
    }

    @Test
    public void testPlantOne() throws IllegalMoveException {

    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, 12})
    public void testPlantMoreThanOne(final int amount) throws IllegalMoveException {
    }

    @Test
    public void testPlantSameFieldWrongType() throws IllegalMoveException {
    }

    @Test
    public void testHarvestOneCardEachField() throws IllegalMoveException {
    }

    @Test
    public void testHarvestWrongField() throws IllegalMoveException {
    }

    @Test
    public void testHarvestAllFieldsEmpty() {
    }

    @Test
    public void testHarvestOnlyOneFieldPlanted() throws IllegalMoveException {
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 6})
    public void testDrawCards(final int amount) throws IllegalMoveException {
    }

    @Test
    public void testBuyThirdField() {

    }

    @Test
    public void testTrade() {

    }
}
