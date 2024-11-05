package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Undoes the latest modification done by user to the address book,
 * such as add, edit and so on, if any.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Undoes the latest modification done by user to the address book, if any.\n"
        + "Example: "
        + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("This is the undo command");
    }
}
