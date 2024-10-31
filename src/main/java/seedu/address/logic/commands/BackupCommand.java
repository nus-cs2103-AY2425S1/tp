package seedu.address.logic.commands;

import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * BackupCommand is responsible for creating a manual backup of the current data in the application.
 * It allows the user to specify an optional action description to better categorize or identify the backup.
 * When executed, it interacts with the Model to save the backup and returns a success message indicating
 * the backup's index.
 */
public class BackupCommand extends Command {
    public static final String COMMAND_WORD = "backup";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a backup with an optional action description.\n"
            + "Example: " + COMMAND_WORD + " myBackup";
    public static final String MESSAGE_SUCCESS = "Backup %d is created successfully.";

    private final String actionDescription;

    /**
     * Constructs a BackupCommand with the specified action description.
     *
     * @param actionDescription An optional description of the backup action for identification.
     */
    public BackupCommand(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    /**
     * Executes the command to create a backup, interacting with the Model to save the data.
     * A success message is returned with the backup's index.
     *
     * @param model The model in which the backup operation is executed.
     * @return CommandResult indicating the success of the backup operation.
     * @throws CommandException If an error occurs during the backup process.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        int backupIndex = model.backupData(actionDescription); // Get the index used
        String message = String.format(MESSAGE_SUCCESS, backupIndex);
        return new CommandResult(message);
    }

    /**
     * Checks if this BackupCommand is equal to another object.
     * Returns true if both objects are of the same class and their action descriptions match.
     *
     * @param other The object to compare to.
     * @return true if the given object is equivalent to this command; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof BackupCommand)) {
            return false;
        } else {
            BackupCommand otherCommand = (BackupCommand) other;
            return Objects.equals(this.actionDescription, otherCommand.actionDescription);
        }
    }

    /**
     * Computes the hash code for this BackupCommand based on its action description.
     *
     * @return The hash code for this command.
     */
    @Override
    public int hashCode() {
        return Objects.hash(actionDescription);
    }
}
