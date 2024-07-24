import game.GameField;
import game.Player;
import game.phases.PhaseOut;
import game.phases.PhasePlanting;
import game.phases.PhasePlantingTraded;
import game.phases.PhaseTrading;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class GameFieldTest {

    @Test
    public void updatePhaseTrading() {
        GameField gameField = new GameField(3, false);
        Assertions.assertNull(gameField.getTradingArea().getTradingCards()[0]);
        Assertions.assertNull(gameField.getTradingArea().getTradingCards()[1]);
        gameField.update(null, new PhaseTrading());
        Assertions.assertNotNull(gameField.getTradingArea().getTradingCards()[0]);
        Assertions.assertNotNull(gameField.getTradingArea().getTradingCards()[1]);
    }

    @Test
    public void updatePhasePlantingTraded() {
        GameField gameField = new GameField(3, false);
        gameField.getTradingArea().getOffersForTCard0()
                .put(gameField.getPlayers().get(1),
                        gameField.getPlayers().get(1).getHand().getHandPile());
        Assertions.assertFalse(gameField.getTradingArea().getOffersForTCard0().isEmpty());
        gameField.update(null, new PhasePlantingTraded());
        Assertions.assertTrue(gameField.getTradingArea().getOffersForTCard0().isEmpty());
        Assertions.assertTrue(gameField.getTradingArea().getOffersForTCard1().isEmpty());
    }

    @Test
    public void updatePhaseOut() {
        GameField gameField = new GameField(3, false);
        gameField.update(null, new PhaseOut());
        Assertions.assertEquals(gameField.getPlayers().get(1), gameField.getTurnPlayer());
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -1, 0, 1, 2, 6, 12})
    public void incorrectPlayerCountBase(int playerCount){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new GameField(playerCount, false));
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -1, 0, 3, 4, 6, 12})
    public void incorrectPlayerCountExt(int playerCount){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new GameField(playerCount, true));
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5})
    public void correctPlayerCountBase(int playerCount){
        Assertions.assertDoesNotThrow(() -> new GameField(playerCount, false));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    public void correctPlayerCountExt(int playerCount){
        Assertions.assertDoesNotThrow(() -> new GameField(playerCount, true));
    }

    @Test
    public void testGetAlCabohneSuc(){
        Assertions.assertNotNull(new GameField(2, true).getAlCabohne());
    }

    @Test
    public void testGetAlCabohneFail(){
        Assertions.assertNull(new GameField(3, false).getAlCabohne());
    }

    @Test
    public void testGetDonCorlebohneSuc(){
        Assertions.assertNotNull(new GameField(2, true).getDonCorlebohne());
    }

    @Test
    public void testGetDonCorlebohneFail(){
        Assertions.assertNull(new GameField(3, false).getDonCorlebohne());
    }

    @Test
    public void testGetJoeBohnanoSuc(){
        Assertions.assertNotNull(new GameField(1, true).getJoeBohnano());
    }

    @Test
    public void testGetJoeBohnanoFailCount(){
        Assertions.assertNull(new GameField(2, true).getJoeBohnano());
    }

    @Test
    public void testGetJoeBohnanoFail(){
        Assertions.assertNull(new GameField(3, false).getJoeBohnano());
    }

    @Test
    public void testGetDiscoverAreaSuc(){
        Assertions.assertNotNull(new GameField(2, true).getDiscoverArea());
    }

    @Test
    public void testGetDiscoverAreaFail(){
        Assertions.assertNull(new GameField(3, false).getDiscoverArea());
    }
}
