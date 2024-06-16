package game.mafia;

import game.cards.CardType;
import game.GameField;
import game.IllegalMoveException;

public class DonCorlebohne extends Boss {

    public DonCorlebohne(GameField gameField) {
        super(gameField);
    }

    @Override
    public void tryHarvest() throws IllegalMoveException {
        CardType currentCardType = super.getField().getCardType();
        if (currentCardType == null) {
            return;
        }
        if (currentCardType.getCoinValue(super.getField().getCardAmount()) >= 2) {
            this.harvest();
        }
    }
}
