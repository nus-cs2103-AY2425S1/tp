package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Undoes the previous command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes previous user command.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undid previous command: ";

    @Override
    public CommandResult execute(Model model) {
        Command previousCommand = model.getPreviousCommand();
        previousCommand.undo(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void undo(Model model) {

    }
}
