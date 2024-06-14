package game;

import java.util.Objects;
import java.util.Scanner;

public class ExtensionClient {

    public static void main(String[] args) throws IllegalMoveException {

        System.out.println("AL CABOHNE");

        GameField gameField = setUpGame();

        run(gameField);
    }

    private static void discoverFields(GameField gameField) {
        System.out.println("[ Discover Field 1: " + gameField.getDiscoverArea().getDiscoverFields().get(0).getCardType()
                + ", " + gameField.getDiscoverArea().getDiscoverFields().get(0).getCardAmount() + "]");
        System.out.println("[ Discover Field 2: " + gameField.getDiscoverArea().getDiscoverFields().get(1).getCardType()
                + ", " + gameField.getDiscoverArea().getDiscoverFields().get(1).getCardAmount() + "]");
        System.out.println("[ Discover Field 3: " + gameField.getDiscoverArea().getDiscoverFields().get(2).getCardType()
                + ", " + gameField.getDiscoverArea().getDiscoverFields().get(2).getCardAmount() + "]");
    }

    private static void bossFields(GameField gameField) {
        System.out.print("[ Al Cabohne: " + gameField.getAlCabohne().getField().getCardType() + ", "
                + gameField.getAlCabohne().getField().getCardAmount() + " ]");
        System.out.print("[ Don Corlebohne: " + gameField.getDonCorlebohne().getField().getCardType() + ", "
                + gameField.getDonCorlebohne().getField().getCardAmount() + " ]");
        if(gameField.getPlayers().size() == 1) {
            System.out.print("[ Joe Bohnano: " + gameField.getJoeBohnano().getField().getCardType() + ", "
                    + gameField.getJoeBohnano().getField().getCardAmount() + " ]");
        }
        System.out.println("[ Bank: "  + gameField.getMafiaBank().getCoins().size() + " ]");
        System.out.println();
    }

    private static void playerField(Player player) {
        for (int i = 0; i < player.getFields().length; i++) {
            System.out.println("Field " + (i+1) + ": " + player.getFields()[i].getCardType() + ", "
                    + player.getFields()[i].getCardAmount());
        }
        System.out.println();
    }

    private static void playerHand(Player player) {
        System.out.print("[ Player " + player.getName() + "'s hand: ");
        for(int i = 0; i <  player.getHand().getHandPile().size(); i++ ) {
            System.out.print(" " + player.getHand().getHandPile().get(i).getName() + " ");
        }
        System.out.println("]");
        System.out.println();
    }

    private static GameField setUpGame() {
        GameField gameField = null;
        Player p1;
        Player p2;
        System.out.println("Enter player amount: ( 1 / 2 )");
        Scanner scanner = new Scanner(System.in);
        int playerAmount = scanner.nextInt();

        if (playerAmount == 1) {
            gameField = new GameField(1, true);
            p1 = gameField.getPlayers().getFirst();

            bossFields(gameField);
            playerHand(p1);
        }
        else if (playerAmount == 2) {
            gameField = new GameField(2, true);
            p1 = gameField.getPlayers().getFirst();
            p2 = gameField.getPlayers().getLast();

            bossFields(gameField);
            playerHand(p1);
            playerHand(p2);
        }
        else {
            System.out.println("Invalid input.");
            setUpGame();
        }
        return gameField;
    }

    private static void run(GameField gameField) throws IllegalMoveException {
        System.out.print("Actions: hand | field | discover | boss | harvest | next");
        if(gameField.getTurnPlayer().getPhase().getClass().getSimpleName().equals("PhasePlanting"))
            System.out.print(" | plant");

        if(gameField.getTurnPlayer().getPhase().getClass().getSimpleName().equals("PhaseCultivating"))
            System.out.print(" | cultivate");

        System.out.println();

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine().trim();

        switch (command) {
            case "hand" -> {
                playerHand(gameField.getTurnPlayer());
                run(gameField);
            }
            case "field" -> {
                playerField(gameField.getTurnPlayer());
                run(gameField);
            }
            case "discover" -> {
                discoverFields(gameField);
                run(gameField);
            }
            case "boss" -> {
                bossFields(gameField);
                run(gameField);
            }
            case "harvest" -> {
                harvest(gameField);
                run(gameField);
            }
            case "plant" -> {
                plant(gameField);
                run(gameField);
            }
            case "cultivate" -> {
                cultivate(gameField);
                run(gameField);
            }
            case "next" -> {
                gameField.getTurnPlayer().nextPhase();
                run(gameField);
            }
            case "score" -> {
                System.out.println("Player " + gameField.getTurnPlayer().getName() + " currently has "
                        + gameField.getTurnPlayer().getScore() + " coins!");
            }
            default -> {
                System.out.println("Invalid input.");
                run(gameField);
            }
        }
        System.out.println();
    }

