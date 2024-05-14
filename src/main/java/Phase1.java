import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Phase1 implements Phase {

    @Override
    public void plant(Player player, int field) throws IllegalMoveException {
        Field currentField = player.getFields()[field];
        Card card = player.getHand().popTopCard();
        if(!currentField.isEmpty() && currentField.getCardType() != card)
            throw new IllegalMoveException("The card type is not the same.");

        if(currentField.getCardType() == card || currentField.isEmpty()) {
            currentField.setCardType(card);
            currentField.addCardToField();
            player.getHand().removeCard(0);
        }
        player.setPhase(new Phase2());
    }

    @Override
    public void harvest(Player player, int fieldNumber) throws IllegalMoveException {
        Field[] fields = player.getFields();

        // check if any field is null
        if (Arrays.stream(fields).anyMatch(Objects::isNull))
            throw new IllegalMoveException("Field cannot be harvested because a field is empty.");

        int maxAmount = Arrays.stream(fields).mapToInt(Field::getCardAmount).max().orElse(0);
        if (maxAmount > 1 && fields[fieldNumber].getCardAmount() > 1 || maxAmount == 1) {
            harvestValidField(player, fieldNumber, fields);
        } else {
            throw new IllegalMoveException("Field cannot be harvested.");
        }
    }

    @Override
    public void tradeCardes(Player player, List<Card> send, List<Card> receive) throws IllegalMoveException {
        throw new IllegalMoveException("Unable to perform this action in the current phase.");
    }

    @Override
    public void drawCards(Player player, Pile pile) throws IllegalMoveException {
        throw new IllegalMoveException("Unable to perform this action in the current phase.");
    }

    private void harvestValidField(Player player, int fieldNumber, Field[] fields) throws IllegalMoveException {
        Card cardType = fields[fieldNumber].getCardType();
        int currentAmount = fields[fieldNumber].getCardAmount();
        int coinAmount = fields[fieldNumber].harvest();
        for (int i = 0; i < coinAmount; i++) player.getCoins().add(cardType);
        for (int i = 0; i < currentAmount-coinAmount; i++) {
            // TODO: add to discard pile
        }
    }
}
