package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Updates the current versionHistory with a new addition.
     * @param versionHistory
     * @param model
     */
    public abstract VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model)
            throws CommandException;

    public void updateTaskStatus(Model model) {
        model.setStatus();
    }

}
