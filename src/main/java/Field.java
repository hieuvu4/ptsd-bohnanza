

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
        cardAmount += 1;
    }

    public boolean isEmpty() {
        return cardAmount == 0;
    }

    public int harvest() {
        int profit = 0;
        int index = cardType.getNumberNeedToHarvest().length-1;
        for (int i = index; i > 0; i--) {
            if(cardType.getNumberNeedToHarvest()[i] <= cardAmount){
                profit = cardType.getCoins()[i];
                break;
            }
        }
        cardAmount = 0;
        cardType = null;
        return profit;
    }
}
