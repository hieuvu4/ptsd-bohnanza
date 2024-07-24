import game.DiscoverArea;
import game.GameField;
import game.Pile;
import game.cards.Card;
import game.cards.CardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DiscoverAreaTest {

    private GameField gameField;
    private Pile pile;

    @BeforeEach
    public void setUp() {
        gameField = mock(GameField.class);
        pile = mock(Pile.class);
        when(gameField.getExtension()).thenReturn(true);
        when(gameField.getPile()).thenReturn(pile);
        when(pile.drawCard()).thenReturn(new Card(CardType.BLAUE_BOHNE), new Card(CardType.PUFFBOHNE));
    }


    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void fillDiscoverCardTest(int field){
        var area = new DiscoverArea(gameField);
        Assertions.assertNotEquals(1, area.getDiscoverFields().get(field).getCardAmount());
        Assertions.assertNull(area.getDiscoverFields().get(field).getCardType());
        area.fillDiscoverCard(field);
        Assertions.assertEquals(1, area.getDiscoverFields().get(field).getCardAmount());
        Assertions.assertEquals(CardType.BLAUE_BOHNE, area.getDiscoverFields().get(field).getCardType());
    }
}
