package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command to undo the last command in the address book.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = ":undo";
    public static final String MESSAGE_SUCCESS = "Undo successful!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.canUndoAddressBook()) {
            return new CommandResult(MESSAGE_FAILURE);
        }
        model.undoAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
