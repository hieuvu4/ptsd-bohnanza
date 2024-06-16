package gui;

import game.IllegalMoveException;
import game.Player;
import game.phases.PhaseTrading;
import io.bitbucket.plt.sdp.bohnanza.gui.Button;
import io.bitbucket.plt.sdp.bohnanza.gui.Coordinate;
import io.bitbucket.plt.sdp.bohnanza.gui.Size;

import java.util.Collections;
import java.util.NoSuchElementException;

public class Hand extends PlayerContainer{

    private final Button flip;
    private boolean shown = false;

    protected Hand(Gui gui, Coordinate pos, Size size, Player player) {
        super(gui, pos, new Size(size.width, (size.height * 6) / 7), "Hand of Player " + player.getName(), player);
        flip = gui.addButton("Flip Cards", new Coordinate(pos.x, pos.y + (size.height * 6) / 7),
                new Size(size.width, size.height / 7), this::flipCards);
    }

    private void flipCards(Button button){
        setCardsVisible(shown = !shown);
    }

    public void setCardsVisible(boolean visible) {
        for (var card : containedCards) {
            card.getCardObject().showFront(visible);
        }
    }

    @Override
    public void reload() {
        clear();
        for (int i = getPlayer().getHand().getHandPile().size() - 1; i >= 0; i--) {
            addCard(getPlayer().getHand().getHandPile().get(i)).getCardObject().showFront(shown);
        }
        Collections.reverse(containedCards);
        formatCards();
    }

    @Override
    public boolean getFrom(Container container, Card card) {
        return false;
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
    public boolean putInOfferField(OfferField offerField, Card card) {
        return !(getPlayer() == getGui().turnPlayer()) && offerField.getPlayer() == getPlayer() && (getGui().turnPlayer().getPhase() instanceof PhaseTrading);
    }

    @Override
    protected boolean putInBoss(Boss boss, Card card) {
        try {
            getPlayer().giveBossCardFromHand(card.getGameCard(), boss.getBoss());
            getGui().reload(Boss.class);
            return true;
        } catch (IllegalMoveException e) {
            return false;
        }
    }
}
