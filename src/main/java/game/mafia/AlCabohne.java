package game.mafia;

import game.cards.CardType;
import game.GameField;
import game.IllegalMoveException;

public class AlCabohne extends Boss {

    public AlCabohne(GameField gameField) {
        super(gameField);
    }

    @Override
    public void tryHarvest() throws IllegalMoveException {
        CardType currentCardType = super.getField().getCardType();
        if (currentCardType == null) {
            return;
        }
        if (currentCardType.getCoinValue(super.getField().getCardAmount()) >= 3) {
            this.harvest();
        }
    }
}
