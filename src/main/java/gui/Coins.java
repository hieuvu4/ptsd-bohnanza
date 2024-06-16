package gui;

import game.IllegalMoveException;
import game.Player;
import io.bitbucket.plt.sdp.bohnanza.gui.Button;
import io.bitbucket.plt.sdp.bohnanza.gui.Compartment;
import io.bitbucket.plt.sdp.bohnanza.gui.Coordinate;
import io.bitbucket.plt.sdp.bohnanza.gui.Size;

public class Coins extends PlayerContainer{

    private Compartment coinAmount;
    private final Coordinate upperLeft;
    private final int width;

    public Coins(Gui gui, Coordinate pos, Size size, Player player) {
        super(gui, new Coordinate(pos.x, pos.y + 20), new Size(size.width, ((size.height / 7) * 6) - 20) , "Coins of Player " + player.getName(), player);
        upperLeft = pos;
        gui.addButton("Third Field", new Coordinate(pos.x, pos.y + (size.height / 7) * 6),
                new Size(size.width, size.height / 7), this::buyThirdField);
        width = size.width;
        displayCoins();
    }

    @Override
    public boolean getFrom(Container container, Card card) {
        return false;
    }

    @Override
    public void reload() {
        displayCoins();
        clear();
        for (var card: getPlayer().getCoins()) {
            var guiCard = addCard(card);
            guiCard.getCardObject().showFront(true);
        }
        formatCards();
    }

    private void displayCoins() {
        if (coinAmount != null){
            getGui().removeCompartment(coinAmount);
        }
        coinAmount = getGui().addCompartment(upperLeft, new Size(width, 20), "Score: " + getPlayer().getScore());
    }

    private void buyThirdField(Button button) {
        try {
            getPlayer().buyThirdField();
        } catch (IllegalMoveException ignored) {}
    }
}
