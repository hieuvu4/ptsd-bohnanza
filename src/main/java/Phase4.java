public class Phase4 extends Phase {

    @Override
    public void harvest(Player player, int fieldNumber) throws IllegalMoveException {
        throw new IllegalMoveException("Unable to perform this action in the current phase.");
    }

}
