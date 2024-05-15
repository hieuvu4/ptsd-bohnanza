

public class Field {
    private Card cardType;
    private int cardAmount;

    public Field() {
        cardType = null;
        cardAmount = 0;
    }

    public Card getCardType() {
        return cardType;
    }

    public void setCardType(final Card card) {
        this.cardType = card;
    }

    public int getCardAmount() {
        return cardAmount;
    }

    /**
     * Adds a card to this field and increases the card amount by 1.
     */
    public void increaseCardAmount() {
        cardAmount++;
    }

    /**
     * Checks if the field is empty.
     * @return  true if field is empty else false
     */
    public boolean isEmpty() {
        return cardType == null && cardAmount == 0;
    }

    /**
     * Returns the amount of coins of the current card type with the current amount. The card type resets to null and
     * the amount of cards will be set to 0.
     * @return  amount of coins
     * @throws  IllegalMoveException if the field is empty
     */
    public int harvest() throws IllegalMoveException {
        if(isEmpty()) throw new IllegalMoveException("Field can't be harvested because field is empty.");
        int coins = cardType.getCoinValue(cardAmount);
        cardType = null;
        cardAmount = 0;
        return coins;
    } // Kann ich irgendwie auch erstmal nur diese Zeile committen/pushen?
}
