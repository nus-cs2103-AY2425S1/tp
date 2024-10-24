package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the previous command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes previous user command.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undid previous command.";
    public static final String MESSAGE_INVALID_PREVIOUS_COMMAND = "No Command found to undo";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Command previousCommand = model.getPreviousCommand();
        if (previousCommand == null) {
            throw new CommandException(MESSAGE_INVALID_PREVIOUS_COMMAND);
        }
        previousCommand.undo(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void undo(Model model) {

    }
}
