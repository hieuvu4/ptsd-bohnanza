package gui;

import io.bitbucket.plt.sdp.bohnanza.gui.Compartment;
import io.bitbucket.plt.sdp.bohnanza.gui.Coordinate;
import io.bitbucket.plt.sdp.bohnanza.gui.Size;

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

    public Card addCard(game.cards.Card gameCard) {
        var card = getGui().addCard(gameCard, upperLeftCorner(), this);
        containedCards.add(card);
        card.getCardObject().showFront(true);
        return card;
    }

    protected void clear() {
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
}
