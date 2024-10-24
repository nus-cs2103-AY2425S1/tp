package careconnect.logic.commands;

import careconnect.logic.commands.exceptions.CommandException;
import careconnect.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    public static final String CONFIRMATION_MESSAGE =
            "This action is irreversible. Press y to continue, or n to cancel.";
    protected static final CommandStack STACK = new CommandStack();

    protected final boolean requireConfirmation;

    protected Command() {
        this.requireConfirmation = false;
    }

    protected Command(boolean requireConfirmation) {
        this.requireConfirmation = requireConfirmation;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

}
