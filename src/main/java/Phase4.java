import java.util.List;
import java.util.stream.IntStream;

public class Phase4 implements Phase {

    @Override
    public void plant(Player player, int field) throws IllegalMoveException {
        throw new IllegalMoveException("Unable to perform this action in the current phase.");
    }

    @Override
    public void harvest(Player player, int fieldNumber) throws IllegalMoveException {
        throw new IllegalMoveException("Unable to perform this action in the current phase.");
    }

    @Override
    public void tradeCardes(Player player, List<Card> send, List<Card> receive) throws IllegalMoveException {
        throw new IllegalMoveException("Unable to perform this action in the current phase.");
    }

    @Override
    public void drawCards(Player player, Pile pile) {
        for(int i = 0; i < 3; i++) player.getHand().addCard(pile.drawCard());
    }
}