    private static void plant(GameField gameField) throws IllegalMoveException {
        System.out.println("Which field should be planted? ( 1 / 2 )");
        Scanner fieldNumber = new Scanner(System.in);
        if (Objects.equals(fieldNumber.next().trim(), "1")) {
            gameField.getTurnPlayer().plant(0, gameField.getTurnPlayer().getHand().popTopCard());
            playerHand(gameField.getTurnPlayer());
            playerField(gameField.getTurnPlayer());
        }
        else if (Objects.equals(fieldNumber.next().trim(), "2")) {
            gameField.getTurnPlayer().plant(1, gameField.getTurnPlayer().getHand().popTopCard());
            playerHand(gameField.getTurnPlayer());
            playerField(gameField.getTurnPlayer());
        }
        else {
            System.out.println("Invalid input.");
            run(gameField);
        }
    }

    private static void cultivate(GameField gameField) throws IllegalMoveException {
        System.out.println("Which card should be cultivated? ( 1 / 2 / 3 )" );
        Scanner discoverCard = new Scanner(System.in);
        if (Objects.equals(discoverCard.next().trim(), "1")) {
            cultivating(gameField, 0);
        }
        else if (Objects.equals(discoverCard.next().trim(), "2")) {
            cultivating(gameField, 1);
        }
        else if (Objects.equals(discoverCard.next().trim(), "3")) {
            cultivating(gameField, 2);
        } else {
            System.out.println("Invalid input.");
            run(gameField);
        }
    }

    private static void cultivating(GameField gameField, int discoverCardFieldNumber) throws IllegalMoveException {
        if (gameField.getPlayers().size() == 1) {
            System.out.println("On which field should it be planted? ( own / al / don / joe )");
        } else {
            System.out.println("On which field should it be planted? ( own / al / don )");
        }
        Scanner field = new Scanner(System.in);
        if (Objects.equals(field.next().trim(), "own")) {
            gameField.getTurnPlayer().cultivateOwnField(discoverCardFieldNumber);
        } else if (Objects.equals(field.next().trim(), "al")) {
            gameField.getTurnPlayer().cultivateBossField(discoverCardFieldNumber, gameField.getAlCabohne());
        } else if (Objects.equals(field.next().trim(), "don")) {
            gameField.getTurnPlayer().cultivateBossField(discoverCardFieldNumber, gameField.getDonCorlebohne());
        } else if (Objects.equals(field.next().trim(), "joe")) {
            gameField.getTurnPlayer().cultivateBossField(discoverCardFieldNumber, gameField.getJoeBohnano());
        } else {
            System.out.println("Invalid input.");
            run(gameField);
        }
    }

    private static void harvest(GameField gameField) throws IllegalMoveException {
        if (gameField.getTurnPlayer().getFields().length == 3) {
            System.out.println("Which field should be harvested? ( 1 / 2 / 3)" );
        } else {
            System.out.println("Which field should be harvested? ( 1 / 2 )" );
        }
        Scanner fieldNumber = new Scanner(System.in);
        if (Objects.equals(fieldNumber.next().trim(), "1")) {
            gameField.getTurnPlayer().harvest(0);
        }
        else if (Objects.equals(fieldNumber.next().trim(), "2")) {
            gameField.getTurnPlayer().harvest(1);
        }
        else if (Objects.equals(fieldNumber.next().trim(), "3")) {
            gameField.getTurnPlayer().harvest(2);
        } else {
            System.out.println("Invalid input.");
            run(gameField);
        }
    }
}
