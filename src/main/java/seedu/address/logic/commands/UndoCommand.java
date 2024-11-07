package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Represents an Undo command that reverses the last modification made to the address book.
 * This command can be used to restore the previous state of the address book after an edit,
 * addition, or deletion.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Address book has undone previous command!";
    public static final String MESSAGE_FAILURE = "There is no current command to be undone!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.canUndoAddressBook()) {
            model.undoAddressBook();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
