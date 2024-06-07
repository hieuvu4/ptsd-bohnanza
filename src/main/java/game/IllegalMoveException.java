package game;

public class IllegalMoveException extends Exception{

    /**
     * Thrown indicates that a method violates a game rule.
     */
    public IllegalMoveException() {}

    public IllegalMoveException(final String message) {
        super(message);
    }
}
