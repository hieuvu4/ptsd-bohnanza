package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TradingArea {

    private final Pile pile;
    private final List<Field> tradingFields;
    private final Field tradingField1;
    private final Field tradingField2;
    private final Field tradingField3;

    public TradingArea(GameField gameField) {
        this.pile = gameField.getPile();
        tradingFields = new ArrayList<>();

        tradingField1 = new Field();
        tradingField2 = new Field();
        tradingField3 = new Field();

        tradingFields.add(tradingField1);
        tradingFields.add(tradingField2);
        tradingFields.add(tradingField3);
    }

    public List<Field> getTradingFields() {
        return tradingFields;
    }

    public void fillTradingField(int index) {
        tradingFields.get(index).setCardType(pile.drawCard());
        tradingFields.get(index).increaseCardAmount();
    }
}
