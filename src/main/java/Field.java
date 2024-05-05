

public class Field {
    private Card cardType;
    private int cardAmount;

    public Field() {

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
