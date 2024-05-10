public class IllegalMoveException extends Exception{

    /**
     * Thrown indicates that a method violates a game rule.
     */
    public IllegalMoveException() {}

    public IllegalMoveException(String message) {
        super(message);
    }
}
