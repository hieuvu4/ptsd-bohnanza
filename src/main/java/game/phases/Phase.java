package game.phases;

import game.*;
import game.cards.Card;
import game.mafia.Boss;

import java.util.Arrays;
import java.util.List;

public abstract class Phase {

    /**
     * Player tries to plant a certain card in a valid field, if the player is in Phase 1 or Phase 3. Otherwise,
     * the method will throw an IllegalMoveException.
     * @param player the player who tries to plant
     * @param fieldNumber the index of the field
     * @param card the card which should be planted
     * @throws IllegalMoveException if the phase is not correct
     */
    public void plant(final Player player, final int fieldNumber, final Card card) throws IllegalMoveException {
        throw new IllegalMoveException("Player " + player.getName()
                + ": Unable to perform this action in the current phase.");
    }

    /**
     * Player can harvest any field any time. If the player violates the rule of harvesting, an IllegalMoveException
     * will be thrown
     * @param player the player who tries to harvest
     * @param fieldNumber the index of the field
     * @throws IllegalMoveException if the player violates the rule
     */
    public void harvest(final Player player, final int fieldNumber) throws IllegalMoveException {
        Field field = player.getField(fieldNumber);

        // check if any field is null
        for (Field f: player.getFields())
            if (f.isEmpty()) throw new IllegalMoveException("Player " + player.getName()
                    + ": Field cannot be harvested because a field is empty.");

        int maxAmount = Arrays.stream(player.getFields()).mapToInt(Field::getCardAmount).max().orElse(0);
        if (maxAmount > 1 && field.getCardAmount() > 1 || maxAmount == 1) {
            harvestValidField(player, field);
        } else {
            throw new IllegalMoveException("Player " + player.getName() + ": Field cannot be harvested.");
        }
    }
    /**
     * Player can check if there are any offers from other players. Player can only do it in phase 2, else an
     * IllegalMoveException will be thrown.
     * @param player the player who tries to check
     * @throws IllegalMoveException if wrong phase
     */
    public void checkOffers(final Player player) throws IllegalMoveException {
        throw new IllegalMoveException("Player " + player.getName()
                + ": Unable to perform this action in the current phase.");
    }

    /**
     * Player can offer cards for getting a specific trading card. Only Player who are in Phase Out can perform this,
     * else an IllegalMoveException will be thrown.
     * @param player the player who wants to offer
     * @param cards exchange cards for the trading card
     * @param tradingCardFieldNumber trading field number with the trading card
     * @throws IllegalMoveException if not in correct phase
     */
    public void offerCards(final Player player, final List<Card> cards, int tradingCardFieldNumber)
            throws IllegalMoveException {
        throw new IllegalMoveException("Player " + player.getName()
                + ": Unable to perform this action in the current phase.");
    }

    /**
     * Player can accept an offer of another player in exchange for a trading card. Player can only perform this in
     * phase 2, else an IllegalMoveException will be thrown.
     * @param player the player who wants to accept an offer
     * @param other the player who offered the card in exchange for the trading card
     * @param tradingCardFieldNumber trading field number with the trading card
     * @throws IllegalMoveException if not in correct phase
     */
    public void acceptOffer(final Player player, final Player other, int tradingCardFieldNumber)
            throws IllegalMoveException {
        throw new IllegalMoveException("Player " + player.getName()
                + ": Unable to perform this action in the current phase.");
    }

    /**
     * Player can take the trading card in phase 2, if that is not the case, an IllegalMoveException will be thrown.
     * @param player the player who takes the trading card
     * @param tradingCardFieldNumber trading field number with the trading card
     * @throws IllegalMoveException if not in correct phase
     */
    public void takeTradingCard(final Player player, int tradingCardFieldNumber) throws IllegalMoveException {
        throw new IllegalMoveException("Player " + player.getName()
                + ": Unable to perform this action in the current phase.");
    }


    /**
     * Player can take the discover cards. If the phase is wrong, an IllegalMoveException will be thrown.
     * @param player the player who takes the discover card
     * @param discoverCardFieldNumber discover field number with the discover card
     * @throws IllegalMoveException if not in correct phase
     */
     public void takeDiscoverCards(final Player player, int discoverCardFieldNumber) throws IllegalMoveException {
         throw new IllegalMoveException("Player " + player.getName()
                 + ": Unable to perform this action in the current phase.");
     }

     public void putDiscoverCardsToDiscard(final Player player, int discoverCardFieldNumber) throws IllegalMoveException {
         throw new IllegalMoveException("Player " + player.getName()
                 + ": Unable to perform this action in the current phase.");
     }

    public void cultivateOwnField(final Player player, final int discoverCardFieldNumber)
            throws IllegalMoveException {
        throw new IllegalMoveException("Player " + player.getName()
                + ": Unable to perform this action in the current phase.");
    }

    public void cultivateBossField(final Player player, final int discoverCardFieldNumber, Boss boss)
     throws IllegalMoveException {
    throw new IllegalMoveException("Player " + player.getName()
         + ": Unable to perform this action in the current phase.");
    }

    /**
     * Player tries to draw three cards. If the phase is wrong, an IllegalMoveException will be thrown.
     * @param player the given player
     * @param pile the pile where the cards will be drawn
     * @throws IllegalMoveException if player is in the wrong phase
     */
    public void drawCards(final Player player, Pile pile) throws IllegalMoveException {
        throw new IllegalMoveException("Player " + player.getName()
                + ": Unable to perform this action in the current phase.");
    }

    private void harvestValidField(final Player player, final Field field) throws IllegalMoveException {
        Card cardType = field.getCardType();
        int currentAmount = field.getCardAmount();
        int coinAmount = field.harvest();
        for (int i = 0; i < coinAmount; i++) player.getCoins().add(cardType);
        for (int i = 0; i < currentAmount-coinAmount; i++)
            player.getGameField().getPile().getDiscardPile().add(cardType);
    }
}
