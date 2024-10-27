package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AgentAssist;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Clears the address book.
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
