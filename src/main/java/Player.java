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

    public void doAction(Action action) throws IllegalMoveException {
        action.execute();
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

    public void setPlanted(boolean planted) {
        this.planted = planted;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }

    public boolean getBought() {
        return bought;
    }

    public void setFields(final Field[] fields) {
        this.fields = fields;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
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
