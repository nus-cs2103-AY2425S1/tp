package seedu.address.logic.commands;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    public static final String MESSAGE_NOT_EXECUTED_ERROR = "This command has not been executed";
    public static final String MESSAGE_EXECUTED_ERROR = "This command has already been executed";
    /**
     * Indicates whether the command is executed.
     * Should be set to true after the command is executed.
     */
    protected boolean isExecuted = false;

    protected void requireNotExecuted() {
        if (isExecuted) {
            throw new AssertionError(MESSAGE_EXECUTED_ERROR);
        }
    }

    protected void requireExecuted() {
        if (!isExecuted) {
            throw new AssertionError(MESSAGE_NOT_EXECUTED_ERROR);
        }
    }

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
