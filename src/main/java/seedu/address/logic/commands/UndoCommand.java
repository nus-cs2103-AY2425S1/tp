package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

//Code adapted from AB4 undo/redo feature with modifications
//With reference to AB3 proposed implementation of undo/redo feature in the DG
/**
 * Undoes the previous command.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo successful!";
    public static final String MESSAGE_FAILURE = "No commands to undo!\n"
            + "Undo command works for add, delete(Person only), edit, clear commands";

    /**
     * Executes the undo command.
     *
     * @param model The model in which the command should operate.
     * @return The result of the command execution.
     * @throws CommandException If there are no commands to undo.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canUndoAddressBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
