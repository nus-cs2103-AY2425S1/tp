package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the latest modification done by user to the address book,
 * such as add, edit and so on, if any.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Successfully undone the latest change to contact data!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Undoes the latest modification done by user to the address book, if any.\n"
        + "Example: "
        + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.undoAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
