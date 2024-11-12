package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

//Code adapted from AB4 undo/redo feature with modifications
//With reference to AB3 proposed implementation of undo/redo feature in the DG
/**
 * Redoes the previously undone command.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo successful!";
    public static final String MESSAGE_FAILURE = "No commands to redo!\n"
            + "Redo command works for after an undo command!";

    /**
     * Executes the redo command.
     *
     * @param model The model in which the command should operate.
     * @return The result of the command execution.
     * @throws CommandException If there are no commands to redo.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canRedoAddressBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
