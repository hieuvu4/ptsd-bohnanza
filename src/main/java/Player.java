import java.util.List;

public class Player {
    private final Hand hand;
    private Field[] fields;
    private final List<Card> tradedCards;
    private final List<Card> coins;
    private int score;

    public Player() {
        this.hand = new Hand();
        this.fields = new Field[2];
        tradedCards = null;
        coins = null;
        score = 0;
    }

    public void plant(int field, Card card) {

    }

    /**
     * Try to
     * @param fieldNumber
     * @throws IllegalMoveException
     */
    public void harvest(int fieldNumber) throws IllegalMoveException {
        for(Field field: fields) if(field == null) throw new IllegalMoveException();
        int maxAmount = 0;
        for(Field field: fields) if(field.getCardAmount() > maxAmount) maxAmount = field.getCardAmount();
        if(maxAmount > 1 && fields[fieldNumber].getCardAmount() > 1 || maxAmount == 1) {
            score += fields[fieldNumber].harvest();
        } else {
            throw new IllegalMoveException("Field cannot be harvested.");
        }
    }

    public void tradeCards(List<Card> send, List<Card> receive) {

    }

    public void drawCards(Pile pile) {

    }

    public void buyThirdField(List<Card> payedCoins) {

    }

    public int getScore() {
        return score;
    }

    public Field getField(int index) {
        return fields[index];
    }

    public Hand getHand() {
        return hand;
    }
}
