import java.util.List;

public class GameField {
    private final Pile pile;
    private final List<Player> players;
    private final Card[] tradingArea;
    private Player turnPlayer;

    public GameField() {
        this.pile = new Pile();
        players = null;
        tradingArea = null;
    }
    
    public void fillTradingArea() {
        
    }

    public void trade(List<Card> send, List<Card> receive, Player other) {

    }
}
