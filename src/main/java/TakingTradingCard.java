public class TakingTradingCard implements Action{

    private final Player player;
    private final int tradingCardFieldNumber;

    /**
     * Player tries to take the trading card. If the player is not in the correct phase, an IllegalMoveException
     * will be thrown.
     * @param player the given player
     * @param tradingCardFieldNumber trading field number with the trading card.
     */
    public TakingTradingCard(Player player, int tradingCardFieldNumber) {
        this.player = player;
        this.tradingCardFieldNumber = tradingCardFieldNumber;
    }

    @Override
    public void execute() throws IllegalMoveException {
        player.getPhase().takeTradingCard(player, tradingCardFieldNumber);
    }
}
