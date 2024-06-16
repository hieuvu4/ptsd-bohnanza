package gui;

import game.IllegalMoveException;
import io.bitbucket.plt.sdp.bohnanza.gui.Button;
import io.bitbucket.plt.sdp.bohnanza.gui.Coordinate;
import io.bitbucket.plt.sdp.bohnanza.gui.Size;

public class OfferField extends PlayerContainer{

    private final int number;

    public OfferField(Gui gui, Coordinate pos, Size size, game.Player player, int number) {
        super(gui, pos, size, "Offer for Card " + number, player);
        this.number = number;

        gui.addButton("Take Offer", new Coordinate(pos.x, pos.y + (size.height * 6) / 7),
                new Size(size.width, size.height / 7), this::takeOffer);
    }

    @Override
    public boolean getFrom(Container container, Card card) {
        return container.putInOfferField(this, card);
    }

    private void takeOffer(Button button) {
        try {
            getPlayer().offerCards(containedCards.stream().map(Card::getGameCard).toList(), number);
            getGui().turnPlayer().acceptOffer(getPlayer(), number);
            clear();
            getGui().reloadTrade();
        } catch (IllegalMoveException | IllegalArgumentException ignored) {}
    }

    @Override
    public void reload() {
        clear();
    }
}
