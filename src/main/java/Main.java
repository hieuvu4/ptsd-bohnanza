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

        player1.tradeCards(null, null);

        nextPhaseOf(player1); // 3

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

        player2.tradeCards(null, null);

        nextPhaseOf(player2); // 3

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

        player3.tradeCards(null, null);

        nextPhaseOf(player3); // 3

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
}
