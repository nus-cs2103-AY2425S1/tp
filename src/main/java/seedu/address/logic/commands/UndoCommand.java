package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command to undo the last command in the address book.
 */
public class UndoCommand extends Command {

    public static final String SHORT_COMMAND_WORD = ":u";
    public static final String LONG_COMMAND_WORD = ":undo";
    public static final String MESSAGE_SUCCESS = "Undo successful!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";
    public static final String COMMAND_SUMMARY_ACTION = "Undo";
    public static final String COMMAND_SUMMARY_FORMAT = LONG_COMMAND_WORD + "\n" + SHORT_COMMAND_WORD;
    public static final String COMMAND_SUMMARY_EXAMPLES = LONG_COMMAND_WORD + "\n" + SHORT_COMMAND_WORD;

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
