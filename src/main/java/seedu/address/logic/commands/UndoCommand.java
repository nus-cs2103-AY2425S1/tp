package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the most recently executed command
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undone the previous command";
    public static final String MESSAGE_COMMAND_CANNOT_BE_UNDONE = "The previous command cannot be undone";
    public static final String MESSAGE_NO_PREVIOUS_COMMAND = "There is no previous command";


    private Command previousCommand;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        this.previousCommand = model.getPreviousCommand();
        if (previousCommand == null) {
            throw new CommandException(MESSAGE_NO_PREVIOUS_COMMAND);
        }
        if (!(previousCommand instanceof UndoableCommand command)) {
            throw new CommandException(MESSAGE_COMMAND_CANNOT_BE_UNDONE);
        }
        command.undo(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
