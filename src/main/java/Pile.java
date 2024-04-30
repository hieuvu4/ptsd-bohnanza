import java.util.List;

public class Pile {
    private static Pile pile;
    private List<Card> cards;
    private List<Card> discardPile;

    private Pile() {}

    public static Pile getPile() {
        if (pile == null) {
            pile = new Pile();
        }
        return pile;
    }

}
