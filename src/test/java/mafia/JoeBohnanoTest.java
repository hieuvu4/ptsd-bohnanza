package mafia;

import game.GameField;
import game.IllegalMoveException;
import game.Pile;
import game.cards.CardType;
import game.mafia.JoeBohnano;
import game.mafia.MafiaBank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JoeBohnanoTest {



    private Pile pile;
    private GameField gameField;
    private JoeBohnano joeBohnano;

    @BeforeEach
    void setUp() {
        gameField = mock(GameField.class);
        when(gameField.getExtension()).thenReturn(true);
        pile = new Pile(gameField);
        when(gameField.getPile()).thenReturn(pile);
        when(gameField.getMafiaBank()).thenReturn(new MafiaBank());
        joeBohnano = new JoeBohnano(gameField);
        when(gameField.getJoeBohnano()).thenReturn(joeBohnano);
    }

    @Test
    public void testTryHarvestCardTypeNull() throws IllegalMoveException {
        joeBohnano.getField().setCardType(null);
        joeBohnano.tryHarvest();

        Assertions.assertNull(joeBohnano.getField().getCardType());
        Assertions.assertEquals(0, gameField.getMafiaBank().getCoins().size());
    }

    @Test
    public void testTryHarvestNotYet() throws IllegalMoveException {
        joeBohnano.getField().setCardType(CardType.BRECHBOHNE);
        for(int i = 0; i < 2; i++) joeBohnano.getField().increaseCardAmount();
        joeBohnano.tryHarvest();

        Assertions.assertEquals(2, joeBohnano.getField().getCardAmount());
    }

    @Test
    public void testTryHarvest() throws IllegalMoveException {
        joeBohnano.getField().setCardType(CardType.BRECHBOHNE);
        for(int i = 0; i < 3; i++) joeBohnano.getField().increaseCardAmount();
        joeBohnano.tryHarvest();

        Assertions.assertEquals(1, gameField.getMafiaBank().getCoins().size());
        Assertions.assertEquals(0, joeBohnano.getField().getCardAmount());
    }
}
