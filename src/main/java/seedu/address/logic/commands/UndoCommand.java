package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the latest command. Throws an error if there is no previous command/ if previous command does
 * not make changes to the Model.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Your latest command has been undone.";
    public static final String MESSAGE_NO_COMMAND_TO_UNDO = "Please input a command first in order to undo it.";

    @Override
    protected CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasPreviousCommand()) {
            throw new CommandException(MESSAGE_NO_COMMAND_TO_UNDO);
        }
        model.undoCommand();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
