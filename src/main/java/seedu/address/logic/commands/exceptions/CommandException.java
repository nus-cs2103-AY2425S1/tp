package seedu.address.logic.commands.exceptions;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 */
public class CommandException extends Exception {
    private static final Logger logger = LogsCenter.getLogger(CommandException.class);

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message}.
     */
    public CommandException(String message) {
        super(message);
        logger.info(String.format("Command Exception: [%s]", message));
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
