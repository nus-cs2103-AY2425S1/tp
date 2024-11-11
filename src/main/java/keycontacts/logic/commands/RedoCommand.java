package keycontacts.logic.commands;

import static java.util.Objects.requireNonNull;

import keycontacts.logic.commands.exceptions.CommandException;
import keycontacts.model.Model;

/**
 * Redoes the latest commit to the model
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reverts the student directory to the state before the "
            + "last undo command that modified it.";
    public static final String MESSAGE_SUCCESS = "The latest undo command has been undone.";
    public static final String MESSAGE_LAST_VERSION = "No later version found.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoStudentDirectory()) {
            throw new CommandException(MESSAGE_LAST_VERSION);
        }

        model.redoStudentDirectory();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
