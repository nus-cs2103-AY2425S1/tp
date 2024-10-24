package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Represents a command to redo the last undone command in the address book.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = ":redo";
    public static final String MESSAGE_SUCCESS = "Redo successful!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";
    public static final String COMMAND_SUMMARY_ACTION = "Redo";
    public static final String COMMAND_SUMMARY_FORMAT =
            ":redo";
    public static final String COMMAND_SUMMARY_EXAMPLES =
            ":redo";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (!model.canRedoAddressBook()) {
            return new CommandResult(MESSAGE_FAILURE);
        }
        model.redoAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
