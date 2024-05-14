import java.util.List;

public class Phase2 implements Phase {

    @Override
    public void plant(Player player, int field) throws IllegalMoveException {
        throw new IllegalMoveException("Unable to perform this action in the current phase.");
    }

    @Override
    public void harvest(Player player, int fieldNumber) throws IllegalMoveException {
        throw new IllegalMoveException("Unable to perform this action in the current phase.");
    }

    @Override
    public void tradeCardes(Player player, List<Card> send, List<Card> receive) {

    }

    @Override
    public void drawCards(Player player, Pile pile) throws IllegalMoveException {
        throw new IllegalMoveException("Unable to perform this action in the current phase.");
    }
}
