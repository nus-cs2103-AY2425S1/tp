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
            + ": Restores the AddressBook from the most recent backup or from a specific file path.\n"
            + "Example: " + COMMAND_WORD + " [optional file path]";

    public static final String MESSAGE_RESTORE_SUCCESS = "AddressBook restored successfully from %s";
    public static final String MESSAGE_RESTORE_FAILURE = "Failed to restore AddressBook from backup.";

    private final Optional<Path> filePath;

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

            // Retrieve the storage from the model
            Storage storage = model.getStorage();

            if (filePath.isPresent()) {
                // Restore from a specific file path
                backupData = storage.readAddressBook(filePath.get())
                        .orElseThrow(() -> new DataLoadingException(new Exception("Backup file not found or invalid")));
            } else {
                // Restore from the most recent backup
                Path recentBackupPath = storage.restoreBackup().orElseThrow(() ->
                        new CommandException("No recent backup available."));
                backupData = storage.readAddressBook(recentBackupPath)
                        .orElseThrow(() -> new DataLoadingException(new Exception("Backup file not found or invalid")));
            }

            model.setAddressBook(backupData);
            return new CommandResult(String.format(MESSAGE_RESTORE_SUCCESS, filePath.orElse(null)));

        } catch (IOException | DataLoadingException e) {
            throw new CommandException(MESSAGE_RESTORE_FAILURE, e);
        }
    }
}
