package game.mafia;

import game.*;

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
        Card card = field.getCardType();
        int fieldSize = field.getCardAmount();
        int coinAmount = field.harvest();
        int diff = fieldSize - coinAmount;
        for(int i = 0; i < coinAmount; i++) bank.addCoin(card);
        for(int i = 0; i < diff; i++) gameField.getPile().getDiscardPile().add(card);
        field.clear();
    }

    public abstract void tryHarvest() throws IllegalMoveException;
}
