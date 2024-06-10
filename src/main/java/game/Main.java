package game;

import game.mafia.AlCabohne;
import game.mafia.DonCorlebohne;
import game.mafia.JoeBohnano;

public class Main {

    public static void main(String[] args) throws IllegalMoveException {
        GameField gameField = new GameField(1);
        TradingArea tradingArea = gameField.getTradingArea();

        AlCabohne alCabohne = gameField.getAlCabohne();
        DonCorlebohne donCorlebohne = gameField.getDonCorlebohne();

        Player p1 = gameField.getPlayers().get(0);
//        Player p2 = gameField.getPlayers().get(1);

        bossFields(alCabohne, donCorlebohne);

        System.out.println("[ P1: " + p1.getHand().getHandPile() + " ]");
//        System.out.println("[ P2: " + p2.getHand().getHandPile() + " ]");

        p1.plant(0, p1.getHand().getHandPile().getFirst());

        p1.nextPhase();


//        p1.nextPhase();

//        tradingFields(tradingArea);
//        p2.takeTradingCards(0);
//        p2.putTradingCardsToDiscard(1);
//        p2.putTradingCardsToDiscard(2);

//        tradingFields(tradingArea);
//        playerField(p2);
//        p2.nextPhase();
    }

    private static void bossFields(AlCabohne alCabohne, DonCorlebohne donCorlebohne) {
        System.out.print("[ Al Cabohne: " + alCabohne.getField().getCardType() + ", "
                + alCabohne.getField().getCardAmount() + " ]");
        System.out.print("[ Don Corlebohne: " + donCorlebohne.getField().getCardType() + ", "
                + donCorlebohne.getField().getCardAmount() + " ]");
        System.out.println();
    }

    private static void tradingFields(TradingArea tradingArea) {
        System.out.println("Trading Field 1: " + tradingArea.getTradingFields().get(0).getCardType() + ", "
                + tradingArea.getTradingFields().get(0).getCardAmount());
        System.out.println("Trading Field 2: " + tradingArea.getTradingFields().get(1).getCardType() + ", "
                + tradingArea.getTradingFields().get(1).getCardAmount());
        System.out.println("Trading Field 3: " + tradingArea.getTradingFields().get(2).getCardType() + ", "
                + tradingArea.getTradingFields().get(2).getCardAmount());
    }

    private static void playerField(Player player) {
        for (int i = 0; i < player.getFields().length; i++) {
            System.out.println("Field " + (i+1) + ": " + player.getFields()[i].getCardType() + ", "
                    + player.getFields()[i].getCardAmount());
        }
    }
}
