package spleetwaise.transaction.logic.commands.exceptions;

import spleetwaise.commons.exceptions.SpleetWaiseCommandException;

/**
 * Represents an error that occurs during command execution in the transaction component.
 */
public class CommandException extends SpleetWaiseCommandException {
    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
