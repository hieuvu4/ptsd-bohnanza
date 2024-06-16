package gui;

import game.IllegalMoveException;
import io.bitbucket.plt.sdp.bohnanza.gui.Coordinate;
import io.bitbucket.plt.sdp.bohnanza.gui.Size;

import java.util.NoSuchElementException;

public class DiscoverField extends Container{

    private final game.Field field;
    private final int number;

    public DiscoverField(Gui gui, Coordinate pos, Size size, game.Field field, int number) {
        super(gui, pos, size, "Discover Field " + number);
        this.field = field;
        this.number = number;
    }

    @Override
    public boolean getFrom(Container container, Card card) {
        return false;
    }

    @Override
    public boolean putInField(Field field, Card card) {
        if (getGui().turnPlayer() != field.getPlayer()) return false;
        try {
            getGui().turnPlayer().cultivateOwnField(number);
            reload();
            getGui().reload(Field.class);
            return true;
        } catch (IllegalMoveException | ArrayIndexOutOfBoundsException | NoSuchElementException e) {
            try {
                getGui().turnPlayer().takeDiscoverCards(number);
                reload();
                getGui().reload(Field.class);
                return true;
            } catch (IllegalMoveException ex) {
                return false;
            }
        }

    }

    @Override
    protected boolean putInBoss(Boss boss, Card card) {
        try {
            getGui().turnPlayer().cultivateBossField(number, boss.getBoss());
            getGui().reload(Boss.class, DiscoverField.class);
            return true;
        } catch (IllegalMoveException e) {
            return false;
        }
    }

    @Override
    public void reload() {
        clear();
        for (int i = 0; i < field.getCardAmount(); i++) {
            addCard(new game.cards.Card(field.getCardType()));

        }
        formatCards();
    }
}
