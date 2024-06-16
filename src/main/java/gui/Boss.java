package gui;

import io.bitbucket.plt.sdp.bohnanza.gui.Coordinate;
import io.bitbucket.plt.sdp.bohnanza.gui.Size;

public class Boss extends Container {

    private final game.mafia.Boss boss;

    public Boss(Gui gui, Coordinate pos, Size size, game.mafia.Boss boss) {
        super(gui, pos, size, boss.getClass().getSimpleName());
        this.boss = boss;
    }

    @Override
    public boolean getFrom(Container container, Card card) {
        return container.putInBoss(this, card);
    }

    public game.mafia.Boss getBoss() {
        return boss;
    }

    @Override
    public void reload() {
        clear();
        for (int i = 0; i < boss.getField().getCardAmount(); i++) {
            addCard(new game.cards.Card(boss.getField().getCardType()));
        }
        formatCards();
    }
}
