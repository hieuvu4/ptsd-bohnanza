import java.util.ArrayList;
import java.util.List;

public class Player {
    private final Hand hand;
    private Field[] fields;
    private final List<Card> tradedCards;
    private final List<Card> coins;
    private Phase phase;

    public Player() {
        this.hand = new Hand();
        this.fields = new Field[2];
        tradedCards = null;
        coins = new ArrayList<>();

        for(int i = 0; i < 2; i++) fields[i] = new Field();
    }

    /**
     * Player plants a card to the specified field. If the field is not empty, an IllegalMoveException will be
     * thrown.
     * @param field the given field where the card should be planted on
     * @throws IllegalMoveException if the given field is not empty
     */
    public void plant(int field) throws IllegalMoveException {
        phase.plant(this, field);
    }

    /**
     * Player tries to harvest the given field. If any field is empty, an IllegalMoveException will be thrown.
     * If the amount of cards of the given field is 1 and there is a field with more amount of cards, an
     * IllegalMoveException will be thrown. If the field has more than one card or all fields only have 1 card each,
     * the field can be harvested.
     * @param fieldNumber indicates which field to harvest
     * @throws IllegalMoveException if player violates the game rule
     */
    public void harvest(int fieldNumber) throws IllegalMoveException {
        phase.harvest(this, fieldNumber);
    }

    public void tradeCards(List<Card> send, List<Card> receive) {

    }

    /**
     * Player tries to draw three cards from a given pile. The cards will be added at the bottom of the hand pile.
     * The state of the player will change to PhaseOut. If there is not enough cards left, the game ends.
     * @param pile the given pile
     */
    public void drawCards(Pile pile) throws IllegalMoveException {
        phase.drawCards(this, pile);
        this.phase = new PhaseOut();
    }

    public void buyThirdField(List<Card> payedCoins) {

    }

    public Field getField(int index) {
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

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public List<Card> getTradedCards(List<Card> tradedCards) {
        return tradedCards;
    }
}
