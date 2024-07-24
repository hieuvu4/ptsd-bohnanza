package gui;

import io.bitbucket.plt.sdp.bohnanza.gui.Coordinate;
import io.bitbucket.plt.sdp.bohnanza.gui.Size;

/**
 * Representative of boss hand in GUI
 */
public class Boss extends Container {

    private final game.mafia.Boss boss;

    /**
     * New boss representing boss on gui
     * @param gui the gui
     * @param pos the position of the boss
     * @param size the size of the boss
     * @param boss the game object being represented
     */
    public Boss(Gui gui, Coordinate pos, Size size, game.mafia.Boss boss) {
        super(gui, pos, size, boss.getClass().getSimpleName());
        this.boss = boss;
    }

    @Override
    public boolean getFrom(Container container, Card card) {
        return container.putInBoss(this, card);
    }

    /**
     * Get game boss this Boss represents
     * @return game boss this Boss represents
     */
    public game.mafia.Boss getBoss() {
        return boss;
    }

    @Override
    public void reload() {
        clear();
        getGui().reload(DiscardPile.class);
        for (int i = 0; i < boss.getField().getCardAmount(); i++) {
            addCard(new game.cards.Card(boss.getField().getCardType()));
        }
        formatCards();
    }
}
