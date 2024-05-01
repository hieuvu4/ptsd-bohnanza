

public class Field {
    private final int fieldNumber;
    private Card cardType;
    private int cardAmount;

    public Field(int fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public Card getCardType() {
        return cardType;
    }

    public void setCardType(Card card) {
        this.cardType = card;
    }

    public int getCardAmount() {
        return cardAmount;
    }

    public void addCardToField() {

    }

    public boolean isEmpty() {
        return false;
    }

    public int harvest() {
        return -1;
    }
}
