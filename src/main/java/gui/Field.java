package gui;

import game.IllegalMoveException;
import io.bitbucket.plt.sdp.bohnanza.gui.Button;
import io.bitbucket.plt.sdp.bohnanza.gui.Coordinate;
import io.bitbucket.plt.sdp.bohnanza.gui.Size;

public class Field extends PlayerContainer{

    private final int number;
    private final Button harvestButton;
    private final Coins coins;

    public Field(Gui gui, Coordinate pos, Size size, int number, game.Player player, Coins coins) {
        super(gui, pos, new Size(size.width, (size.height * 6) / 7), "Field " + number + " of Player " + player.getName(), player);
        this.number = number;
        this.coins = coins;
        harvestButton = gui.addButton("Harvest", new Coordinate(pos.x, pos.y + (size.height * 6) / 7),
                new Size(size.width, size.height / 7), this::harvest);
    }

    @Override
    public boolean getFrom(Container container, Card card) {
        return container.putInField(this, card);
    }

    public int getNumber() {
        return number;
    }

    private void harvest(Button b){
        try{
            getPlayer().harvest(number);
            clear();
            coins.reload();
            getGui().reload(DiscardPile.class);
        } catch (IllegalMoveException | ArrayIndexOutOfBoundsException ignored) {}
    }

    @Override
    public void reload() {
        clear();
        try {
            for (int i = 0; i < getPlayer().getField(number).getCardAmount(); i++) {
                addCard(new game.cards.Card(getPlayer().getField(number).getCardType()));
            }
        } catch (ArrayIndexOutOfBoundsException ignored){}
        formatCards();
    }
}
