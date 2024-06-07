package game.phases;

import game.IllegalMoveException;
import game.Pile;
import game.Player;

public class Phase4 extends Phase {

    /**
     * Player draws 3 cards. If the player has already drawn and tries to draw again,  an IllegalMoveException will be
     * thrown.
     * @param player the given player
     * @param pile the pile where the cards will be drawn
     * @throws IllegalMoveException if the player already drawn cards
     */
    @Override
    public void drawCards(final Player player, final Pile pile) throws IllegalMoveException {
        if(player.getDrawn()) throw new IllegalMoveException("Player " + player.getName()
                + ": Already drawn three cards.");

        for(int i = 0; i < 3; i++) player.getHand().addCard(pile.drawCard());
    }

}
