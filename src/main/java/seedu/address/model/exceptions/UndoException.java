package seedu.address.model.exceptions;

import seedu.address.logic.commands.exceptions.CommandException;

public class UndoException extends CommandException{
    public UndoException(String message) {
        super(message);
    }

    public UndoException(String message, Throwable cause) {
        super(message, cause);
    }
}
