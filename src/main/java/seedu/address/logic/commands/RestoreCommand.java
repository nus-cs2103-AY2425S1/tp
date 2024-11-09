package seedu.address.logic.commands;

import java.io.IOException;
import java.util.Objects;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Restores data from a specified backup file based on the provided index.
 * Includes a confirmation mechanism to prevent accidental data loss.
 */
public class RestoreCommand extends Command {

    public static final String COMMAND_WORD = "restore";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " <index>: Restores a backup by index.\n"
            + "Example: " + COMMAND_WORD + " 3";

    public static final String MESSAGE_CONFIRMATION =
            "Restoring a backup will overwrite the current patient records."
                    + " Do you want to continue? (Y to restore, any other key to cancel)";

    public static final String MESSAGE_CANCELLED =
            "Restore operation cancelled. Consider creating a backup of your current records first.";

    public static final String MESSAGE_BACKUP_REMINDER =
            "It is recommended to create a backup of the current records before proceeding to prevent data loss.";

    public static final String MESSAGE_RESTORE_SUCCESS =
            "Data restored successfully from backup index %d.";

    public static final String MESSAGE_RESTORE_FAILURE =
            "Failed to restore from backup. Please ensure the index is correct.";

    public static final String MESSAGE_BACKUP_NOT_AVAILABLE =
            "Backup file at index %d is not available.";

    private final int index;
    private final boolean isConfirmed;

    /**
     * Constructs a RestoreCommand to restore data from a backup file identified by the index.
     *
     * @param index       The index of the backup to restore.
     * @param isConfirmed Whether the user has confirmed the restoration.
     */
    public RestoreCommand(int index, boolean isConfirmed) {
        this.index = index;
        this.isConfirmed = isConfirmed;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);

        // Check if the backup file is available
        boolean isBackupAvailable = model.isBackupAvailable(index);
        if (!isBackupAvailable) {
            // Backup is not available; return a message indicating this
            return new CommandResult(String.format(MESSAGE_BACKUP_NOT_AVAILABLE, index));
        }

        if (!isConfirmed) {
            // Return a CommandResult indicating confirmation is required
            return new CommandResult(MESSAGE_CONFIRMATION + "\n" + MESSAGE_BACKUP_REMINDER,
                    false, false, true);
        }

        // Proceed with restoration if user confirms
        try {
            model.restoreBackup(index);
            return new CommandResult(String.format(MESSAGE_RESTORE_SUCCESS, index));
        } catch (IOException | DataLoadingException e) {
            throw new CommandException(MESSAGE_RESTORE_FAILURE, e);
        }
    }

    /**
     * Gets the index of the backup to restore.
     *
     * @return The backup index.
     */
    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RestoreCommand
                && index == ((RestoreCommand) other).index
                && isConfirmed == ((RestoreCommand) other).isConfirmed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, isConfirmed);
    }
}
