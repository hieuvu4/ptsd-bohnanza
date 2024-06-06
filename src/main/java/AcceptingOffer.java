public class AcceptingOffer implements Action {

    private final Player player;
    private final Player other;
    private final int tradingCardFieldNumber;

    /**
     * Player tries to accept an offer of another player. The player gets the offered cards of the other player while
     * the other player gets the trading card. If the player is not in the correct phase, an IllegalMoveException
     * will be thrown.
     * @param player the given player
     * @param other the other player who gets the trading card
     * @param tradingCardFieldNumber trading field number with the trading card
     */
    public AcceptingOffer(Player player, Player other, int tradingCardFieldNumber) {
        this.player = player;
        this.other = other;
        this.tradingCardFieldNumber = tradingCardFieldNumber;
    }

    @Override
    public void execute() throws IllegalMoveException {
        player.getPhase().acceptOffer(player, other, tradingCardFieldNumber);
    }
}
