package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the previous command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes previous user command.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undid previous command: %1$s";
    public static final String MESSAGE_INVALID_PREVIOUS_COMMAND = "No Command found to undo";
    private static final boolean IS_UNDOABLE = false;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Command previousCommand = model.getPreviousCommand();
        if (previousCommand == null) {
            throw new CommandException(MESSAGE_INVALID_PREVIOUS_COMMAND);
        }
        previousCommand.undo(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, model.getPreviousInput()));
    }

    @Override
    public void undo(Model model) {

    }

    @Override
    public boolean canBeUndone() {
        return IS_UNDOABLE;
    }
}
