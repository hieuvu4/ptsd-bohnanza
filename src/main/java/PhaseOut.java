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
        if (cards.isEmpty()) throw new IllegalArgumentException("Player " + player.getName()
                + " didn't offered any cards.");
        for (Card card : cards) {
            if(!player.getHand().getHandPile().contains(card)) throw new IllegalArgumentException("Player "
                    + player.getName() + " has doesn't have an offered card.");
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
}
