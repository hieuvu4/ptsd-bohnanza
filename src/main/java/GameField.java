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

    // Darf man im trade die Karten noch aendern?
    // Oder sind die einmal festgesetzt, welche man tauschen will
    // Je nachdem, wie der Trade funktioniert, die Methode mit final-Param anpassen
    // Player wird sich wahrscheinlich ändern können, da mehrere "others" tauschen könnten.
    public void trade(final List<Card> send, final List<Card> receive, Player other) {

    }
}
