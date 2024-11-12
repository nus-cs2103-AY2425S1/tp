package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command that redoes the last undone action in the address book.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reapplies the last undone change to the address book.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_REDO_SUCCESS = "Successfully redone the last undone action!";
    public static final String MESSAGE_REDO_FAILURE = "There are no actions to redo.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoAddressBook()) {
            throw new CommandException(MESSAGE_REDO_FAILURE);
        }

        redoLastAction(model);
        return new CommandResult(MESSAGE_REDO_SUCCESS);
    }

    /**
     * Redoes the last action in the address book model.
     *
     * @param model The model of the application.
     */
    private void redoLastAction(Model model) {
        model.redoAddressBook(); // Calls the model to redo the action
    }
}
