package gui;

import game.EndOfGameException;
import game.GameField;
import game.IllegalMoveException;
import game.phases.PhaseDrawing;
import io.bitbucket.plt.sdp.bohnanza.gui.*;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.util.*;

/**
 * Main class of the gui
 */
public class Gui {

    private final HashMap<CardObject, Card> cards = new HashMap<>();
    private final List<Container> containers = new ArrayList<>();
    private final GUI gui;
    private final GameField game;
    private final Button nextPhaseButton;
    private Compartment phaseInfo;


    public Gui() {
        int playerCount = getPlayerCount();
        game = new GameField(playerCount, playerCount <= 2);
        gui = new GUI(new Size(1500, 1000), new Size(100, 200), new Size(200, 400), new Color(255, 255, 255), new Color(0, 0, 0));
        gui.setCardDnDHandler(this::dndUpdate);
        nextPhaseButton = addButton("Next Phase", new Coordinate(game.getExtension()? 900: 1100, 0), new Size(100, 50), this::nextPhase);
        for (int i = 0; i < game.getPlayers().size(); i++) {
            new Player(this, new Coordinate(0, i * 350), new Size(1100, 350), game.getPlayers().get(i), containers, game.getExtension());
        }
        if (game.getExtension()) {
            containers.add(new DiscardPile(this, new Coordinate(900, 50), new Size(100, 300), game.getPile().getDiscardPile()));
            containers.add(new DiscoverField(this, new Coordinate(1100, 0), new Size(100, 300), game.getDiscoverArea().getDiscoverFields().get(0), 0));
            containers.add(new DiscoverField(this, new Coordinate(1200, 0), new Size(100, 300), game.getDiscoverArea().getDiscoverFields().get(1), 1));
            containers.add(new DiscoverField(this, new Coordinate(1300, 0), new Size(100, 300), game.getDiscoverArea().getDiscoverFields().get(2), 2));
            containers.add(new Boss(this, new Coordinate(1100, 300), new Size(100, 300), game.getAlCabohne()));
            containers.add(new Boss(this, new Coordinate(1200, 300), new Size(100, 300), game.getDonCorlebohne()));
            if (game.getJoeBohnano() != null) {
                containers.add(new Boss(this, new Coordinate(1300, 300), new Size(100, 300), game.getJoeBohnano()));
            }
            containers.add(new MafiaBank(this, new Coordinate(1200, 600), new Size(100, 300), game.getMafiaBank()));
        } else {
            containers.add(new TradingArea(this, new Coordinate(1100, 50), new Size(100, 300), game.getTradingArea(), 0));
            containers.add(new TradingArea(this, new Coordinate(1100, 350), new Size(100, 300), game.getTradingArea(), 1));
        }
        reload();
        try {
            gui.start();
        } catch (EndOfGameException ignored){
            showWinner();
        }

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

    /**
     * Wrap a game Card and register the wrapped card with the game
     * @param card the card to wrap
     * @param pos the position of the card in the GUI
     * @param currentContainer the container the card is to be contained in
     * @return the wrapped card
     */
    public Card addCard(game.cards.Card card, Coordinate pos, Container currentContainer) {
        var cardObject = gui.addCard(Util.toGUIType(card.cardType()), pos);
        var cardT = new Card(this, currentContainer, card, cardObject);
        cards.put(cardObject, cardT);
        return cardT;
    }

    public void removeCard(CardObject cardObject) {
        gui.removeCard(cardObject);
    }

    public CardObject[] cardsInCompartment(Compartment compartment) {
        return gui.getCardObjectsInCompartment(compartment);
    }

    public void removeCompartment(Compartment compartment) {
        gui.removeCompartment(compartment);
    }

    /**
     * @return the player whose turn it is
     */
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
            if (game.getTurnPlayer().getPhase() instanceof PhaseDrawing) {
                game.getTurnPlayer().drawCards(game.getPile());
                game.getTurnPlayer().nextPhase();
            }
            if (phaseInfo != null) {
                gui.removeCompartment(phaseInfo);
            }
            reload();
        } catch (IllegalMoveException ignored) {
        }
    }

    private void reload(){
        containers.forEach(Reloadable::reload);
        gui.removeCompartment(phaseInfo);
        phaseInfo = gui.addCompartment(new Coordinate(1100, 700), new Size(100, 40), "Player " +
                game.getTurnPlayer().getName() + System.lineSeparator() +
                game.getTurnPlayer().getPhase().getClass().getSimpleName()
        );
    }

    /**
     * Reload all Reloadables of the specified Concrete Types
     * @param reload the concrete Types of Reloadable to reload
     */
    @SafeVarargs
    public final void reload(Class<? extends Reloadable>... reload){
        containers.stream().filter(o-> Arrays.asList(reload).contains(o.getClass()))
                .forEach(Reloadable::reload);
    }

    private void showWinner() {
        var winMessage = new StringBuilder();
        game.getPlayers().stream()
                .sorted(Comparator.comparingInt(p -> p.getScore()))
                .forEach(p->winMessage.append(p.getName())
                        .append(": ")
                        .append(p.getScore())
                        .append(System.lineSeparator())
                );
        if(game.getExtension()){
            winMessage.append("Mafia: ")
                    .append(game.getMafiaBank().getCoins().size())
                    .append(System.lineSeparator());
        }

        var message = new MessageDialog(null, "Winners", null, winMessage.toString(), MessageDialog.INFORMATION, 0);
        message.open();
    }

    private static int getPlayerCount() {
        Display display = new Display();
        Shell shell = new Shell(display);
        InputDialog inputDialog = new InputDialog(shell, "Enter Amount of players", "Enter 1 or 2 to play \"Al Cabohne\".\n" +
                "Enter 3, 4 or 5 to play \"Bohnanza\".", null, newText -> {
            try{
                int i = Integer.parseInt(newText);
                if (i < 1 || i > 5) {
                    return "Player amount must be between 1 and 5";
                }
            } catch (NumberFormatException e){
                return "Not a Number";
            }
            return null;
        });
        inputDialog.open();
        int playerCount = Integer.parseInt(inputDialog.getValue());
        inputDialog.close();
        shell.close();
        display.close();
        return playerCount;
    }
}
