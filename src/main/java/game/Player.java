package game;

import game.cards.Card;
import game.cards.CardType;
import game.mafia.Boss;
import game.phases.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Player extends Observable {
    private final String name;
    private final Hand hand;
    private Field[] fields;
    private final List<Card> coins;
    private Phase phase;
    private final GameField gameField;

    private List<Card> tradedCards = null;

    private boolean planted = false;
    private boolean traded = false;
    private boolean drawn = false;
    private boolean bought = false;

    public Player(String name, GameField gameField) {
        this.name = name;
        this.hand = new Hand();
        this.fields = new Field[2];
        coins = new ArrayList<>();
        phase = new PhaseOut();
        this.gameField = gameField;

        if(!gameField.getExtension()) {
            tradedCards = new ArrayList<>();
        }

        for(int i = 0; i < 2; i++) fields[i] = new Field();
    }

    /**
     * Player tries to offer cards for a specific trading card while trading phase. If the player is not in the correct
     * phase, an IllegalMoveException will be thrown.
     * @param cards the offered cards
     * @param tradingCardFieldNumber trading field number with the trading card
     * @throws IllegalMoveException if not in correct phase
     */
    public void offerCards(final List<Card> cards, final int tradingCardFieldNumber) throws IllegalMoveException {
        phase.offerCards(this, cards, tradingCardFieldNumber);
    }

    /**
     * Player tries to check if there are any offers. If the player is not in the correct phase, an
     * IllegalMoveException will be thrown.
     * @throws IllegalMoveException if not in correct phase
     */
    public void checkOffers() throws IllegalMoveException {
        phase.checkOffers(this);
    }

    /**
     * Player tries to accept an offer of another player. The player gets the offered cards of the other player while
     * the other player gets the trading card. If the player is not in the correct phase, an IllegalMoveException
     * will be thrown.
     * @param other the other player who gets the trading card
     * @param tradingCardFieldNumber trading field number with the trading card
     * @throws IllegalMoveException if not in correct phase
     */
    public void acceptOffer(final Player other, final int tradingCardFieldNumber) throws IllegalMoveException {
        phase.acceptOffer(this, other, tradingCardFieldNumber);
    }

    /**
     * Player tries to take the trading card. If the player is not in the correct phase, an IllegalMoveException
     * will be thrown.
     * @param tradingCardFieldNumber trading field number with the trading card.
     * @throws IllegalMoveException if not in correct phase
     */
    public void takeTradingCards(final int tradingCardFieldNumber) throws IllegalMoveException {
        phase.takeTradingCard(this, tradingCardFieldNumber);
    }

    /**
     * Player plants a card to the specified field. If the field is not empty, an IllegalMoveException will be
     * thrown.
     * @param fieldNumber the given field where the card should be planted on
     * @throws IllegalMoveException if the given field is not empty
     */
    public void plant(final int fieldNumber, final Card card) throws IllegalMoveException {
        phase.plant(this, fieldNumber, card);
        if (phase instanceof PhasePlanting)
            planted = true;
    }

    /**
     * Player tries to harvest the given field. If any field is empty, an IllegalMoveException will be thrown.
     * If the amount of cards of the given field is 1 and there is a field with more amount of cards, an
     * IllegalMoveException will be thrown. If the field has more than one card or all fields only have 1 card each,
     * the field can be harvested.
     * @param fieldNumber indicates which field to harvest
     * @throws IllegalMoveException if player violates the game rule
     */
    public void harvest(final int fieldNumber) throws IllegalMoveException {
        phase.harvest(this, fieldNumber);
    }

    /**
     * Player tries to take the discover card. If the player is not in the correct phase, an IllegalMoveException
     * will be thrown.
     * @param discoverCardFieldNumber discover field number with the discover card.
     * @throws IllegalMoveException if not in correct phase
     */
    public void takeDiscoverCards(final int discoverCardFieldNumber) throws IllegalMoveException {
        phase.takeDiscoverCards(this, discoverCardFieldNumber);
    }

    public void putDiscoverCardsToDiscard(final int discoverCardFieldNumber) throws IllegalMoveException {
        phase.putDiscoverCardsToDiscard(this, discoverCardFieldNumber);
    }

    public void cultivateOwnField(int discoverCardFieldNumber) throws IllegalMoveException {
        phase.cultivateOwnField(this, discoverCardFieldNumber);
    }

    public void cultivateBossField(int discoverCardFieldNumber, Boss boss) throws IllegalMoveException {
        phase.cultivateBossField(this, discoverCardFieldNumber, boss);
    }

    public void giveBossCardFromHand(final Card card, final Boss boss) throws IllegalMoveException {
        phase.giveBossCardFromHandPile(this, card, boss);
    }

    /**
     * Player tries to draw three cards from a given pile. This move is only possible in phase 4. The cards will be
     * added at the bottom of the hand pile. The state of the player will change from phase 4 to PhaseOut. If there are
     * not enough cards left, the game ends.
     * @param pile the given pile
     */
    public void drawCards(final Pile pile) throws IllegalMoveException {
        phase.drawCards(this, pile);
        drawn = true;
    }

    /**
     * Player tries to buy a third field. If player doesn't have enough coins to buy the third field, an
     * IllegalMoveException will be thrown.
     * @throws IllegalMoveException if not enough coins
     */
    public void buyThirdField() throws IllegalMoveException {
        if(bought) throw new IllegalMoveException("Player " + this.getName() + ": Already bought a field.");
        int price = (gameField.getExtension())? 4 : 3;
        if(coins.size() < price) throw new IllegalMoveException("Player " + this.getName()
                + ": Not enough coins to buy a third field.");
        bought = true;
        Field[] newFields = new Field[fields.length + 1];
        for (int i = 0; i < fields.length; i++) newFields[i] = fields[i];
        newFields[fields.length] = new Field();
        fields = newFields;
        for(int i = 0; i < price; i++) {
            Card card = coins.getFirst();
            coins.removeFirst();
            gameField.getPile().getDiscardPile().add(card);
        }
    }

    public Field getField(final int index) {
        return fields[index];
    }

    public Field[] getFields() {
        return fields;
    }

    public Hand getHand() {
        return hand;
    }

    /**
     * Returns player's current score.
     * @return integer which represents the score
     */
    public int getScore() {
        return coins.size();
    }

    public List<Card> getCoins() {
        return coins;
    }

    public void setPhase(final Phase phase) {
        this.phase = phase;
    }

    public boolean getDrawn() {
        return drawn;
    }

    public Phase getPhase() {
        return phase;
    }

    public String getName() {
        return name;
    }

    public GameField getGameField() {
        return gameField;
    }

    public List<Card> getTradedCards() {
        return tradedCards;
    }

    /**
     * Brings the player to the next Phase if certain conditions are fulfilled.
     * @throws IllegalMoveException if the conditions are not fulfilled
     */
    public void nextPhase() throws IllegalMoveException {
        if(gameField.getExtension()) {
            handleExtensionPhases();
        }
        else {
            handlePhases();
        }

    }

    private void handlePhases() throws IllegalMoveException {
        switch(phase) {
            case PhasePlanting p1:
                if (!(hand.getHandPile().isEmpty() || planted))
                    throw new IllegalMoveException("Player " + this.name
                            + ": A card from the hand pile should be planted.");
                planted = false;
                phase = new PhaseTrading();
                setChanged();
                notifyObservers(phase);
                break;
            case PhaseTrading p2:
                if(gameField.getTradingArea().getTradingCards()[0] == null
                        && gameField.getTradingArea().getTradingCards()[1] == null) traded = true;
                if(!traded) throw new IllegalMoveException("Player " + this.name + ": Trading is not finished yet.");
                traded = false;
                phase = new PhasePlantingTraded();
                setChanged();
                notifyObservers(phase);
                break;
            case PhasePlantingTraded p3:
                boolean empty = getGameField().getPlayers().stream()
                        .map(Player::getTradedCards)
                        .allMatch(List::isEmpty);
                if (!empty) throw new IllegalMoveException("Player " + this.name
                        + ": Traded cards should be planted.");
                phase = new PhaseDrawing();
                setChanged();
                notifyObservers(phase);
                break;
            case PhaseDrawing p4:
                if (!drawn) throw new IllegalMoveException("Player " + this.name + ": Player should have draw cards.");
                drawn = false;
                phase = new PhaseOut();
                setChanged();
                notifyObservers(phase);
                break;
            default:
                throw new IllegalMoveException("Player " + this.name
                        + ": Unable to perform this action in the current phase.");
        }
    }

    private void handleExtensionPhases() throws IllegalMoveException {
        switch(phase) {
            case PhaseUsing phaseUsing:
                boolean checkDiscoverFieldsEmpty = true;
                for(int i = 0; i < 3; i++) {
                    if(gameField.getDiscoverArea().getDiscoverFields().get(i).getCardType() != null) {
                        checkDiscoverFieldsEmpty = false;
                        break;
                    };
                }
                if(!checkDiscoverFieldsEmpty) throw new IllegalMoveException("Discover Fields should be empty.");

                phase = new PhaseGiving();
                setChanged();
                notifyObservers(phase);
                break;
            case PhaseGiving phaseGiving:
                phase = new PhasePlanting();
                setChanged();
                notifyObservers(phase);
                break;
            case PhasePlanting phasePlanting:
                if (!(hand.getHandPile().isEmpty() || planted))
                    throw new IllegalMoveException("Player " + this.name
                            + ": A card from the hand pile should be planted.");
                planted = false;
                phase = new PhaseRevealing();
                setChanged();
                notifyObservers(phase);
                break;
            case PhaseRevealing phaseRevealing:

                phase = new PhaseCultivating();
                setChanged();
                notifyObservers(phase);
                break;
            case PhaseCultivating phaseCultivating:

                // only if 1 player
                if(gameField.getPlayers().size() == 1) {
                    checkDiscoverFieldsEmpty();
                }

                if(checkBossEmpty() && !hand.getHandPile().isEmpty())
                    throw new IllegalMoveException("Player " + this.name + " should give a boss a card.");

                phase = new PhaseDrawing();
                setChanged();
                notifyObservers(phase);
                break;
            case PhaseDrawing phaseDrawing:
                if (!drawn) throw new IllegalMoveException("Player " + this.name + ": Player should have draw cards.");
                drawn = false;
                phase = new PhaseOut();
                setChanged();
                notifyObservers(phase);
                break;
            default:
                throw new IllegalMoveException("Player " + this.name
                        + ": Unable to perform this action in the current phase.");
        }
    }

    private void checkDiscoverFieldsEmpty() throws IllegalMoveException {
        if(gameField.getPlayers().size() == 1
                && !(gameField.getDiscoverArea().getDiscoverFields().get(0).getCardType() == null
                && gameField.getDiscoverArea().getDiscoverFields().get(1).getCardType() == null
                && gameField.getDiscoverArea().getDiscoverFields().get(2).getCardType() == null)
        ) throw new IllegalMoveException("Discover Fields are not empty.");
    }

    private boolean checkBossEmpty() {
        Boss[] bosses = (gameField.getPlayers().size() == 1) ?
                new Boss[]{gameField.getAlCabohne(), gameField.getDonCorlebohne(), gameField.getJoeBohnano()} :
                new Boss[]{gameField.getAlCabohne(), gameField.getDonCorlebohne()};

        for (Boss boss : bosses) {
            if(boss.getField().isEmpty()) return true;
        }
        return false;
    }
}