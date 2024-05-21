import java.util.*;

public class GameField implements Observer {
    private final Pile pile;
    private final List<Player> players;
    private final Card[] tradingArea;
    private Player turnPlayer;
    private Phase phase;

    public GameField(int playerAmount) {
        this.pile = new Pile();
        players = new ArrayList<>();
        tradingArea = new Card[2];

        for (int i = 0; i < playerAmount; i++) {
            players.add(new Player(i+1 + ""));
            for (int j = 0; j < 5; j++) players.get(i).getHand().addCard(pile.drawCard());

            players.get(i).addObserver(this);
        }

        turnPlayer = players.getFirst();
        turnPlayer.setPhase(new Phase1());
        phase = turnPlayer.getPhase();
        System.out.println("Turn of Player " + (players.indexOf(turnPlayer)+1) + ".");
    }

    public void trade(final List<Card> send, final List<Card> receive, Player other) {

    }

    public List<Player> getPlayers() {
        return players;
    }

    public Pile getPile() {
        return pile;
    }

    public Card[] getTradingArea() {
        return tradingArea;
    }

    /**
     * This method updates the attributes phase which represent the current phase of the current player's turn.
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        this.phase = (Phase) arg;
        if(phase instanceof Phase2) {
            // TODO: REWORK TRADING
            fillTradingArea();
            try {
                turnPlayer.tradeCards(null, Arrays.asList(tradingArea));
            } catch (IllegalMoveException e) {
                throw new RuntimeException(e);
            }
        }
        if (phase instanceof PhaseOut) {
            int index = players.indexOf(turnPlayer);
            turnPlayer = (index+1 >= players.size()) ? players.getFirst() : players.get(index+1);
            turnPlayer.setPhase(new Phase1());
            System.out.println("Turn of Player " + (players.indexOf(turnPlayer)+1) + ".");
        }
    }

    private void fillTradingArea() {
        tradingArea[0] = getPile().drawCard();
        tradingArea[1] = getPile().drawCard();
    }
}
