package gui;

import io.bitbucket.plt.sdp.bohnanza.gui.Compartment;
import io.bitbucket.plt.sdp.bohnanza.gui.Coordinate;
import io.bitbucket.plt.sdp.bohnanza.gui.Size;

import java.util.List;

/**
 * Class constructing and containing all the elements needed per player
 */
public class Player {

    private final Field[] fields = new Field[3];
    private final OfferField[] offerFields = new OfferField[2];
    private final Hand hand;
    private final Compartment fieldBackground;
    private final TradedCards tradedCards;
    private final Coins coins;

    public Player(Gui gui, Coordinate pos, Size size, game.Player player, List<Container> containerList, boolean extension) {
        int container_width =  size.width /11;

        coins = new Coins(gui, new Coordinate(pos.x + container_width * 7, pos.y),
                new Size(container_width, size.height), player);
        containerList.add(coins);

        for (int i = 0; i < fields.length; i++) {
            fields[i] = new Field(gui, new Coordinate(pos.x + i * container_width * 2, pos.y),
                    new Size(container_width * 2, size.height), i, player, coins);
            containerList.add(fields[i]);
        }

        hand = new Hand(gui, new Coordinate(pos.x + container_width * 6, pos.y),
                new Size(container_width, size.height), player);
        containerList.add(hand);


        if (extension){
            tradedCards = null;
        } else {
            tradedCards = new TradedCards(gui, new Coordinate(pos.x + container_width * 8, pos.y),
                    new Size(container_width, size.height), player);

            for (int i = 0; i < offerFields.length; i++) {
                offerFields[i] = new OfferField(gui, new Coordinate(pos.x + container_width * 9 + i * container_width, pos.y),
                        new Size(container_width, size.height), player, i);
                containerList.add(offerFields[i]);
            }
            containerList.add(tradedCards);
        }
        fieldBackground = gui.addCompartment(pos, new Size(pos.x + container_width * 6, (size.height * 6) / 7),
                "", "BOHNENFELD_ALLE");
    }
}
