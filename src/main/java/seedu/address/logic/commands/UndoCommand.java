package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command that undoes the last action in the address book.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reverts the address book to its previous state.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_UNDO_SUCCESS = "Successfully undone the last action!";
    public static final String MESSAGE_UNDO_FAILURE = "There are no actions to undo.";

    /**
     * Executes the undo command to revert the last action.
     *
     * @param model The model of the application, which contains the address book.
     * @return The result of executing the undo command.
     * @throws CommandException If there are no actions to undo.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoAddressBook()) {
            throw new CommandException(MESSAGE_UNDO_FAILURE);
        }

        undoLastAction(model);
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }

    /**
     * Undoes the last action in the address book model.
     *
     * @param model The model of the application.
     */
    private void undoLastAction(Model model) {
        model.undoAddressBook(); // Calls the model to undo the action
    }
}
