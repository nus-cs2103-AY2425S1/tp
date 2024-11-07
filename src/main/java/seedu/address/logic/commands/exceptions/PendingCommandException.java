package seedu.address.logic.commands.exceptions;

public class PendingCommandException extends CommandException {
    public PendingCommandException(String message) {
        super(message);
    }
}
