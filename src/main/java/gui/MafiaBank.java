package gui;

import io.bitbucket.plt.sdp.bohnanza.gui.Compartment;
import io.bitbucket.plt.sdp.bohnanza.gui.Coordinate;
import io.bitbucket.plt.sdp.bohnanza.gui.Size;

public class MafiaBank extends Container{

    private final game.mafia.MafiaBank mafiaBank;
    private Compartment coinAmount;
    private final Coordinate upperLeft;
    private final int width;

    public MafiaBank(Gui gui, Coordinate pos, Size size, game.mafia.MafiaBank mafiaBank) {
        super(gui, new Coordinate(pos.x, pos.y + 20), new Size(size.width, size.height - 20), "Mafia Bank");
        this.mafiaBank = mafiaBank;
        upperLeft = pos;
        width = size.width;
    }

    @Override
    public boolean getFrom(Container container, Card card) {
        return false;
    }

    private void displayCoins() {
        if (coinAmount != null){
            getGui().removeCompartment(coinAmount);
        }
        coinAmount = getGui().addCompartment(upperLeft, new Size(width, 20), "Score: " + mafiaBank.getCoins().size());
    }

    @Override
    public void reload() {
        clear();
        displayCoins();
        mafiaBank.getCoins().forEach(this::addCard);
        formatCards();
    }
}
