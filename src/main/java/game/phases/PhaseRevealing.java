package game.phases;

import game.IllegalMoveException;
import game.Player;

public class PhaseRevealing extends Phase {

    public PhaseRevealing() {
        System.out.println("Phase Revealing");
    }

    @Override
    public void harvest(Player player, int fieldNumber) throws IllegalMoveException {
        throw new IllegalMoveException("Player " + player.getName()
                + ": Unable to perform this action in the current phase.");
    }
}
