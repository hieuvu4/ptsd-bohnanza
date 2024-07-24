package mafia;

import game.GameField;
import game.IllegalMoveException;
import game.Pile;
import game.cards.CardType;
import game.mafia.AlCabohne;
import game.mafia.MafiaBank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class AlCabohneTest {

    private Pile pile;
    private GameField gameField;
    private AlCabohne alCabohne;

    @BeforeEach
    void setUp() {
        gameField = mock(GameField.class);
        when(gameField.getExtension()).thenReturn(true);
        pile = new Pile(gameField);
        when(gameField.getPile()).thenReturn(pile);
        when(gameField.getMafiaBank()).thenReturn(new MafiaBank());
        alCabohne = new AlCabohne(gameField);
        when(gameField.getAlCabohne()).thenReturn(alCabohne);
    }

    @Test
    public void testTryHarvestCardTypeNull() throws IllegalMoveException {
        alCabohne.getField().setCardType(null);
        alCabohne.tryHarvest();

        Assertions.assertNull(alCabohne.getField().getCardType());
        Assertions.assertEquals(0, gameField.getMafiaBank().getCoins().size());
    }


    @Test
    public void testTryHarvestNotYet() throws IllegalMoveException {
        alCabohne.getField().setCardType(CardType.BRECHBOHNE);
        for(int i = 0; i < 5; i++) alCabohne.getField().increaseCardAmount();
        alCabohne.tryHarvest();

        Assertions.assertEquals(5, alCabohne.getField().getCardAmount());
    }

    @Test
    public void testTryHarvest() throws IllegalMoveException {
        alCabohne.getField().setCardType(CardType.BRECHBOHNE);
        for(int i = 0; i < 6; i++) alCabohne.getField().increaseCardAmount();
        alCabohne.tryHarvest();

        Assertions.assertEquals(3, gameField.getMafiaBank().getCoins().size());
        Assertions.assertEquals(0, alCabohne.getField().getCardAmount());
    }
}
