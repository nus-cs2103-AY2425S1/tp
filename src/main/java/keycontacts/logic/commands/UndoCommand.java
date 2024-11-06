package keycontacts.logic.commands;

import static java.util.Objects.requireNonNull;

import keycontacts.logic.commands.exceptions.CommandException;
import keycontacts.model.Model;

/**
 * Undoes the latest commit to the model
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reverts the student directory to the state before the "
            + "last command that modified it.";
    public static final String MESSAGE_SUCCESS = "The latest command has been undone.";
    public static final String MESSAGE_LAST_VERSION = "No earlier version found.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoStudentDirectory()) {
            throw new CommandException(MESSAGE_LAST_VERSION);
        }

        model.undoStudentDirectory();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    @Override
    public boolean shouldCommitModel() {
        return false;
    }
}
