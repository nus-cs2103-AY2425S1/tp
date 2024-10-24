package seedu.address.logic.commands;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    /**
     * Indicates whether the command is executed.
     * Should be set to true after the command is executed.
     */
    protected boolean isExecuted = false;

    /**
     * Executes the command and returns the result message.
     * A command should only be executed once.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Returns the status of the command execution.
     *
     * @return true if the command is executed
     */
    public boolean isExecuted() {
        return isExecuted;
    }
}
