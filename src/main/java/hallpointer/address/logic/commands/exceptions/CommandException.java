package hallpointer.address.logic.commands.exceptions;

import hallpointer.address.logic.commands.Command;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 */
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified details {@code message} and {@code cause}.
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
