package game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradingArea {

    private final Card[] tradingCards;
    private final Map<Player, List<Card>> offersForTCard0;
    private final Map<Player, List<Card>> offersForTCard1;
    private final GameField gameField;

    public TradingArea(GameField gameField) {
        this.gameField = gameField;
        this.tradingCards = new Card[2];
        this.offersForTCard0 = new HashMap<>();
        this.offersForTCard1 = new HashMap<>();
    }

    public Card[] getTradingCards() {
        return tradingCards;
    }

    public void emptyTradingField(int tradingFieldNumber) {
        tradingCards[tradingFieldNumber] = null;
    }

    public Map<Player, List<Card>> getOffersForTCard0() {
        return offersForTCard0;
    }

    public Map<Player, List<Card>> getOffersForTCard1() {
        return offersForTCard1;
    }

    /**
     * Fills the trading area by drawing two cards of the pile and added to the area.
     */
    public void fillTradingArea() {
        tradingCards[0] = gameField.getPile().drawCard();
        tradingCards[1] = gameField.getPile().drawCard();
    }
}
