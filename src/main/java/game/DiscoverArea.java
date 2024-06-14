package game;

import java.util.ArrayList;
import java.util.List;

public class DiscoverArea {

    private final Pile pile;
    private final List<Field> discoverFields;
    private final Field discoverField1;
    private final Field discoverField2;
    private final Field discoverField3;

    public DiscoverArea(GameField gameField) {
        this.pile = gameField.getPile();
        discoverFields = new ArrayList<>();

        discoverField1 = new Field();
        discoverField2 = new Field();
        discoverField3 = new Field();

        discoverFields.add(discoverField1);
        discoverFields.add(discoverField2);
        discoverFields.add(discoverField3);
    }

    public List<Field> getDiscoverFields() {
        return discoverFields;
    }

    public void fillDiscoverCard(int index) {
        discoverFields.get(index).setCardType(pile.drawCard().cardType());
        discoverFields.get(index).increaseCardAmount();
    }
}
