public class CheckingOffers implements Action {

    private final Player player;

    /**
     * Player tries to check if there are any offers. If the player is not in the correct phase, an
     * IllegalMoveException will be thrown.
     * @throws IllegalMoveException if not in correct phase
     */
    public CheckingOffers(Player player) {
        this.player = player;
    }

    @Override
    public void execute() throws IllegalMoveException {
        player.getPhase().checkOffers(player);
    }
}
