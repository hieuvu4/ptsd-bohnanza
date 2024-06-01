import java.util.List;

public class PhaseOut extends Phase {

    @Override
    public void offerCards(final Player player, final List<Card> cards, int tradingCardFieldNumber) {
        if (cards.isEmpty()) throw new IllegalArgumentException("Player " + player.getName()
                + " didn't offered any cards.");

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
