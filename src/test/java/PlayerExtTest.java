import game.*;
import game.cards.Card;
import game.cards.CardType;
import game.mafia.AlCabohne;
import game.mafia.Boss;
import game.mafia.DonCorlebohne;
import game.mafia.JoeBohnano;
import game.phases.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerExtTest {

    private Player player;
    private Pile pile;
    private GameField gameField;
    private DiscoverArea discoverArea;

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
    }

    @Test
    public void testPlantNoCards() {
        player.setPhase(new PhasePlanting());
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            player.plant(0, new Card(CardType.BRECHBOHNE));
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": There are no cards in the hand.", exception.getMessage());
    }

    @Test
    public void testPlantOne() throws IllegalMoveException {
        player.setPhase(new PhasePlanting());
        Card card = new Card(CardType.BRECHBOHNE);
        player.getHand().addCard(card);
        player.plant(0, card);

        Assertions.assertEquals(CardType.BRECHBOHNE, player.getField(0).getCardType());
        Assertions.assertEquals(1, player.getField(0).getCardAmount());
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 12})
    public void testPlantMoreThanOne(int amount) throws IllegalMoveException {
        player.setPhase(new PhasePlanting());
        for(int i = 0; i < 2; i++) {
            Card card = new Card(CardType.BRECHBOHNE);
            player.getHand().addCard(card);
            player.plant(0, player.getHand().peekTopCard());
        }
        for(int i = 0; i < amount; i++) {
            Card card = new Card(CardType.BRECHBOHNE);
            player.getHand().addCard(card);
            Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
                player.plant(0, player.getHand().peekTopCard());
            });
            Assertions.assertEquals("Player "
                    + player.getName() + ": Can't plant a third time.", exception.getMessage());
        }
    }

    @Test
    public void testPlantWrongCard() {
        player.setPhase(new PhasePlanting());
        player.getHand().addCard(new Card(CardType.BRECHBOHNE));
        player.getHand().addCard(new Card(CardType.BLAUE_BOHNE));

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.plant(0, new Card(CardType.BLAUE_BOHNE));
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": The given card is not the first card.", exception.getMessage());
    }

    @Test
    public void testPlantSameFieldWrongType() throws IllegalMoveException {
        player.setPhase(new PhasePlanting());
        Card card = new Card(CardType.BRECHBOHNE);
        Card card2 = new Card(CardType.BLAUE_BOHNE);
        player.getHand().addCard(card);
        player.getHand().addCard(card2);
        player.plant(0, card);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.plant(0, card2);
        });
        Assertions.assertEquals(CardType.BRECHBOHNE, player.getField(0).getCardType());
        Assertions.assertEquals(1, player.getField(0).getCardAmount());
        Assertions.assertEquals("Player " + player.getName()
                + ": The given card type is not the same.", exception.getMessage());
    }

    @Test
    public void testPlantWrongPhase() {
        Phase[] phases = {new PhaseUsing(), new PhaseGiving(), new PhaseRevealing(), new PhaseCultivating(),
                new PhaseDrawing()};
        for(Phase phase : phases) {
            player.setPhase(phase);
            player.getHand().addCard(new Card(CardType.BRECHBOHNE));

            Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
                player.plant(0, new Card(CardType.BRECHBOHNE));
            });
            Assertions.assertEquals("Player " + player.getName()
                    + ": Unable to perform this action in the current phase.", exception.getMessage());
        }
    }

    @Test
    public void testHarvestAllFieldsEmpty() {
        player.setPhase(new PhasePlanting());
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.harvest(0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Field cannot be harvested because a field is empty.", exception.getMessage());
    }

    @Test
    public void testHarvestOnlyOneFieldPlanted() throws IllegalMoveException {
        player.setPhase(new PhasePlanting());
        Card card = new Card(CardType.BRECHBOHNE);
        player.getHand().addCard(card);
        player.plant(0, card);
        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.harvest(0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Field cannot be harvested because a field is empty.", exception.getMessage());
    }

    @Test
    public void testHarvestOneCardEachField() throws IllegalMoveException {
        player.setPhase(new PhasePlanting());

        Card card1 = new Card(CardType.BRECHBOHNE);
        Card card2 = new Card(CardType.BLAUE_BOHNE);

        player.getHand().addCard(card1);
        player.getHand().addCard(card2);
        player.plant(0, card1);
        player.plant(1, card2);
        player.harvest( 0);

        Assertions.assertNull(player.getField(0).getCardType());
        Assertions.assertEquals(CardType.BLAUE_BOHNE, player.getField(1).getCardType());
    }

    @Test
    public void testHarvest() throws IllegalMoveException {
        Phase[] phases = {new PhasePlanting(), new PhaseCultivating(), new PhaseDrawing(), new PhaseGiving(),
                new PhaseUsing(), new PhaseOut()};

        for(Phase phase : phases) {
            Player player = new Player("", gameField);
            player.setPhase(new PhasePlanting());
            Card card1 = new Card(CardType.BRECHBOHNE);
            Card card2 = new Card(CardType.BLAUE_BOHNE);
            player.getHand().addCard(card1);
            player.getHand().addCard(card2);
            player.plant(0, card1);
            player.plant(1, card2);
            player.setPhase(phase);
            player.harvest(0);

            Assertions.assertNull(player.getField(0).getCardType());
            Assertions.assertEquals(CardType.BLAUE_BOHNE, player.getField(1).getCardType());
        }
    }

    @Test
    public void testDrawCards() throws IllegalMoveException {
        player.setPhase(new PhaseDrawing());
        player.drawCards(pile);

        Assertions.assertEquals(2, player.getHand().getHandPile().size());
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
        player.setPhase(new PhaseRevealing());

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.harvest(0);
        });
        Assertions.assertEquals("Player " + player.getName()
                + ": Unable to perform this action in the current phase.", exception.getMessage());
    }

    @Test
    public void testDrawCardsTwoTimes() throws IllegalMoveException {
        player.setPhase(new PhaseDrawing());
        player.drawCards(pile);

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.drawCards(pile);
        });

        Assertions.assertEquals("Player " + player.getName()
                + ": Already drawn three cards.", exception.getMessage());
    }

    @Test
    public void testDrawCardsWrongPhase() {
        Phase[] phases = {new PhaseUsing(), new PhaseGiving(), new PhasePlanting(), new PhaseRevealing(),
                new PhaseCultivating(), new PhaseOut()};

        for (Phase phase : phases) {
            player.setPhase(phase);

            Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
                player.drawCards(pile);
            });

            Assertions.assertEquals("Player " + player.getName()
                            + ": Unable to perform this action in the current phase.",
                    exception.getMessage());
        }
    }

    @Test
    public void testBuyThirdField() throws IllegalMoveException {
        for(int i = 0; i < 4; i++) player.getCoins().add(new Card(CardType.BRECHBOHNE));
        player.buyThirdField();

        Assertions.assertEquals(3, player.getFields().length);
        Assertions.assertEquals(0, player.getCoins().size());
    }

    @Test
    public void testBuyThirdFieldNotEnoughCoins() {
        for(int i = 0; i < 2; i++) player.getCoins().add(new Card(CardType.BRECHBOHNE));

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.buyThirdField();
        });

        Assertions.assertEquals(2, player.getFields().length);
        Assertions.assertEquals("Player " + player.getName()
                + ": Not enough coins to buy a third field.", exception.getMessage());
    }

    @Test
    public void testBuyThirdFieldTwoTimes() throws IllegalMoveException {
        for(int i = 0; i < 10; i++) player.getCoins().add(new Card(CardType.BRECHBOHNE));
        player.buyThirdField();

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.buyThirdField();
        });

        Assertions.assertEquals(3, player.getFields().length);
        Assertions.assertEquals("Player " + player.getName() + ": Already bought a field.",
                exception.getMessage());
    }

    @Test
    public void testTakeDiscoverCardsNull() {
        discoverArea.getDiscoverFields().get(0).setCardType(null);
        player.setPhase(new PhaseUsing());

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
           player.takeDiscoverCards(0);
        });

        Assertions.assertEquals("There is no discover card in this field.", exception.getMessage());
    }

    @Test
    public void testTakeDiscoverCardsNoSuchCardInField() {
        discoverArea.getDiscoverFields().get(0).setCardType(CardType.BRECHBOHNE);
        player.getField(0).setCardType(CardType.BLAUE_BOHNE);
        player.getField(1).setCardType(CardType.FEUERBOHNE);
        player.setPhase(new PhaseUsing());

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.takeDiscoverCards(0);
        });

        Assertions.assertEquals("There is no such card in player " + player.getName()
                + "'s field.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 12, 50})
    public void testTakeDiscoverCards(int amount) throws IllegalMoveException {
        discoverArea.getDiscoverFields().get(0).setCardType(CardType.BRECHBOHNE);
        for(int i = 0; i < amount; i++) discoverArea.getDiscoverFields().get(0).increaseCardAmount();
        player.getField(0).setCardType(CardType.BLAUE_BOHNE);
        player.getField(1).setCardType(CardType.BRECHBOHNE);
        player.getField(0).increaseCardAmount();
        player.getField(1).increaseCardAmount();
        player.setPhase(new PhaseUsing());
        player.takeDiscoverCards(0);

        Assertions.assertEquals(amount+1, player.getField(1).getCardAmount());
        Assertions.assertNull(discoverArea.getDiscoverFields().get(1).getCardType());
    }


    @Test
    public void testPutDiscoverCardsToDiscard() {
        discoverArea.getDiscoverFields().get(0).setCardType(null);
        player.setPhase(new PhaseUsing());

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.putDiscoverCardsToDiscard(0);
        });

        Assertions.assertEquals("There is no discover card in this field.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 12, 50})
    public void testPutDiscoverCardsNoSuchCardInField(int amount) throws IllegalMoveException {
        discoverArea.getDiscoverFields().get(0).setCardType(CardType.BRECHBOHNE);
        for (int i = 0; i < amount; i++) discoverArea.getDiscoverFields().get(0).increaseCardAmount();
        player.setPhase(new PhaseUsing());
        player.putDiscoverCardsToDiscard(0);

        Assertions.assertEquals(amount, pile.getDiscardPile().size());
        Assertions.assertNull(discoverArea.getDiscoverFields().get(0).getCardType());
    }

    @Test
    public void testCultivateOwnFieldDiscoverFieldNull() {
        discoverArea.getDiscoverFields().get(0).setCardType(null);
        player.setPhase(new PhaseCultivating());

        Exception exception = Assertions.assertThrows(IllegalMoveException.class, () -> {
            player.cultivateOwnField(0);
        });
        Assertions.assertEquals("There is no discover card in this field.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-2, -1, 4, 6})
    public void testCultivateOwnFieldWrongNumber(int amount) {
        player.setPhase(new PhaseCultivating());
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
        player.setPhase(new PhaseCultivating());
        player.cultivateOwnField(0);

        Assertions.assertNull(discoverArea.getDiscoverFields().get(0).getCardType());
        Assertions.assertEquals(amount, player.getField(0).getCardAmount());
    }


    @ParameterizedTest
    @ValueSource(ints = {2, 4, 5, 7, 9, 50})
    public void testCultivateOwnFieldNotNullField(int amount) throws IllegalMoveException {
        discoverArea.getDiscoverFields().get(0).setCardType(CardType.BRECHBOHNE);
        for(int i = 0; i < amount; i++) discoverArea.getDiscoverFields().get(0).increaseCardAmount();
        player.setPhase(new PhaseCultivating());
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
        player.setPhase(new PhaseCultivating());
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
        player.setPhase(new PhaseCultivating());
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
        player.setPhase(new PhaseCultivating());
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
        player.setPhase(new PhaseCultivating());
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
        player.setPhase(new PhaseCultivating());
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
        player.setPhase(new PhaseCultivating());
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
        player.setPhase(new PhaseCultivating());
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
        player.setPhase(new PhaseCultivating());
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
        player.setPhase(new PhaseCultivating());
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
