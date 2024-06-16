package gui;

import io.bitbucket.plt.sdp.bohnanza.gui.Coordinate;
import io.bitbucket.plt.sdp.bohnanza.gui.Size;

import java.util.List;

public class DiscardPile extends Container {

    private final List<game.cards.Card> field;

    public DiscardPile(Gui gui, Coordinate pos, Size size, List<game.cards.Card> field) {
        super(gui, pos, size, "Discard Pile ");
        this.field = field;
    }

    @Override
    public boolean getFrom(Container container, Card card) {
        return container.putInDiscardPile(card);
    }


    @Override
    public void reload() {
        clear();
        for (game.cards.Card card : field) {
            addCard(new game.cards.Card(card.cardType()));
        }
        formatCards();
    }
}
