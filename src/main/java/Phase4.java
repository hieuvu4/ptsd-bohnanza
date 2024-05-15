public class Phase4 extends Phase {

    @Override
    public void drawCards(Player player, Pile pile) throws IllegalMoveException {
        if(player.getDrawn()) throw new IllegalMoveException("Player already drawn three cards.");
        for(int i = 0; i < 3; i++) player.getHand().addCard(pile.drawCard());
    }
}
