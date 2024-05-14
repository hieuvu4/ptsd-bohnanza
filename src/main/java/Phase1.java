public class Phase1 extends Phase {

    @Override
    public void plant(Player player, int fieldNumber, Card card) throws IllegalMoveException {
        if (card != player.getHand().popTopCard())
            throw new IllegalMoveException("The given card is not the first card.");

        Field currentField = player.getField(fieldNumber);
        if(!currentField.isEmpty() && currentField.getCardType() != card)
            throw new IllegalMoveException("The card type is not the same.");

        if(currentField.getCardType() == card || currentField.isEmpty()) {
            currentField.setCardType(card);
            currentField.addCardToField();
            player.getHand().removeCard(0);
        }
    }
}
