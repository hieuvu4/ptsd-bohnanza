import java.util.List;

public class BuyingThirdField implements Action {

    private final Player player;

    /**
     * Player tries to buy a third field. If player doesn't have enough coins to buy the third field, an
     * IllegalMoveException will be thrown.
     * @param player the given player
     */
    public BuyingThirdField(Player player) {
        this.player = player;
    }

    @Override
    public void execute() throws IllegalMoveException {
        boolean bought = player.getBought();
        List<Card> coins = player.getCoins();
        if(bought) throw new IllegalMoveException("Player " + player.getName() + ": Already bought a field.");
        if(coins.size() < 3) throw new IllegalMoveException("Player " + player.getName()
                + ": Not enough coins to buy a third field.");
        player.setBought(true);
        Field[] newFields = new Field[3];
        newFields[0] = player.getField(0);
        newFields[1] = player.getField(1);
        newFields[2] = null;
        player.setFields(newFields);
        for(int i = 0; i < 3; i++) {
            Card card = coins.getFirst();
            coins.removeFirst();
            player.getGameField().getPile().getDiscardPile().add(card);
        }
    }
}
