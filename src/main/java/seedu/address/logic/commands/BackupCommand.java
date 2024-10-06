package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Backs up the current address book to a user-specified destination.
 */
public class BackupCommand extends Command {
    public static final String COMMAND_WORD = "backup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Backs up the current data to the specified file path.\n"
            + "Parameters: DESTINATION_PATH (must be a valid path)\n"
            + "Example: " + COMMAND_WORD + " /path/to/backup.json";

    public static final String MESSAGE_SUCCESS = "Backup successful at %s";
    public static final String MESSAGE_FAILURE = "Backup failed due to: %s";

    private static final Logger logger = LogsCenter.getLogger(BackupCommand.class);
    private final String destinationPath;

    /**
     * Constructs a {@code BackupCommand} with the given destination path.
     *
     * @param destinationPath The path where the backup will be stored. Must not be null.
     * @throws NullPointerException if the destination path is null.
     */
    public BackupCommand(String destinationPath) {
        requireNonNull(destinationPath, "Destination path must not be null.");
        this.destinationPath = destinationPath;
    }

    /**
     * Executes the backup command to save the current state of the address book.
     *
     * @param model The model containing the current state of the address book. Must not be null.
     * @return The result of the command execution.
     * @throws CommandException if the backup operation fails.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getStorage() == null) {
            logger.warning("Storage is not initialized, cannot proceed with backup.");
            throw new CommandException(String.format(MESSAGE_FAILURE, "Storage is not initialized!"));
        }

        try {
            logger.info("Starting backup to path: " + destinationPath);
            model.backupData(destinationPath);
            logger.info("Backup successful.");
            return new CommandResult(String.format(MESSAGE_SUCCESS, destinationPath));
        } catch (IOException e) {
            logger.severe("Backup failed: " + e.getMessage());
            throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()));
        }
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof BackupCommand)) {
            return false;
        }

        BackupCommand otherCommand = (BackupCommand) other;
        return Objects.equals(destinationPath, otherCommand.destinationPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destinationPath);
    }

    public String getDestinationPath() {
        return destinationPath;
    }
}
