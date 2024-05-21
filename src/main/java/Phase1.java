import java.util.NoSuchElementException;

public class Phase1 extends Phase {
    /**
     * Player tries to plant a given card to a certain field. If the field is occupied, the player doesn't have any
     * hand pile or the given card doesn't exist in the hand pile, the method will throw an IllegalMoveException.
     * @param player the player who tries to plant
     * @param fieldNumber the index of the field
     * @param card the card which should be planted
     * @throws IllegalMoveException if hand pile is empty, no such card exists in the hand pile or the field is occupied
     */
    @Override
    public void plant(final Player player, final int fieldNumber, final Card card) throws IllegalMoveException {
        if(player.getHand().getHandPile().isEmpty())
            throw new NoSuchElementException("Player " + player.getName() + ": There are no cards in the hand.");

        if (card != player.getHand().popTopCard())
            throw new IllegalMoveException("Player " + player.getName() + ": The given card is not the first card.");

        Field currentField = player.getField(fieldNumber);
        if(!currentField.isEmpty() && currentField.getCardType() != card)
            throw new IllegalMoveException("Player " + player.getName() + ": The given card type is not the same.");

        if(currentField.getCardType() == card || currentField.isEmpty()) {
            currentField.setCardType(card);
            currentField.increaseCardAmount();
            player.getHand().removeCard(0);
        }
    }
}
