import java.util.List;

public class Offering implements Action {

    private final Player player;
    private final List<Card> cards;
    private final int tradingCardFieldNumber;


    /**
     * Player tries to offer cards for a specific trading card while trading phase. If the player is not in the correct
     * phase, an IllegalMoveException will be thrown.
     * @param player the given player
     * @param cards the offered cards
     * @param tradingCardFieldNumber trading field number with the trading card
     */
    public Offering(Player player, List<Card> cards, int tradingCardFieldNumber) {
        this.player = player;
        this.cards = cards;
        this.tradingCardFieldNumber = tradingCardFieldNumber;
    }

    @Override
    public void execute() throws IllegalMoveException {
        player.getPhase().offerCards(player, cards, tradingCardFieldNumber);
    }
}
