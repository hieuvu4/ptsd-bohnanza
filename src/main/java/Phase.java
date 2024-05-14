import java.util.List;

public interface Phase {

    void plant(Player player, int field) throws IllegalMoveException;

    void harvest(Player player, int fieldNumber) throws IllegalMoveException;

    void tradeCardes(Player player, List<Card> send, List<Card> receive) throws IllegalMoveException;

    void drawCards(Player player, Pile pile) throws IllegalMoveException;
}
