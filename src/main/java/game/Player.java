package game;

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

    private boolean planted = false;
    private boolean drawn = false;
    private boolean bought = false;

    public Player(String name, GameField gameField) {
        this.name = name;
        this.hand = new Hand();
        this.fields = new Field[2];
        coins = new ArrayList<>();
        phase = new PhaseOut();
        this.gameField = gameField;

        for(int i = 0; i < 2; i++) fields[i] = new Field();
    }

    /**
     * Player plants a card to the specified field. If the field is not empty, an IllegalMoveException will be
     * thrown.
     * @param fieldNumber the given field where the card should be planted on
     * @throws IllegalMoveException if the given field is not empty
     */
    public void plant(final int fieldNumber, final Card card) throws IllegalMoveException {
        phase.plant(this, fieldNumber, card);
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
     * Player tries to take the trading card. If the player is not in the correct phase, an IllegalMoveException
     * will be thrown.
     * @param tradingCardFieldNumber trading field number with the trading card.
     * @throws IllegalMoveException if not in correct phase
     */
    public void takeTradingCards(final int tradingCardFieldNumber) throws IllegalMoveException {
        phase.takeTradingCards(this, tradingCardFieldNumber);
    }

    public void putTradingCardsToDiscard(final int tradingCardFieldNumber) throws IllegalMoveException {
        phase.putTradingCardsToDiscard(this, tradingCardFieldNumber);
    }

    public void cultivateOwnField(int tradingCardFieldNumber) throws IllegalMoveException {
        phase.cultivateOwnField(this, tradingCardFieldNumber);
    }

    public void cultivateBossField(int tradingCardFieldNumber, Boss boss) throws IllegalMoveException {
        phase.cultivateBossField(this, tradingCardFieldNumber, boss);
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
        int price = 4;
        if(coins.size() < price) throw new IllegalMoveException("Player " + this.getName()
                + ": Not enough coins to buy a third field.");
        bought = true;
        Field[] newFields = new Field[fields.length + 1];
        for (int i = 0; i < fields.length; i++) newFields[i] = fields[i];
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

    /**
     * Brings the player to the next Phase if certain conditions are fulfilled.
     * @throws IllegalMoveException if the conditions are not fulfilled
     */
    public void nextPhase() throws IllegalMoveException {
        switch(phase) {
            case PhaseUsing phaseUsing:
                boolean checkTradingFieldsEmpty = true;
                for(int i = 0; i < 3; i++) {
                    if(gameField.getTradingArea().getTradingFields().get(i) == null) {
                        checkTradingFieldsEmpty = false;
                        break;
                    };
                }
                if(!checkTradingFieldsEmpty) throw new IllegalMoveException("Trading Fields should be empty.");

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
                checkTradingFieldsEmpty();

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

    private void checkTradingFieldsEmpty() throws IllegalMoveException {
        if(gameField.getPlayers().size() == 1
                && !(gameField.getTradingArea().getTradingFields().get(0).getCardType() == null
                && gameField.getTradingArea().getTradingFields().get(1).getCardType() == null
                && gameField.getTradingArea().getTradingFields().get(2).getCardType() == null)
        ) throw new IllegalMoveException("Trading Fields are not empty.");
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