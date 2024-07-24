package game.phases;

import game.cards.Card;
import game.Field;
import game.IllegalMoveException;
import game.Player;

import java.util.NoSuchElementException;
import java.util.Objects;

public class PhasePlanting extends Phase {

    private int plantedAmount = 0;

    public PhasePlanting() {
        System.out.println("Phase Planting");
    }

    /**
     * Player tries to plant a given card to a certain field. If the field is occupied, the player doesn't have any
     * hand pile or the given card doesn't exist in the hand pile, the method will throw an IllegalMoveException.
     * @param player the player who tries to plant
     * @param fieldNumber the index of the field
     * @param card the card which should be planted
     * @throws IllegalMoveException if hand pile is empty, no such card exists in the hand pile or the field is
     *  occupied
     */
    @Override
    public void plant(final Player player, final int fieldNumber, final Card card) throws IllegalMoveException {
        if (plantedAmount > 1)
            throw new IllegalMoveException("Player " + player.getName() + ": Can't plant a third time.");
        if(player.getHand().getHandPile().isEmpty())
            throw new NoSuchElementException("Player " + player.getName() + ": There are no cards in the hand.");

        for(int i = 0; i < player.getFields().length; i++) {
            if(i == fieldNumber) {
                continue;
            }
            if(player.getField(i).getCardType() == card.cardType()) {
                throw new IllegalMoveException("Player " + player.getName() + ": Card type already exists.");
            }
        }

        if (!card.equals(player.getHand().peekTopCard()))
            throw new IllegalMoveException("Player " + player.getName() + ": The given card is not the first card.");

        Field currentField = player.getField(fieldNumber);
        if(!currentField.isEmpty() && !currentField.getCardType().equals(card.cardType()))
            throw new IllegalMoveException("Player " + player.getName() + ": The given card type is not the same.");

        if(currentField.isEmpty() || currentField.getCardType().equals(card.cardType())) {
            currentField.setCardType(card.cardType());
            currentField.increaseCardAmount();
            player.getHand().removeCard(0);
            plantedAmount++;
        }
    }
}
