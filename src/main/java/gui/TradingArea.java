package gui;

import game.IllegalMoveException;
import io.bitbucket.plt.sdp.bohnanza.gui.Coordinate;
import io.bitbucket.plt.sdp.bohnanza.gui.Size;

public class TradingArea extends Container {

    private final game.TradingArea tradingArea;
    private final int number;

    public TradingArea(Gui gui, Coordinate pos, Size size, game.TradingArea tradingArea, int number) {
        super(gui, pos, size, "Trading Area");
        this.tradingArea = tradingArea;
        this.number = number;
    }

    @Override
    public boolean getFrom(Container container, Card card) {
        return false;
    }

    @Override
    protected boolean putInTradedCards(TradedCards tradedCards, Card card) {
        try {
            tradedCards.getPlayer().takeTradingCards(number);
            return true;
        } catch (IllegalMoveException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void reload() {
        clear();
        if (tradingArea.getTradingCards()[number] != null){
            var card = addCard(tradingArea.getTradingCards()[number]);
            card.getCardObject().showFront(true);
        }
        formatCards();
    }
}
