package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.Storage;

/**
 * Restores the data from a backup file.
 */
public class RestoreCommand extends Command {

    public static final String COMMAND_WORD = "restore";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Restores the ClinicBuddy from the most recent backup or from a specific file path.\n"
            + "Example: " + COMMAND_WORD + " [optional file path]";

    public static final String MESSAGE_RESTORE_SUCCESS = "ClinicBuddy restored successfully from %s";
    public static final String MESSAGE_RESTORE_FAILURE =
            "Failed to restore ClinicBuddy from backup. Format: restore backups/<backup file name>.";

    protected final Optional<Path> filePath;

    /**
     * Creates a RestoreCommand to restore from the most recent backup or a specific file path.
     */
    public RestoreCommand(Optional<Path> filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            ReadOnlyAddressBook backupData;
            Path backupPath;

            // Retrieve the storage from the model
            Storage storage = model.getStorage();

            if (filePath.isPresent()) {
                // Restore from a specific file path
                backupPath = filePath.get();
                backupData = storage.readAddressBook(backupPath)
                        .orElseThrow(() -> new DataLoadingException(new Exception("Backup file not found or invalid")));
            } else {
                // Restore from the second-most recent backup
                backupPath = storage.restoreBackup().orElseThrow(() ->
                        new CommandException("Backup file not found or invalid"));
                backupData = storage.readAddressBook(backupPath)
                        .orElseThrow(() -> new DataLoadingException(new Exception("Backup file not found or invalid")));
            }

            model.setAddressBook(backupData);
            return new CommandResult(String.format(MESSAGE_RESTORE_SUCCESS, backupPath.toString()));

        } catch (IOException | DataLoadingException e) {
            throw new CommandException(MESSAGE_RESTORE_FAILURE, e);
        }
    }

    public Optional<Path> getFilePath() {
        return filePath;
    }

}
