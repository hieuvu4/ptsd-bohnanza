import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Card> handPile = new ArrayList<>();


    public List<Card> getHandPile() {
        return handPile;
    }

    public void addCard(Card card) {
        handPile.add(card);
    }

    public void removeCard(int index) {
        handPile.remove(index);
    }
}

