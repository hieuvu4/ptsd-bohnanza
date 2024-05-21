public class Main {

    public static void main(String[] args) throws IllegalMoveException {

        GameField gameField = new GameField(3);

        Player player1 = gameField.getPlayers().get(0);
        Player player2 = gameField.getPlayers().get(1);
        Player player3 = gameField.getPlayers().get(2);

        System.out.println();

        line();

        getHandPileOf(player1);
        getHandPileOf(player2);
        getHandPileOf(player3);

        line();

        player1.plant(0, player1.getHand().popTopCard());
        player1.plant(1, player1.getHand().popTopCard());

        getHandPileOf(player1);
        getFieldsOf(player1);

        nextPhaseOf(player1); // 2

        showTradingArea(gameField);

        nextPhaseOf(player1); // 3

        showTradedCards(player1);

        player1.harvest(0);
        player1.plant(0, player1.getTradedCards().getFirst());

        player1.harvest(1);
        player1.plant(1, player1.getTradedCards().getFirst());

        getFieldsOf(player1);

        nextPhaseOf(player1); // 4

        getFieldsOf(player1);

        player1.drawCards(gameField.getPile());
        getHandPileOf(player1);

        nextPhaseOf(player1); // 5

        ///////////////////////////////////////////////

        player2.plant(0, player2.getHand().popTopCard());

        player2.plant(1, player2.getHand().popTopCard());

        getHandPileOf(player2);
        getFieldsOf(player2);

        nextPhaseOf(player2); // 2

        showTradingArea(gameField);

        nextPhaseOf(player2); // 3

        showTradedCards(player2);

        player2.harvest(0);
        player2.plant(0, player2.getTradedCards().getFirst());

        player2.harvest(1);
        player2.plant(1, player2.getTradedCards().getFirst());

        getFieldsOf(player2);

        nextPhaseOf(player2); // 4

        getFieldsOf(player2);

        player2.drawCards(gameField.getPile());
        getHandPileOf(player2);

        nextPhaseOf(player2); // 5

        ///////////////////////////////////////////////

        player3.plant(0, player3.getHand().popTopCard());

        player3.plant(1, player3.getHand().popTopCard());

        getHandPileOf(player3);
        getFieldsOf(player3);

        nextPhaseOf(player3); // 2

        showTradingArea(gameField);

        nextPhaseOf(player3); // 3

        showTradedCards(player3);

        player3.harvest(0);
        player3.plant(0, player3.getTradedCards().getFirst());

        player3.harvest(1);
        player3.plant(1, player3.getTradedCards().getFirst());

        getFieldsOf(player3);

        nextPhaseOf(player3); // 4

        getFieldsOf(player3);

        player3.drawCards(gameField.getPile());
        getHandPileOf(player3);

        nextPhaseOf(player3); // 5


    }

    private static void getHandPileOf(Player player) {
        System.out.println("Hand pile of player " + player.getName());
        System.out.println(player.getHand().getHandPile());
        System.out.println();
    }

    private static void getFieldsOf(Player player) {
        System.out.println("Fields of Player " + player.getName());
        System.out.println("Field 1: " + player.getField(0).getCardType() + ", " + player.getField(0).getCardAmount());
        System.out.println("Field 2: " + player.getField(1).getCardType() + ", " + player.getField(1).getCardAmount());
        System.out.println();
    }

    private static void nextPhaseOf(Player player) throws IllegalMoveException {
        System.out.println("---> Next Phase");
        System.out.println();
        player.nextPhase();

    }

    private static void line() {
        System.out.println("------------------------------");
        System.out.println();
    }

    private static void showTradingArea(GameField gamefield) {
        System.out.println("TradingArea 1: " + gamefield.getTradingArea()[0]);
        System.out.println("TradingArea 2: " + gamefield.getTradingArea()[1]);
        System.out.println();
    }

    private static void showTradedCards(Player player) {
        System.out.println("TradedCards: " + player.getTradedCards());
    }
}
