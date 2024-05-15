public class Phase3 extends Phase {

    @Override
    public void plant(Player player, int fieldNumber, Card card) throws IllegalMoveException {
        if (player.getTradedCards().isEmpty())
            throw new IllegalMoveException("There are no traded cards in this player.");
        if (!player.getTradedCards().contains(card))
            throw new IllegalMoveException("There is no such card in traded cards in this player.");

        Field currentField = player.getField(fieldNumber);
        if(currentField.getCardType() == card || currentField.isEmpty()) {
            currentField.setCardType(card);
            currentField.addCardToField();
            player.getTradedCards().remove(card);
        }
    }
}