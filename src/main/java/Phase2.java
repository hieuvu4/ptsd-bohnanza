import java.util.List;

public class Phase2 extends Phase {

    @Override
    public void tradeCards(final Player player, final List<Card> send, final List<Card> receive) throws IllegalMoveException {
        for (final Card card : receive) {
            player.getTradedCards().add(card);
        }
    }
} 
