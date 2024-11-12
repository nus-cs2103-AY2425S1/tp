package seedu.address.logic.commands.exceptions;

/**
 * Represents an error which occurs during the execution of a ConfirmCommand.
 */
public class PendingCommandException extends CommandException {
    public PendingCommandException(String message) {
        super(message);
    }
}
