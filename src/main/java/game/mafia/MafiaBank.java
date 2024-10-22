package game.mafia;

import game.cards.Card;
import game.cards.CardType;

import java.util.ArrayList;
import java.util.List;

public class MafiaBank {

    List<Card> coins;

    public MafiaBank() {
        coins = new ArrayList<>();
    }

    public void addCoin(Card card) {
        coins.add(card);
    }

    public List<Card> getCoins() {
        return coins;
    }
}
