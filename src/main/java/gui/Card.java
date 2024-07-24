package gui;

import io.bitbucket.plt.sdp.bohnanza.gui.CardObject;

/**
 * Class uniting game and gui Card
 */
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

    /**
     * @return the card Object that is in the GUI
     */
    public CardObject getCardObject() {
        return cardObject;
    }

    /**
     * @return the card in the game this card represents
     */
    public game.cards.Card getGameCard() {
        return card;
    }

    /**
     * @return the container this card is currently in
     */
    public Container getCurrentContainer() {
        return currentContainer;
    }

    /**
     * @param currentContainer the container to be set as current
     */
    public void setCurrentContainer(Container currentContainer) {
        this.currentContainer = currentContainer;
    }

    /**
     * Remove the card from the GUI
     */
    public void dispose() {
        getGui().removeCard(cardObject);
    }
}
