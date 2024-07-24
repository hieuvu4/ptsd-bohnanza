package gui;

import io.bitbucket.plt.sdp.bohnanza.gui.Coordinate;
import io.bitbucket.plt.sdp.bohnanza.gui.Size;

/**
 * Base class for containers belonging to a player
 */
public abstract class PlayerContainer extends Container {

    private final game.Player player;

    /**
     * Constructs a new Container
     * @param gui the gui
     * @param pos the position of the Container
     * @param size the size of the Container
     * @param label the label to be displayed. may be null
     * @param player the player this container belongs to
     */
    public PlayerContainer(Gui gui, Coordinate pos, Size size, String label, game.Player player) {
        super(gui, pos, size, label);
        this.player = player;
    }

    /**
     * @return the player this container belongs to
     */
    protected game.Player getPlayer() {
        return player;
    }
}
