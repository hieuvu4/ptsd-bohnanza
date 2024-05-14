import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class Phase {

    public void plant(Player player, int fieldNumber, Card card) throws IllegalMoveException {
        throw new IllegalMoveException("Unable to perform this action in the current phase.");
    }

    public void harvest(Player player, int fieldNumber) throws IllegalMoveException {
        Field field = player.getField(fieldNumber);

        // check if any field is null
        if (Arrays.stream(player.getFields()).anyMatch(Objects::isNull))
            throw new IllegalMoveException("Field cannot be harvested because a field is empty.");

        int maxAmount = Arrays.stream(player.getFields()).mapToInt(Field::getCardAmount).max().orElse(0);
        if (maxAmount > 1 && field.getCardAmount() > 1 || maxAmount == 1) {
            harvestValidField(player, field);
        } else {
            throw new IllegalMoveException("Field cannot be harvested.");
        }
    }

     public void tradeCards(Player player, List<Card> send, List<Card> receive) throws IllegalMoveException {
         throw new IllegalMoveException("Unable to perform this action in the current phase.");
     }

    public void drawCards(Player player, Pile pile) throws IllegalMoveException {
        throw new IllegalMoveException("Unable to perform this action in the current phase.");
    }

    private void harvestValidField(Player player, Field field) throws IllegalMoveException {
        Card cardType = field.getCardType();
        int currentAmount = field.getCardAmount();
        int coinAmount = field.harvest();
        for (int i = 0; i < coinAmount; i++) player.getCoins().add(cardType);
        for (int i = 0; i < currentAmount-coinAmount; i++) {
            // TODO: add to discard pile
        }
    }
}
