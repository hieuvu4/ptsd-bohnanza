import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Player {
    private final Hand hand;
    private Field[] fields;
    private final List<Card> tradedCards;
    private final List<Card> coins;

    public Player() {
        this.hand = new Hand();
        this.fields = new Field[2];
        tradedCards = null;
        coins = new ArrayList<>();
    }

    public void plant(int field, Card card) {

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
        // check if any field is null
        if (Arrays.stream(fields).anyMatch(Objects::isNull)) throw new IllegalMoveException("A field is empty.");
        int maxAmount = Arrays.stream(fields).mapToInt(Field::getCardAmount).max().orElse(0);
        if (maxAmount > 1 && fields[fieldNumber].getCardAmount() > 1 || maxAmount == 1) {
            harvestValidField(fieldNumber);
        } else {
            throw new IllegalMoveException("Field cannot be harvested.");
        }
    }

    public void tradeCards(List<Card> send, List<Card> receive) {

    }

    /**
     * Player tries to draw three cards from a given pile. The cards will be added at the bottom of the hand pile.
     * If there is not enough cards left, the game ends.
     * @param pile the given pile
     */
    public void drawCards(Pile pile) {
        IntStream.range(0, 3).forEach(i -> {hand.addCard(pile.drawCard());});
    }

    public void buyThirdField(List<Card> payedCoins) {

    }

    public Field getField(int index) {
        return fields[index];
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


    private void harvestValidField(int fieldNumber) throws IllegalMoveException {
        Card cardType = fields[fieldNumber].getCardType();
        int currentAmount = fields[fieldNumber].getCardAmount();
        int coinAmount = fields[fieldNumber].harvest();
        IntStream.range(0, coinAmount).forEach(i -> {coins.add(cardType);});
        IntStream.range(0, currentAmount-coinAmount).forEach(i -> {
            // TODO: add to discard pile
        });
    }
}
