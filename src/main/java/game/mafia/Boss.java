package game.mafia;

import game.*;
import game.cards.Card;
import game.cards.CardType;

public abstract class Boss {

    private final MafiaBank bank;
    private final GameField gameField;
    private final Field field;

    public Boss(GameField gameField) {
        field = new Field();
        this.gameField = gameField;
        this.bank = gameField.getMafiaBank();
    }

    public Field getField() {
        return field;
    }

    public void harvest() throws IllegalMoveException {
        CardType cardType = field.getCardType();
        int fieldSize = field.getCardAmount();
        int coinAmount = field.harvest();
        int diff = fieldSize - coinAmount;
        for(int i = 0; i < coinAmount; i++) bank.addCoin(new Card(cardType));
        for(int i = 0; i < diff; i++) gameField.getPile().getDiscardPile().add(new Card(cardType));
        field.clear();
    }

    public abstract void tryHarvest() throws IllegalMoveException;
}
