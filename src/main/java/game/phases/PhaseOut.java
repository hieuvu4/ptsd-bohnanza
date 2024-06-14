package game.phases;

import game.Field;
import game.IllegalMoveException;
import game.Player;
import game.cards.Card;

import java.util.List;

public class PhaseOut extends Phase {
    /**
     * Player tries to offer cards for a specific trading card. If the offered cards aren't in player's hand pile,
     * the offered cards are empty or the given tradingCardFieldNumber is wrong, an IllegalArgumentException will be
     * thrown.
     * @param player the player who wants to offer
     * @param cards exchange cards for the trading card
     * @param tradingCardFieldNumber trading field number with the trading card
     */
    @Override
    public void offerCards(final Player player, final List<Card> cards, int tradingCardFieldNumber) {
        for (Card card : cards) {
            boolean hasCard = player.getHand().getHandPile().contains(card);
            if (!hasCard) {
                throw new IllegalArgumentException("Player " + player.getName() + " doesn't have an offered card.");
            }
        }

        switch (tradingCardFieldNumber) {
            case 0:
                player.getGameField().getTradingArea().getOffersForTCard0().put(player, cards);
                break;
            case 1:
                player.getGameField().getTradingArea().getOffersForTCard1().put(player, cards);
                break;
            default:
                throw new IllegalArgumentException("Trading card field number must be between 0 and 1");
        }
    }

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
        if(!currentField.isEmpty() && !currentField.getCardType().equals(card.cardType()))
            throw new IllegalMoveException("Player " + player.getName() + ": The given card type is not the same.");

        if(currentField.isEmpty() || currentField.getCardType().equals(card.cardType())) {
            currentField.setCardType(card.cardType());
            currentField.increaseCardAmount();
            player.getTradedCards().remove(card);
        }
    }
}
