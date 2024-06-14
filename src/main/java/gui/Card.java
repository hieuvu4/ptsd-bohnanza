package gui;

import io.bitbucket.plt.sdp.bohnanza.gui.CardObject;

public class Card extends GuiElement{

    private Container currentContainer;
    private final CardObject cardObject;
    private final game.Card cardType;

    public Card(Gui gui, Container currentContainer, game.Card cardType, CardObject cardObject) {
        super(gui);
        this.currentContainer = currentContainer;
        this.cardObject = cardObject;
        this.cardType = cardType;
    }

    public CardObject getCardObject() {
        return cardObject;
    }

    public game.Card getCardType() {
        return cardType;
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
