package game;

import game.phases.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Player extends Observable {
    private final String name;
    private final Hand hand;
    private Field[] fields;
    private final List<Card> tradedCards;
    private final List<Card> coins;
    private Phase phase;
    private final GameField gameField;

    private boolean planted = false;
    private boolean traded = false;
    private boolean drawn = false;
    private boolean bought = false;

    public Player(String name, GameField gameField) {
        this.name = name;
        this.hand = new Hand();
        this.fields = new Field[2];
        tradedCards = new ArrayList<>();
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
        if(coins.size() < 3) throw new IllegalMoveException("Player " + this.getName()
                + ": Not enough coins to buy a third field.");
        bought = true;
        Field[] newFields = new Field[3];
        newFields[0] = fields[0];
        newFields[1] = fields[1];
        newFields[2] = null;
        fields = newFields;
        for(int i = 0; i < 3; i++) {
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

    public List<Card> getTradedCards() {
        return tradedCards;
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
            case Phase1 p1:
                if (!(hand.getHandPile().isEmpty() || planted))
                    throw new IllegalMoveException("Player " + this.name
                            + ": A card from the hand pile should be planted.");
                planted = false;
                phase = new Phase2();
                setChanged();
                notifyObservers(phase);
                break;
            case Phase2 p2:
                if(gameField.getTradingArea().getTradingCards()[0] == null
                        && gameField.getTradingArea().getTradingCards()[1] == null) traded = true;
                if(!traded) throw new IllegalMoveException("Player " + this.name + ": Trading is not finished yet.");
                traded = false;
                phase = new Phase3();
                setChanged();
                notifyObservers(phase);
                break;
            case Phase3 p3:
                if (!tradedCards.isEmpty()) throw new IllegalMoveException("Player " + this.name
                        + ": Traded cards should be planted.");
                phase = new Phase4();
                setChanged();
                notifyObservers(phase);
                break;
            case Phase4 p4:
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
}