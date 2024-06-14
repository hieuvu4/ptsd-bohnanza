package game.cards;

public record Card(CardType cardType) {

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
}
