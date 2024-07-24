package gui;

import io.bitbucket.plt.sdp.bohnanza.gui.Compartment;
import io.bitbucket.plt.sdp.bohnanza.gui.Coordinate;
import io.bitbucket.plt.sdp.bohnanza.gui.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for Containers containing Cards
 * uses double dispatch to move cards between different Containers
 */
public abstract class Container extends GuiElement implements Reloadable {

    protected final List<Card> containedCards = new ArrayList<>();
    private final Compartment compartment;

    /**
     * Constructs a new Container
     * @param gui the gui
     * @param pos the position of the Container
     * @param size the size of the Container
     * @param label the label to be displayed. may be null
     */
    public Container(Gui gui, Coordinate pos, Size size, String label) {
        super(gui);
        compartment = gui.addCompartment(pos, size, label);
    }

    /**
     * Moves a card from this container to container
     * @param card the card to be moved
     * @param container the container to move the card into
     */
    public void moveCardTo(Card card, Container container) {
        if (containedCards.remove(card)){
            container.containedCards.add(card);
        }
        card.setCurrentContainer(container);
    }

    /**
     * Wraps and adds a game Card to this container. The card is front facing
     * @param gameCard the game Card
     * @return the wrapped card
     */
    public Card addCard(game.cards.Card gameCard) {
        var card = getGui().addCard(gameCard, upperLeftCorner(), this);
        containedCards.add(card);
        card.getCardObject().showFront(true);
        return card;
    }

    /**
     * Empty container and dispose of all contained cards
     */
    protected void clear() {
        containedCards.forEach(Card::dispose);
        containedCards.clear();
    }

    /**
     * Format contained Card to be well distributed in Container
     */
    public void formatCards() {
        var cards = getGui().cardsInCompartment(this.compartment);
        compartment.centerHorizontal(cards);
        compartment.distributeVertical(cards);
    }

    /**
     * @return upperLeftCorner of this container
     */
    public Coordinate upperLeftCorner(){
        return compartment.upperLeft;
    }

    /**
     * Checks whether a point is inside this container
     * @param point the point to be checked
     * @return whether point is inside this container
     */
    public boolean containsPoint(Coordinate point) {
        return compartment.upperLeft.x <= point.x &&
                compartment.upperLeft.x + compartment.size.width >= point.x &&
                compartment.upperLeft.y <= point.y &&
                compartment.upperLeft.y + compartment.size.height >= point.y;
    }

    /**
     * Start of double dispatch
     * Each subclass should implement with return this.putIn{ClassName} if cards can be inserted in it
     * return false otherwise
     * @param container the container the card is retrieved from
     * @param card the card the
     * @return whether the card could be retrieved form the container
     */
    public abstract boolean getFrom(Container container, Card card);

    public boolean putInField(Field field, Card card){
        return false;
    }

    public boolean putInOfferField(OfferField offerField, Card card) {
        return false;
    }

    protected boolean putInTradedCards(TradedCards tradedCards, Card card) {
        return false;
    }

    protected boolean putInBoss(Boss boss, Card card) {
        return false;
    }

    protected boolean putInDiscardPile(Card card) {return false;}
}
