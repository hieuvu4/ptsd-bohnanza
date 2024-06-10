package game.mafia;

import game.Card;
import game.GameField;
import game.IllegalMoveException;

public class JoeBohnano extends Boss {

    public JoeBohnano(GameField gameField) {
        super(gameField);
    }

    @Override
    public void tryHarvest() throws IllegalMoveException {
        Card currentCard = super.getField().getCardType();
        if (currentCard.getCoinValue(super.getField().getCardAmount()) >= 1) {
            this.harvest();
        }
    }
}
