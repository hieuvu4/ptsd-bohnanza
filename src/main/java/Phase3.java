import java.util.List;

public class Phase3 implements Phase {

    @Override
    public void plant(Player player, int field) {

    }

    @Override
    public void harvest(Player player, int fieldNumber) {

    }

    @Override
    public void tradeCardes(Player player, List<Card> send, List<Card> receive) throws IllegalMoveException {
        throw new IllegalMoveException("Unable to perform this action in the current phase.");
    }

    @Override
    public void drawCards(Player player, Pile pile) throws IllegalMoveException {
        throw new IllegalMoveException("Unable to perform this action in the current phase.");
    }
}
