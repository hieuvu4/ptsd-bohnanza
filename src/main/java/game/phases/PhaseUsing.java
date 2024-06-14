package game.phases;

import game.*;
import game.cards.Card;

public class PhaseUsing extends Phase {

    public PhaseUsing() {
        System.out.println("Phase Using");
    }

    @Override
    public void takeDiscoverCards(final Player player, int discoverCardFieldNumber) throws IllegalMoveException {
        Field discoverField = getField(player, discoverCardFieldNumber);

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
    public void putDiscoverCardsToDiscard(Player player, int discoverCardFieldNumber) throws IllegalMoveException {
        Field discoverField = getField(player, discoverCardFieldNumber);

        if (discoverField.getCardType() == null) throw new IllegalMoveException();

        Pile pile = player.getGameField().getPile();

        for(int i = 0; i < discoverField.getCardAmount(); i++) {
            pile.getDiscardPile().add(new Card(discoverField.getCardType()));
        }

        discoverField.clear();
    }

    private static Field getField(Player player, int discoverCardFieldNumber) {
        if (discoverCardFieldNumber < 0 || discoverCardFieldNumber > 2) {
            throw new IllegalArgumentException("Discover card field number must be between 0 and 2.");
        }

        DiscoverArea discoverArea = player.getGameField().getDiscoverArea();
        Field discoverField = discoverArea.getDiscoverFields().get(discoverCardFieldNumber);
        return discoverField;
    }
}
