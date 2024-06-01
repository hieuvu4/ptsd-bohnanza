import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TradingAreaTest {

    TradingArea tradingArea;
    GameField gameField;

    @BeforeEach
    public void setUp() {
        gameField = mock(GameField.class);
        when(gameField.getPile()).thenReturn(new Pile());
        tradingArea = new TradingArea(gameField);
    }

    @Test
    public void testFillTradingArea() {
        tradingArea.fillTradingArea();

        Assertions.assertEquals(2, tradingArea.getTradingCards().length);
        Assertions.assertNotNull(tradingArea.getTradingCards()[0]);
        Assertions.assertNotNull(tradingArea.getTradingCards()[1]);
    }

    @Test
    public void testEmptyTradingField() {
        tradingArea.fillTradingArea();
        tradingArea.emptyTradingField(0);

        Assertions.assertEquals(2, tradingArea.getTradingCards().length);
        Assertions.assertNull(tradingArea.getTradingCards()[0]);
        Assertions.assertNotNull(tradingArea.getTradingCards()[1]);
    }
}