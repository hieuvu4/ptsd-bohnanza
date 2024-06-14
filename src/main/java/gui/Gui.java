package gui;

import game.GameField;
import game.IllegalMoveException;
import game.phases.Phase4;
import io.bitbucket.plt.sdp.bohnanza.gui.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Gui {

    private final HashMap<CardObject, Card> cards = new HashMap<>();
    private final List<Container> containers = new ArrayList<>();
    private final GUI gui;
    private final GameField game;
    private final Button nextPhaseButton;


    public Gui() {
        game = new GameField(2);
        gui = new GUI(new Size(1500, 1000), new Size(100, 200), new Size(50, 100), new Color(255, 255, 255), new Color(0, 0, 0));
        gui.setCardDnDHandler(this::dndUpdate);
        nextPhaseButton = addButton("Next Phase", new Coordinate(1000, 0), new Size(100, 50), this::nextPhase);
        for (int i = 0; i < game.getPlayers().size(); i++) {
            new Player(this, new Coordinate(0, i * 350), new Size(1000, 350), game.getPlayers().get(i), containers);
        }
        containers.add(new TradingArea(this, new Coordinate(1000, 200), new Size(100, 150), game.getTradingArea(), 0));
        containers.add(new TradingArea(this, new Coordinate(1000, 350), new Size(100, 150), game.getTradingArea(), 1));
        gui.start();
    }

    public static void main(String[] args) {
        Gui gui = new Gui();
    }

    public Button addButton(String label, Coordinate pos, Size size, ButtonHandler buttonHandler){
        return gui.addButton(label, pos, size, buttonHandler);
    }

    public Compartment addCompartment(Coordinate upperLeft, Size size, String label) {
        return gui.addCompartment(upperLeft, size, label);
    }

    public Compartment addCompartment(Coordinate upperLeft, Size size, String label, String imageName) {
        return gui.addCompartment(upperLeft, size, label, imageName);
    }

    public Card addCard(game.Card cardType, Coordinate pos, Container currentContainer) {
        var cardObject = gui.addCard(Util.toGUIType(cardType), pos);
        var card = new Card(this, currentContainer, cardType, cardObject);
        cards.put(cardObject, card);
        return card;
    }

    public void removeCard(CardObject cardObject) {
        gui.removeCard(cardObject);
    }

    public CardObject[] cardsInCompartment(Compartment compartment) {
        return gui.getCardObjectsInCompartment(compartment);
    }

    public game.Player turnPlayer() {
        return game.getTurnPlayer();
    }

    private Coordinate dndUpdate(CardObject card, Coordinate mouseCoordinate, Coordinate newCoordinate){
        var guiCard = cards.get(card);
        for (Container container: containers){
            if (container.containsPoint(mouseCoordinate)){
                if (container.getFrom(guiCard.getCurrentContainer(), guiCard)){
                    guiCard.getCurrentContainer().moveCardTo(guiCard, container);
                    guiCard.getCardObject().showFront(true);
                    return newCoordinate;
                }
            }
        }
        return null;
    }

    private void nextPhase(Button button) {
        try {
            game.getTurnPlayer().nextPhase();
            if (game.getTurnPlayer().getPhase() instanceof Phase4) {
                game.getTurnPlayer().drawCards(game.getPile());
                game.getTurnPlayer().nextPhase();
            }
            reload();
        } catch (IllegalMoveException ignored) {
        }
    }

    private void reload(){
        containers.forEach(Reloadable::reload);
    }

    public void reloadTrade() {
        containers.stream().filter(o -> List.of(TradedCards.class, TradingArea.class).contains(o.getClass()))
                .forEach(Reloadable::reload);
    }
}
