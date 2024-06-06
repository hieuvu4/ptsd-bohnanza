public class Drawing implements Action {

    private final Player player;
    private final Pile pile;

    public Drawing(Player player, Pile pile) {
        this.player = player;
        this.pile = pile;
    }

    @Override
    public void execute() throws IllegalMoveException {
        player.getPhase().drawCards(player, pile);
        player.setDrawn(true);
    }
}
