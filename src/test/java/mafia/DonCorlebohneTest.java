package mafia;

import game.GameField;
import game.IllegalMoveException;
import game.Pile;
import game.cards.CardType;
import game.mafia.DonCorlebohne;
import game.mafia.MafiaBank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DonCorlebohneTest {


    private Pile pile;
    private GameField gameField;
    private DonCorlebohne donCorlebohne;

    @BeforeEach
    void setUp() {
        gameField = mock(GameField.class);
        when(gameField.getExtension()).thenReturn(true);
        pile = new Pile(gameField);
        when(gameField.getPile()).thenReturn(pile);
        when(gameField.getMafiaBank()).thenReturn(new MafiaBank());
        donCorlebohne = new DonCorlebohne(gameField);
        when(gameField.getDonCorlebohne()).thenReturn(donCorlebohne);
    }

    @Test
    public void testTryHarvestCardTypeNull() throws IllegalMoveException {
        donCorlebohne.getField().setCardType(null);
        donCorlebohne.tryHarvest();

        Assertions.assertNull(donCorlebohne.getField().getCardType());
        Assertions.assertEquals(0, gameField.getMafiaBank().getCoins().size());
    }

    @Test
    public void testTryHarvestNotYet() throws IllegalMoveException {
        donCorlebohne.getField().setCardType(CardType.BRECHBOHNE);
        for(int i = 0; i < 4; i++) donCorlebohne.getField().increaseCardAmount();
        donCorlebohne.tryHarvest();

        Assertions.assertEquals(4, donCorlebohne.getField().getCardAmount());
    }

    @Test
    public void testTryHarvest() throws IllegalMoveException {
        donCorlebohne.getField().setCardType(CardType.BRECHBOHNE);
        for(int i = 0; i < 5; i++) donCorlebohne.getField().increaseCardAmount();
        donCorlebohne.tryHarvest();

        Assertions.assertEquals(2, gameField.getMafiaBank().getCoins().size());
        Assertions.assertEquals(0, donCorlebohne.getField().getCardAmount());
    }
}
