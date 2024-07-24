package mafia;

import game.cards.Card;
import game.cards.CardType;
import game.mafia.MafiaBank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MafiaBankTest {

    private MafiaBank mafiaBank;

    @BeforeEach
    void setUp() {
        mafiaBank = new MafiaBank();
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7, 8, 9, 10})
    public void addCoin(int amount) {
        for(int i = 0; i < amount; i++) mafiaBank.addCoin(new Card(CardType.BRECHBOHNE));

        Assertions.assertEquals(amount, mafiaBank.getCoins().size());
    }
}
