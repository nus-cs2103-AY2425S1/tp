package seedu.address.logic.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Restores the data from a backup file by index.
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
     * Creates a RestoreCommand to restore from a backup by index.
     *
     * @param index The index of the backup to restore.
     */
    public RestoreCommand(int index) {
        this.index = index;
    }

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

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RestoreCommand
                && index == ((RestoreCommand) other).index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
