package game.phases;

import game.*;
import game.mafia.Boss;

public class PhaseCultivating extends Phase {

    public PhaseCultivating() {
        System.out.println("Phase Cultivating");
    }

    @Override
    public void cultivateOwnField(Player player, int tradingCardFieldNumber) throws IllegalMoveException {
        if (tradingCardFieldNumber < 0 || tradingCardFieldNumber > 2)
            throw new IllegalArgumentException("Trading card field number must be between 0 and 2.");

        TradingArea tradingArea = player.getGameField().getTradingArea();
        Field tradingField = tradingArea.getTradingFields().get(tradingCardFieldNumber);

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
    public void cultivateBossField(Player player, int tradingCardFieldNumber, Boss boss) throws IllegalMoveException {
        if (tradingCardFieldNumber < 0 || tradingCardFieldNumber > 2)
            throw new IllegalArgumentException("Trading card field number must be between 0 and 2.");
        TradingArea tradingArea = player.getGameField().getTradingArea();
        Field tradingField = tradingArea.getTradingFields().get(tradingCardFieldNumber);

        if (tradingField.getCardType() == null) throw new IllegalMoveException();

        boss.harvest();

        boss.getField().setCardType(tradingField.getCardType());

        for(int i = 0; i < tradingField.getCardAmount(); i++) boss.getField().increaseCardAmount();

        tradingField.clear();
    }
}
