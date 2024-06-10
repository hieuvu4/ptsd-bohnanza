package game.phases;

import game.*;

import java.util.Arrays;

public class PhaseUsing extends Phase {

    public PhaseUsing() {
        System.out.println("Phase Using");
    }

    @Override
    public void takeTradingCards(final Player player, int tradingCardFieldNumber) throws IllegalMoveException {
        Field tradingField = getField(player, tradingCardFieldNumber);

        if (tradingField.getCardType() == null) throw new IllegalMoveException();

        boolean cardFound = false;
        int index = -1;
        for(int i = 0; i < player.getFields().length; i++) {
            Field playerField = player.getField(i);
            if(playerField.getCardType() == tradingField.getCardType() || playerField.getCardType() == null) {
                cardFound = true;
                index = i;
                break;
            }
        }
        if (!cardFound) throw new IllegalMoveException();

        for(int i = 0; i < tradingField.getCardAmount(); i++) {
            player.getField(index).setCardType(tradingField.getCardType());
            player.getField(index).increaseCardAmount();
        }

        tradingField.clear();
    }

    @Override
    public void putTradingCardsToDiscard(Player player, int tradingCardFieldNumber) throws IllegalMoveException {
        Field tradingField = getField(player, tradingCardFieldNumber);

        if (tradingField.getCardType() == null) throw new IllegalMoveException();

        Pile pile = player.getGameField().getPile();

        for(int i = 0; i < tradingField.getCardAmount(); i++) {
            pile.getDiscardPile().add(tradingField.getCardType());
        }

        tradingField.clear();
    }

    private static Field getField(Player player, int tradingCardFieldNumber) {
        if (tradingCardFieldNumber < 0 || tradingCardFieldNumber > 2) {
            throw new IllegalArgumentException("Trading card field number must be between 0 and 2.");
        }

        TradingArea tradingArea = player.getGameField().getTradingArea();
        Field tradingField = tradingArea.getTradingFields().get(tradingCardFieldNumber);
        return tradingField;
    }
}
