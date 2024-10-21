package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Backs up the current address book to the default backup directory with a timestamped filename.
 */
public class BackupCommand extends Command {
    public static final String COMMAND_WORD = "backup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a backup of the current records in the default backup directory - /backups/.\n"
            + "Example: " + COMMAND_WORD + " <without arguments>";

    public static final String MESSAGE_SUCCESS = "Backup successful at %s";
    public static final String MESSAGE_FAILURE = "Backup failed due to: %s";
    protected static long lastManualBackupTime = 0; // Track last manual backup time
    private static final Logger logger = LogsCenter.getLogger(BackupCommand.class);
    private static final long MANUAL_BACKUP_INTERVAL_MS = 5000; // 5 seconds debounce for manual backups

    /**
     * Constructs a {@code BackupCommand}.
     */
    public BackupCommand() {
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

        long currentTime = System.currentTimeMillis();

        // Ensure only one manual backup is allowed within the debounce window
        if (currentTime - lastManualBackupTime < MANUAL_BACKUP_INTERVAL_MS) {
            return new CommandResult("Manual backup already triggered. Please wait a few seconds.");
        }

        lastManualBackupTime = currentTime; // Update the last backup time

        try {
            // Format the current date and time for the filename
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS"));
            String backupPath = String.format("backups/clinicbuddy-backup-%s.json", timestamp);

            logger.info("Starting backup to path: " + backupPath);
            model.backupData(backupPath); // Use String path here

            logger.info("Backup successful.");
            return new CommandResult(String.format(MESSAGE_SUCCESS, backupPath));
        } catch (IOException e) {
            logger.severe("Backup failed: " + e.getMessage());
            throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof BackupCommand);
    }

    @Override
    public int hashCode() {
        return COMMAND_WORD.hashCode();
    }
}
