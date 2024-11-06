package seedu.address.logic.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command to restore data from a specified backup file based on the provided index.
 * This command interacts with the Model to retrieve and replace the current data with that from the specified backup.
 */
public class RestoreCommand extends Command {

    public static final String COMMAND_WORD = "restore";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " <index>: Restores a backup by index.\n"
            + "Example: " + COMMAND_WORD + " 3";
    public static final String MESSAGE_RESTORE_SUCCESS =
            "Data restored successfully from backup index %d.";
    public static final String MESSAGE_RESTORE_FAILURE =
            "Failed to restore from backup. Please ensure the index is correct.";

    private final int index;

    /**
     * Constructs a RestoreCommand to restore data from a backup file identified by the index.
     *
     * @param index The index of the backup to restore.
     */
    public RestoreCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the RestoreCommand by attempting to restore data from the specified backup.
     * If the operation is successful, a success message is returned. In case of failure,
     * a CommandException with an appropriate message is thrown.
     *
     * @param model The model in which the data is restored from a backup.
     * @return CommandResult indicating the success of the restore operation.
     * @throws CommandException If an error occurs during the restoration process.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);

        try {
            Path backupPath = model.restoreBackup(index);
            return new CommandResult(String.format(MESSAGE_RESTORE_SUCCESS, index));
        } catch (IOException | DataLoadingException e) {
            throw new CommandException(MESSAGE_RESTORE_FAILURE, e);
        }
    }

    /**
     * Checks if this RestoreCommand is equal to another object.
     * Returns true if both objects are of the same class and their indexes match.
     *
     * @param other The object to compare to.
     * @return true if the given object is equivalent to this command; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RestoreCommand
                && index == ((RestoreCommand) other).index);
    }

    /**
     * Computes the hash code for this RestoreCommand based on its index.
     *
     * @return The hash code for this command.
     */
    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
