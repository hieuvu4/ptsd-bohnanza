package game.phases;

import game.*;
import game.mafia.Boss;

public class PhaseCultivating extends Phase {

    public PhaseCultivating() {
        System.out.println("Phase Cultivating");
    }

    @Override
    public void cultivateOwnField(Player player, int discoverCardFieldNumber) throws IllegalMoveException {
        if (discoverCardFieldNumber < 0 || discoverCardFieldNumber > 2)
            throw new IllegalArgumentException("Discover card field number must be between 0 and 2.");

        DiscoverArea discoverArea = player.getGameField().getDiscoverArea();
        Field discoverField = discoverArea.getDiscoverFields().get(discoverCardFieldNumber);

        if (discoverField.getCardType() == null) throw new IllegalMoveException();

        boolean cardFound = false;
        int index = -1;
        for(int i = 0; i < player.getFields().length; i++) {
            Field playerField = player.getField(i);
            if(playerField.getCardType() == discoverField.getCardType() || playerField.getCardType() == null) {
                cardFound = true;
                index = i;
                break;
            }
        }
        if (!cardFound) throw new IllegalMoveException();

        for(int i = 0; i < discoverField.getCardAmount(); i++) {
            player.getField(index).setCardType(discoverField.getCardType());
            player.getField(index).increaseCardAmount();
        }

        discoverField.clear();
    }

    @Override
    public void cultivateBossField(Player player, int discoverCardFieldNumber, Boss boss) throws IllegalMoveException {
        if (discoverCardFieldNumber < 0 || discoverCardFieldNumber > 2)
            throw new IllegalArgumentException("Discover card field number must be between 0 and 2.");
        DiscoverArea discoverArea = player.getGameField().getDiscoverArea();
        Field discoverField = discoverArea.getDiscoverFields().get(discoverCardFieldNumber);

        if (discoverField.getCardType() == null) throw new IllegalMoveException();

        if (discoverField.getCardType() != boss.getField().getCardType()) {
            boss.harvest();

            boss.getField().setCardType(discoverField.getCardType());
        }

        for(int i = 0; i < discoverField.getCardAmount(); i++) boss.getField().increaseCardAmount();

        discoverField.clear();
    }
}
