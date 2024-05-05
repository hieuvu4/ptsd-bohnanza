import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {

    private final List<Card> handPile = new ArrayList<>();


    public List<Card> getHandPile() {
        return Collections.unmodifiableList(handPile);
    }

    public Card popTopCard() {
        return null;
    }

    public void addCard(Card card) {

    }

    public void removeCard(int index) {

    }
}

