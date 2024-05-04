import java.util.List;

public class Player {
    private final Hand hand;
    private Field[] field;
    private final List<Card> tradedCards;
    private final List<Card> coins;

    public Player() {
        this.hand = new Hand();
        this.field = new Field[2];
        tradedCards = null;
        coins = null;
    }


    public void plant(int field, Card card) {

    }

    public void harvest(int field) {

    }

    public void tradeCards(List<Card> send, List<Card> receive) {

    }

    public void drawCards() {

    }

    public void buyThirdField(List<Card> payedCoins) {

    }

    public int getPoints() {
        return 0;
    }

}
