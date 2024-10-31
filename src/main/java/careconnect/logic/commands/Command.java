package careconnect.logic.commands;

import careconnect.logic.Messages;
import careconnect.logic.commands.exceptions.CommandException;
import careconnect.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    public static final String CONFIRMATION_MESSAGE =
            "The %s command is irreversible. Press y to continue, or n to cancel.";

    protected static final CommandStack STACK = new CommandStack();

    protected static Command commandToConfirm = null;

    protected final boolean requireConfirmation;

    protected Command() {
        this.requireConfirmation = false;
    }

    protected Command(boolean requireConfirmation) {
        this.requireConfirmation = requireConfirmation;
    }

    /**
     * Validates that there exists a command waiting to be confirmed
     *
     * @throws CommandException if no such command is found
     */
    public static void requireConfirmableCommand() throws CommandException {
        if (Command.commandToConfirm == null) {
            throw new CommandException(Messages.MESSAGE_NO_EXECUTABLE_COMMAND);
        }
    }

    /**
     * Validates that there is no unconfirmed command
     *
     * @throws CommandException if an unconfirmed command exist
     */
    public static void requireNoUnconfirmedCommand() throws CommandException {
        if (Command.commandToConfirm != null) {
            throw new CommandException(Messages.MESSAGE_UNCOMFIRMED_COMMAND);
        }
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
