import java.util.NoSuchElementException;

public class Phase1 extends Phase {

    @Override
    public void plant(final Player player, final int fieldNumber, final Card card) throws IllegalMoveException {
        if(player.getHand().getHandPile().isEmpty())
            throw new NoSuchElementException("There are no cards in the hand.");

        if (card != player.getHand().popTopCard())
            throw new IllegalMoveException("The given card is not the first card.");

        Field currentField = player.getField(fieldNumber);
        if(!currentField.isEmpty() && currentField.getCardType() != card)
            throw new IllegalMoveException("The given card type is not the same.");

        if(currentField.getCardType() == card || currentField.isEmpty()) {
            currentField.setCardType(card);
            currentField.increaseCardAmount();
            player.getHand().removeCard(0);
        }
    }
}
