package phases;

import game.*;
import game.cards.Card;
import game.cards.CardType;
import game.mafia.AlCabohne;
import game.mafia.DonCorlebohne;
import game.mafia.JoeBohnano;
import game.phases.Phase;
import game.phases.PhaseCultivating;
import game.phases.PhasePlanting;
import game.phases.PhaseRevealing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhaseRevealingTest {

    private Player player;
    private Pile pile;
    private GameField gameField;
    private Phase phase;


    @BeforeEach
    void setUp() {
        gameField = mock(GameField.class);
        when(gameField.getExtension()).thenReturn(true);
        pile = new Pile(gameField);
        when(gameField.getPile()).thenReturn(pile);
        player = new Player("Test", gameField);
        when(gameField.getPlayers()).thenReturn(new ArrayList<>(List.of(player)));
        AlCabohne alCabohne = new AlCabohne(gameField);
        DonCorlebohne donCorlebohne = new DonCorlebohne(gameField);
        JoeBohnano joeBohnano = new JoeBohnano(gameField);
        when(gameField.getAlCabohne()).thenReturn(alCabohne);
        when(gameField.getDonCorlebohne()).thenReturn(donCorlebohne);
        when(gameField.getJoeBohnano()).thenReturn(joeBohnano);
        phase = new PhaseRevealing();
        player.setPhase(phase);
    }

    @Test
    public void testHarvestWrongPhase() throws IllegalMoveException {
        player.setPhase(new PhasePlanting());
        Card card1 = new Card(CardType.BRECHBOHNE);
        Card card2 = new Card(CardType.BLAUE_BOHNE);
        player.getHand().addCard(card1);
        player.getHand().addCard(card2);
        player.plant(0, card1);
        player.plant(1, card2);
        player.setPhase(phase);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.harvest(0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Unable to perform this action in the current phase.", exception.getMessage());
    }

}
