package gui;

import io.bitbucket.plt.sdp.bohnanza.gui.Coordinate;
import io.bitbucket.plt.sdp.bohnanza.gui.Size;

public abstract class PlayerContainer extends Container {

    private final game.Player player;

    public PlayerContainer(Gui gui, Coordinate pos, Size size, String label, game.Player player) {
        super(gui, pos, size, label);
        this.player = player;
    }

    protected game.Player getPlayer() {
        return player;
    }
}
