public class PhaseOut extends Phase {

    @Override
    public void harvest(final Player player, final int fieldNumber) throws IllegalMoveException {
        throw new IllegalMoveException("Unable to perform this action in the current phase.");
    }
}
