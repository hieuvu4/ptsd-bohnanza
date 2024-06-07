package game.phases;

import game.Card;
import game.Field;
import game.IllegalMoveException;
import game.Player;

public class Phase3 extends Phase {

    /**
     * Player tries to plant a given card to a certain field. If the field is occupied, the player doesn't have any
     * traded cards or the given card doesn't exist in the traded cards, the method will throw an
     * IllegalMoveException.
     * @param player the player who tries to plant
     * @param fieldNumber the index of the field
     * @param card the card which should be planted
     * @throws IllegalMoveException if card doesn't exist, list of traded cards is empty or field is occupied
     */
    @Override
    public void plant(final Player player, final int fieldNumber, final Card card) throws IllegalMoveException {
        if (player.getTradedCards().isEmpty())
            throw new IllegalMoveException("Player " + player.getName()
                    + ": There are no traded cards in this player.");

        if (!player.getTradedCards().contains(card))
            throw new IllegalMoveException("Player " + player.getName()
                    + ": There is no such card in traded cards in this player.");

        Field currentField = player.getField(fieldNumber);
        if(!currentField.isEmpty() && currentField.getCardType() != card)
            throw new IllegalMoveException("Player " + player.getName() + ": The given card type is not the same.");

        if(currentField.getCardType() == card || currentField.isEmpty()) {
            currentField.setCardType(card);
            currentField.increaseCardAmount();
            player.getTradedCards().remove(card);
        }
    }
}
