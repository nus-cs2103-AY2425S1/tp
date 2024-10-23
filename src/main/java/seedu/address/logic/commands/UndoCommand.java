package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the previous concrete command.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo successful - ";
    public static final String MESSAGE_NO_COMMANDS = "No commands to undo";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNotExecuted();
        requireNonNull(model);
        CommandResult undoResult = model.undoAddressBook();

        if (undoResult == null) {
            throw new CommandException(MESSAGE_NO_COMMANDS);
        }

        isExecuted = true;
        return new CommandResult(MESSAGE_SUCCESS + undoResult.getFeedbackToUser());
    }
}
