package gui;

import io.bitbucket.plt.sdp.bohnanza.gui.CardObject;

public class Card extends GuiElement{

    private Container currentContainer;
    private final CardObject cardObject;
    private final game.cards.Card card;

    public Card(Gui gui, Container currentContainer, game.cards.Card card, CardObject cardObject) {
        super(gui);
        this.currentContainer = currentContainer;
        this.cardObject = cardObject;
        this.card = card;
    }

    public CardObject getCardObject() {
        return cardObject;
    }

    public game.cards.Card getCardType() {
        return card;
    }

    public Container getCurrentContainer() {
        return currentContainer;
    }

    public void setCurrentContainer(Container currentContainer) {
        this.currentContainer = currentContainer;
    }

    public void dispose() {
        getGui().removeCard(cardObject);
    }
}
