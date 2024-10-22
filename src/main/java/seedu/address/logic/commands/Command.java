package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    /**
     * Executes the command and optionally commits the changes to the address book.
     *
     * @param model {@code Model} which the command should operate on.
     * @return The result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     */
    public final CommandResult execute(Model model) throws CommandException {
        CommandResult result = executeCommand(model);

        if (requiresCommit()) {
            model.commitAddressBook();
        }

        return result;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    protected abstract CommandResult executeCommand(Model model) throws CommandException;

    protected boolean requiresCommit() {
        return true;
    }
}
