package game.phases;

import game.Card;
import game.Player;
import game.TradingArea;

import java.util.List;
import java.util.Map;

public class Phase2 extends Phase {

    /**
     * Player checks if there are any offers. If not, a message will be printed, which tells the player that there are
     * none offers. else the offers are shown for both trading cards.
     * @param player the player who tries to check
     */
    @Override
    public void checkOffers(Player player) {
        if(player.getGameField().getTradingArea().getOffersForTCard0().isEmpty())
            System.out.println("There are no offers to trade!");
        System.out.println(player.getGameField().getTradingArea().getOffersForTCard0());
        System.out.println(player.getGameField().getTradingArea().getOffersForTCard1());
    }

    /**
     * Player tries to accept the offer of another player. An IllegalArgumentException will be thrown if the other
     * player doesn't exist, the trading card is not available anymore or the tradingCardFieldNumber is not 0 or 1.
     * @param player the player who wants to accept an offer
     * @param other the player who offered the card in exchange for the trading card
     * @param tradingCardFieldNumber trading field number with the trading card
     */
    @Override
    public void acceptOffer(final Player player, final Player other, int tradingCardFieldNumber) {
        TradingArea tradingArea = player.getGameField().getTradingArea();
        switch (tradingCardFieldNumber) {
            case 0:
                checkLegalArguments(tradingArea.getOffersForTCard0(), other, tradingCardFieldNumber, tradingArea);
                executeOffer(tradingArea.getOffersForTCard0(), other, player, tradingArea, tradingCardFieldNumber);
                break;
            case 1:
                checkLegalArguments(tradingArea.getOffersForTCard1(), other, tradingCardFieldNumber, tradingArea);
                executeOffer(tradingArea.getOffersForTCard1(), other, player, tradingArea, tradingCardFieldNumber);
                break;
            default:
                throw new IllegalArgumentException("Trading card field number must be between 0 and 1");
        }
    }

    /**
     * Player tries to take the trading card. An IllegalArgumentException will be thrown, if the trading card is not
     * available anymore or the tradingCardFieldNumber is not 0 or 1.
     * @param player the player who takes the trading card
     * @param tradingCardFieldNumber trading field number with the trading card
     */
    @Override
    public void takeTradingCard(final Player player, int tradingCardFieldNumber) {
        TradingArea tradingArea = player.getGameField().getTradingArea();
        switch (tradingCardFieldNumber) {
            case 0, 1:
                if (tradingArea.getTradingCards()[tradingCardFieldNumber] == null)
                    throw new IllegalArgumentException("Trading card is not available anymore.");

                player.getTradedCards().add(tradingArea.getTradingCards()[tradingCardFieldNumber]);
                tradingArea.emptyTradingField(tradingCardFieldNumber);
                break;

            default:
                throw new IllegalArgumentException("Trading card field number must be between 0 and 1");
        }
    }

    private static void executeOffer(Map<Player, List<Card>> tradingArea, Player other, Player player,
                                     TradingArea tradingArea1, int tradingCardFieldNumber) {
        List<Card> tradedCards1 = tradingArea.get(other);
        for (Card card : tradedCards1) {
            player.getTradedCards().add(card);
            other.getHand().removeCard(card);
        }
        other.getTradedCards().add(tradingArea1.getTradingCards()[tradingCardFieldNumber]);
        tradingArea1.emptyTradingField(tradingCardFieldNumber);
    }

    private static void checkLegalArguments(Map<Player, List<Card>> player, Player other, int tradingCardFieldNumber,
                                            TradingArea tradingArea) {
        if (!player.containsKey(other))
            throw new IllegalArgumentException("No such player for the trading card in field "
                    + tradingCardFieldNumber + " exists.");
        if (tradingArea.getTradingCards()[tradingCardFieldNumber] == null)
            throw new IllegalArgumentException("Card is not available anymore.");
    }
}
