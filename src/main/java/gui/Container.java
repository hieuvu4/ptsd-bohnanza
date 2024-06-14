package gui;

import io.bitbucket.plt.sdp.bohnanza.gui.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Container extends GuiElement implements Reloadable {

    protected final List<Card> containedCards = new ArrayList<>();
    private final Compartment compartment;

    public Container(Gui gui, Coordinate pos, Size size, String label) {
        super(gui);
        compartment = gui.addCompartment(pos, size, label);
    }

    public void moveCardTo(Card card, Container container) {
        if (containedCards.remove(card)){
            container.containedCards.add(card);
        }
        card.setCurrentContainer(container);
    }

    public void removeCard(Card card) {
        containedCards.remove(card);
    }

    public Card addCard(game.Card gameCard) {
        var card = getGui().addCard(gameCard, upperLeftCorner(), this);
        containedCards.add(card);
        return card;
    }

    public void clear() {
        containedCards.forEach(Card::dispose);
        containedCards.clear();
    }

    public void formatCards() {
        var cards = getGui().cardsInCompartment(this.compartment);
        compartment.centerHorizontal(cards);
        compartment.distributeVertical(cards);
    }

    public Coordinate upperLeftCorner(){
        return compartment.upperLeft;
    }

    public boolean containsPoint(Coordinate point) {
        return compartment.upperLeft.x <= point.x &&
                compartment.upperLeft.x + compartment.size.width >= point.x &&
                compartment.upperLeft.y <= point.y &&
                compartment.upperLeft.y + compartment.size.height >= point.y;
    }

    public abstract boolean getFrom(Container container, Card card);

    public boolean putInHand(Hand hand, Card card) {
        return false;
    }

    public boolean putInField(Field field, Card card){
        return false;
    }

    public boolean putInOfferField(OfferField offerField, Card card) {
        return false;
    }

    protected boolean putInTradedCards(TradedCards tradedCards, Card card) {
        return false;
    }
}
