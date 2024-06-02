import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IllegalMoveException {

        GameField gameField = new GameField(3);
        Pile pile = gameField.getPile();

        Player player1 = gameField.getPlayers().get(0);
        Player player2 = gameField.getPlayers().get(1);
        Player player3 = gameField.getPlayers().get(2);

        System.out.println();

        line();
        handPile(player1);
        handPile(player2);
        handPile(player3);
        line();

        plant(player1, 0, player1.getHand().getHandPile().getFirst());
        plant(player1, 1, player1.getHand().getHandPile().getFirst());
        getField(player1);

        nextPhase(player1);

        getTradingArea(gameField);

        player1.takeTradingCards(0);
        player1.takeTradingCards(1);

        nextPhase(player1);

        player1.harvest(0);
        player1.plant(0, player1.getTradedCards().getFirst());
        player1.harvest(1);
        player1.plant(1, player1.getTradedCards().getFirst());
        getField(player1);

        nextPhase(player1);

        drawCards(player1, pile);
        handPile(player1);

        nextPhase(player1);

        /////////////////////////////////////////////////////////////////////////


        plant(player2, 0, player2.getHand().getHandPile().getFirst());
        plant(player2, 1, player2.getHand().getHandPile().getFirst());
        getField(player2);

        nextPhase(player2);

        getTradingArea(gameField);

        player2.takeTradingCards(0);
        player2.takeTradingCards(1);

        nextPhase(player2);

        player2.harvest(0);
        player2.plant(0, player2.getTradedCards().getFirst());
        player2.harvest(1);
        player2.plant(1, player2.getTradedCards().getFirst());
        getField(player2);

        nextPhase(player2);

        drawCards(player2, pile);
        handPile(player2);

        nextPhase(player2);

        /////////////////////////////////////////////////////////////////////////

        plant(player3, 0, player3.getHand().getHandPile().getFirst());
        plant(player3, 1, player3.getHand().getHandPile().getFirst());
        getField(player3);

        nextPhase(player3);

        getTradingArea(gameField);

        player3.takeTradingCards(0);
        player3.takeTradingCards(1);

        nextPhase(player3);

        player3.harvest(0);
        player3.plant(0, player3.getTradedCards().getFirst());
        player3.harvest(1);
        player3.plant(1, player3.getTradedCards().getFirst());
        getField(player3);

        nextPhase(player3);

        drawCards(player3, pile);
        handPile(player3);

        nextPhase(player3);

        /////////////////////////////////////////////////////////////////////////

    }

    private static void handPile(Player player) {
        System.out.println("Hand pile of player " + player.getName() + ":");
        System.out.println(player.getHand().getHandPile());
        System.out.println();
    }

    private static void plant(Player player, int fieldNumber, Card card) throws IllegalMoveException {
        player.plant(fieldNumber, card);
        System.out.println("Player " + player.getName() + " planted in field " + fieldNumber + " with card "
                + card.getName());
        System.out.println();
    }

    private static void line() {
        System.out.println("--------------------------------------");
        System.out.println();
    }

    private static void getField(Player player) {
        System.out.println("Field of player " + player.getName() + ":");
        System.out.println(player.getField(0).getCardType() + ", " + player.getField(0).getCardAmount());
        System.out.println(player.getField(1).getCardType() + ", " + player.getField(1).getCardAmount());
        System.out.println();
    }

    private static void getTradingArea(GameField gameField) {
        System.out.println("Trading Area:");
        System.out.println(Arrays.toString(gameField.getTradingArea().getTradingCards()));
        System.out.println();
    }

    private static void nextPhase(Player player) throws IllegalMoveException {
        System.out.println(">> Next Phase <<");
        player.nextPhase();
        System.out.println();
    }

    private static void drawCards(Player player, Pile pile) throws IllegalMoveException {
        player.drawCards(pile);
        System.out.println("Player " + player.getName() + " drawed cards.");
        System.out.println();
    }
}
