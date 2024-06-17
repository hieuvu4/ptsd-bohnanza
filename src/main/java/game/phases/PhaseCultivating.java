package game.phases;

import game.*;
import game.cards.Card;
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
            if(playerField.getCardType() == discoverField.getCardType()) {
                cardFound = true;
                index = i;
                break;
            }
        }
        if(!cardFound) {
            for(int i = 0; i < player.getFields().length; i++) {
                Field playerField = player.getField(i);
                if(playerField.getCardType() == null) {
                    cardFound = true;
                    index = i;
                    break;
                }
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

        Boss[] bosses = (player.getGameField().getPlayers().size() == 2)?
                new Boss[]{player.getGameField().getAlCabohne(), player.getGameField().getDonCorlebohne()} :
                new Boss[]{player.getGameField().getAlCabohne(), player.getGameField().getDonCorlebohne(),
                        player.getGameField().getJoeBohnano()};

        for(Boss b : bosses) {
            if (b.equals(boss)) {
                continue;
            }
            if (b.getField().getCardType() != null && b.getField().getCardType() == player.getGameField().getDiscoverArea().getDiscoverFields().get(discoverCardFieldNumber).getCardType()){
                throw new IllegalMoveException("Another boss already has a card of type");
            }
        }

        if (discoverCardFieldNumber < 0 || discoverCardFieldNumber > 2)
            throw new IllegalArgumentException("Discover card field number must be between 0 and 2.");
        DiscoverArea discoverArea = player.getGameField().getDiscoverArea();
        Field discoverField = discoverArea.getDiscoverFields().get(discoverCardFieldNumber);

        if (discoverField.getCardType() == null) throw new IllegalMoveException();

        if(boss.getField().getCardType() == null) {
            boss.getField().setCardType(discoverField.getCardType());
        }

        if (discoverField.getCardType() != boss.getField().getCardType()) {
            boss.harvest();

            boss.getField().setCardType(discoverField.getCardType());
        }

        for(int i = 0; i < discoverField.getCardAmount(); i++) boss.getField().increaseCardAmount();

        discoverField.clear();
    }

    @Override
    public void giveBossCardFromHandPile(Player player, Card card, Boss boss) throws IllegalMoveException {
        if(!player.getHand().getHandPile().contains(card))
            throw new IllegalMoveException("No such card in hand.");

        if(!boss.getField().isEmpty())
            throw new IllegalArgumentException("Field of boss is not empty");

        Boss[] bosses = (player.getGameField().getPlayers().size() == 2)?
                new Boss[]{player.getGameField().getAlCabohne(), player.getGameField().getDonCorlebohne()} :
                new Boss[]{player.getGameField().getAlCabohne(), player.getGameField().getDonCorlebohne(),
                player.getGameField().getJoeBohnano()};

        for(Boss b : bosses) {
            if (b.getField().getCardType() != null && b.getField().getCardType() == card.cardType()){
                throw new IllegalMoveException("Another boss already has a card of type " + card.cardType());
            }
        }

        boss.getField().setCardType(card.cardType());
        boss.getField().increaseCardAmount();
        player.getHand().getHandPile().remove(card);
    }
}
