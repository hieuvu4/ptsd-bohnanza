public class Planting implements Action{

    private final int fieldNumber;
    private final Card card;
    private final Player player;

    /**
     * Player plants a card to the specified field. If the field is not empty, an IllegalMoveException will be
     * thrown.
     * @param player the given player
     * @param fieldNumber the given field where the card should be planted on
     * @param card the given card
     */
    public Planting(Player player, int fieldNumber, Card card) {
        this.player = player;
        this.fieldNumber = fieldNumber;
        this.card = card;
    }

    @Override
    public void execute() throws IllegalMoveException {
        player.getPhase().plant(player, fieldNumber, card);
        player.setPlanted(true);
    }
}
