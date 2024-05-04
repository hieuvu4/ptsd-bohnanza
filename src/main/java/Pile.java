import java.util.List;

public class Pile {
    private final List<Card> cards;
    private final List<Card> discardPile;

    public Pile() {
        cards = null;
        discardPile = null;
    }


    public Card drawCard() { // impl auto reshuffle
        return null;
    }

    public void discardCard(Card card){
        discardCard(card, 1);
    }

    public void discardCard(Card card, int count){

    }
}
