public class Harvesting implements Action{

    private final Player player;
    private final int fieldnumber;

    /**
     * Player tries to harvest the given field. If any field is empty, an IllegalMoveException will be thrown.
     * If the amount of cards of the given field is 1 and there is a field with more amount of cards, an
     * IllegalMoveException will be thrown. If the field has more than one card or all fields only have 1 card each,
     * the field can be harvested.
     * @param player the given player
     * @param fieldnumber the given field where the card should be planted on
     */
    public Harvesting(Player player, int fieldnumber) {
        this.player = player;
        this.fieldnumber = fieldnumber;
    }

    @Override
    public void execute() throws IllegalMoveException {
        player.getPhase().harvest(player, fieldnumber);
    }
}
