package gui;

import io.bitbucket.plt.sdp.bohnanza.gui.Compartment;
import io.bitbucket.plt.sdp.bohnanza.gui.Coordinate;
import io.bitbucket.plt.sdp.bohnanza.gui.GUI;
import io.bitbucket.plt.sdp.bohnanza.gui.Size;

import java.util.List;

public class Player {

    private final Field[] fields = new Field[3];
    private final OfferField[] offerFields = new OfferField[2];
    private final Hand hand;
    private final Compartment fieldBackground;
    private final TradedCards tradedCards;

    public Player(Gui gui, Coordinate pos, Size size, game.Player player, List<Container> containerList) {
        int container_width = size.width / 10;
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new Field(gui, new Coordinate(pos.x + i * container_width * 2, pos.y),
                    new Size(container_width * 2, size.height), i, player);
            containerList.add(fields[i]);
        }

        hand = new Hand(gui, new Coordinate(pos.x + container_width * 6, pos.y),
                new Size(container_width, size.height), player);
        containerList.add(hand);
        tradedCards = new TradedCards(gui, new Coordinate(pos.x + container_width * 7, pos.y),
                new Size(container_width, size.height), player);
        for (int i = 0; i < offerFields.length; i++) {
            offerFields[i] = new OfferField(gui, new Coordinate(pos.x + container_width * 8 + i * container_width, pos.y),
                    new Size(container_width, size.height), player, i);
            containerList.add(offerFields[i]);
        }
        containerList.add(tradedCards);


        fieldBackground = gui.addCompartment(pos, new Size(pos.x + (size.width * 6) / 10, (size.height * 6) / 7),
                "", "BOHNENFELD_ALLE");
        hand.reload();
    }

}
