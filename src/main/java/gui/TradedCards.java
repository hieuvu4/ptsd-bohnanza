package gui;

import game.IllegalMoveException;
import io.bitbucket.plt.sdp.bohnanza.gui.Coordinate;
import io.bitbucket.plt.sdp.bohnanza.gui.Size;

import java.util.NoSuchElementException;

public class TradedCards extends PlayerContainer{


    public TradedCards(Gui gui, Coordinate pos, Size size, game.Player player) {
        super(gui, pos, size, "Traded Cards\n of Player " + player.getName(), player);
    }

    @Override
    public boolean getFrom(Container container, Card card) {
        return container.putInTradedCards(this, card);
    }

    @Override
    public boolean putInField(Field field, Card card) {
        if (this.getPlayer() != field.getPlayer()) return false;
        try {
            getPlayer().plant(field.getNumber(), card.getGameCard());
            return true;
        } catch (IllegalMoveException | ArrayIndexOutOfBoundsException | NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public void reload() {
        clear();
        getPlayer().getTradedCards().forEach(this::addCard);
        formatCards();
    }
}
