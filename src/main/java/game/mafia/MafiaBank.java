package game.mafia;

import game.cards.CardType;

import java.util.ArrayList;
import java.util.List;

public class MafiaBank {

    List<CardType> coins;

    public MafiaBank() {
        coins = new ArrayList<>();
    }

    public void addCoin(CardType cardType) {
        coins.add(cardType);
    }

    public List<CardType> getCoins() {
        return coins;
    }
}
