package phases;

import game.*;
import game.cards.Card;
import game.cards.CardType;
import game.mafia.AlCabohne;
import game.mafia.Boss;
import game.mafia.DonCorlebohne;
import game.mafia.JoeBohnano;
import game.phases.Phase;
import game.phases.PhaseCultivating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhaseCultivatingTest {

    private Player player;
    private Pile pile;
    private GameField gameField;
    private DiscoverArea discoverArea;
    private Phase phase;


    @BeforeEach
    void setUp() {
        gameField = mock(GameField.class);
        when(gameField.getExtension()).thenReturn(true);
        pile = new Pile(gameField);
        discoverArea = new DiscoverArea(gameField);
        when(gameField.getPile()).thenReturn(pile);
        when(gameField.getDiscoverArea()).thenReturn(discoverArea);
        player = new Player("Test", gameField);
        when(gameField.getPlayers()).thenReturn(new ArrayList<>(List.of(player)));
        AlCabohne alCabohne = new AlCabohne(gameField);
        DonCorlebohne donCorlebohne = new DonCorlebohne(gameField);
        JoeBohnano joeBohnano = new JoeBohnano(gameField);
        when(gameField.getAlCabohne()).thenReturn(alCabohne);
        when(gameField.getDonCorlebohne()).thenReturn(donCorlebohne);
        when(gameField.getJoeBohnano()).thenReturn(joeBohnano);
        phase = new PhaseCultivating();
        player.setPhase(phase);

    }

    @Test
    public void testCultivateOwnFieldDiscoverFieldNull() {
        discoverArea.getDiscoverFields().get(0).setCardType(null);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.cultivateOwnField(0);
        });
        Assertions.assertEquals("There is no discover card in this field.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-2, -1, 4, 6})
    public void testCultivateOwnFieldWrongNumber(int amount) {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            player.cultivateOwnField(amount);
        });
        Assertions.assertEquals("Discover card field number must be between 0 and 2.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 5, 7, 9, 50})
    public void testCultivateOwnFieldNullField(int amount) throws IllegalMoveException {
        discoverArea.getDiscoverFields().get(0).setCardType(CardType.BRECHBOHNE);
        for(int i = 0; i < amount; i++) discoverArea.getDiscoverFields().get(0).increaseCardAmount();
        player.cultivateOwnField(0);

        Assertions.assertNull(discoverArea.getDiscoverFields().get(0).getCardType());
        Assertions.assertEquals(amount, player.getField(0).getCardAmount());
    }


    @ParameterizedTest
    @ValueSource(ints = {2, 4, 5, 7, 9, 50})
    public void testCultivateOwnFieldNotNullField(int amount) throws IllegalMoveException {
        discoverArea.getDiscoverFields().get(0).setCardType(CardType.BRECHBOHNE);
        for(int i = 0; i < amount; i++) discoverArea.getDiscoverFields().get(0).increaseCardAmount();
        player.getField(0).setCardType(CardType.FEUERBOHNE);
        player.getField(0).increaseCardAmount();
        player.getField(1).setCardType(CardType.BRECHBOHNE);
        player.getField(1).increaseCardAmount();
        player.cultivateOwnField(0);

        Assertions.assertNull(discoverArea.getDiscoverFields().get(0).getCardType());
        Assertions.assertEquals(amount+1, player.getField(1).getCardAmount());
    }


    @Test
    public void testCultivateBossFieldDiscoverFieldNull() {
        discoverArea.getDiscoverFields().get(0).setCardType(null);
        Boss[] bosses = {gameField.getAlCabohne(), gameField.getDonCorlebohne(), gameField.getJoeBohnano()};

        for(Boss boss : bosses) {
            Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
                player.cultivateBossField(0, boss);
            });
            Assertions.assertEquals("There is no discover card in this field.", exception.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {-2, -1, 4, 6})
    public void testCultivateBossFieldWrongNumber(int amount) {
        Boss[] bosses = {gameField.getAlCabohne(), gameField.getDonCorlebohne(), gameField.getJoeBohnano()};
        for(Boss boss : bosses) {
            Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
                player.cultivateBossField(amount, boss);
            });
            Assertions.assertEquals("Discover card field number must be between 0 and 2.",
                    exception.getMessage());
        }
    }

    @Test
    public void testCultivateBossAnotherBossAlreadyHasType() {
        Boss[] bosses = {gameField.getAlCabohne(), gameField.getDonCorlebohne(), gameField.getJoeBohnano()};
        for(Boss boss : bosses) {
            discoverArea.getDiscoverFields().get(0).setCardType(CardType.BRECHBOHNE);
            discoverArea.getDiscoverFields().get(0).increaseCardAmount();
            boss.getField().setCardType(CardType.BRECHBOHNE);
            boss.getField().increaseCardAmount();
            for(Boss boss2 : bosses) {
                if(!boss2.equals(boss)) {
                    Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
                        player.cultivateBossField(0, boss2);
                    });
                    Assertions.assertEquals("Another boss already has a card of type.",
                            exception.getMessage());
                    Assertions.assertNull(boss2.getField().getCardType());
                }
            }
            discoverArea.getDiscoverFields().get(0).clear();
            boss.getField().clear();
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 6, 8, 10})
    public void testCultivateBossFieldNullField(int amount) throws IllegalMoveException {
        Boss[] bosses = {gameField.getAlCabohne(), gameField.getDonCorlebohne(), gameField.getJoeBohnano()};
        for(Boss boss : bosses) {
            discoverArea.getDiscoverFields().get(0).setCardType(CardType.BRECHBOHNE);
            for(int i = 0; i < amount; i++)discoverArea.getDiscoverFields().get(0).increaseCardAmount();
            player.cultivateBossField(0, boss);

            Assertions.assertEquals(CardType.BRECHBOHNE, boss.getField().getCardType());
            Assertions.assertEquals(amount, boss.getField().getCardAmount());
            Assertions.assertNull(discoverArea.getDiscoverFields().get(0).getCardType());
            boss.getField().clear();
        }

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 6, 8, 10})
    public void testCultivateBossFieldNotNullField(int amount) throws IllegalMoveException {
        Boss[] bosses = {gameField.getAlCabohne(), gameField.getDonCorlebohne(), gameField.getJoeBohnano()};
        for(Boss boss : bosses) {
            boss.getField().setCardType(CardType.BRECHBOHNE);
            boss.getField().increaseCardAmount();
            discoverArea.getDiscoverFields().get(0).setCardType(CardType.BRECHBOHNE);
            for(int i = 0; i < amount; i++) discoverArea.getDiscoverFields().get(0).increaseCardAmount();
            player.cultivateBossField(0, boss);

            Assertions.assertEquals(CardType.BRECHBOHNE, boss.getField().getCardType());
            Assertions.assertEquals(1 + amount, boss.getField().getCardAmount());
            Assertions.assertNull(discoverArea.getDiscoverFields().get(0).getCardType());
            boss.getField().clear();
        }
    }

    @Test
    public void giveBossCardFromHandPileNoSuchCard() {
        Card card = new Card(CardType.BRECHBOHNE);
        Card card2 = new Card(CardType.FEUERBOHNE);
        player.getHand().getHandPile().add(card);
        Boss[] bosses = {gameField.getAlCabohne(), gameField.getDonCorlebohne(), gameField.getJoeBohnano()};
        for(Boss boss : bosses) {
            Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
                player.giveBossCardFromHand(card2, boss);
            });
            Assertions.assertEquals("No such card in hand.", exception.getMessage());
        }
    }

    @Test
    public void giveBossCardFromHandPileBossFieldNotEmpty() {
        Card card = new Card(CardType.BRECHBOHNE);
        player.getHand().getHandPile().add(card);
        Boss[] bosses = {gameField.getAlCabohne(), gameField.getDonCorlebohne(), gameField.getJoeBohnano()};
        bosses[0].getField().setCardType(CardType.BLAUE_BOHNE);
        bosses[1].getField().setCardType(CardType.GARTENBOHNE);
        bosses[2].getField().setCardType(CardType.ROTE_BOHNE);
        for(Boss boss : bosses) {
            Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
                player.giveBossCardFromHand(card, boss);
            });
            Assertions.assertEquals("Field of boss is not empty.", exception.getMessage());
            boss.getField().clear();
        }
    }


    @Test
    public void giveBossCardFromHandPileAnotherBossAlreadyHasCardType() {
        Card card = new Card(CardType.BRECHBOHNE);
        player.getHand().getHandPile().add(card);
        Boss[] bosses = {gameField.getAlCabohne(), gameField.getDonCorlebohne()};
        bosses[0].getField().setCardType(CardType.BLAUE_BOHNE);
        bosses[1].getField().setCardType(CardType.BRECHBOHNE);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.giveBossCardFromHand(card, gameField.getJoeBohnano());
        });
        Assertions.assertEquals("Another boss already has a card of type " + card.cardType(), exception.getMessage());
    }

    @Test
    public void giveBossCardFromHandPile() throws IllegalMoveException {
        Boss[] bosses = {gameField.getAlCabohne(), gameField.getDonCorlebohne(), gameField.getJoeBohnano()};

        for(Boss boss : bosses) {
            Card card = new Card(CardType.BRECHBOHNE);
            player.getHand().getHandPile().add(card);
            player.giveBossCardFromHand(card, boss);

            Assertions.assertEquals(CardType.BRECHBOHNE, boss.getField().getCardType());
            Assertions.assertEquals(new ArrayList<Card>(), player.getHand().getHandPile());

            boss.getField().clear();
        }
    }
}
