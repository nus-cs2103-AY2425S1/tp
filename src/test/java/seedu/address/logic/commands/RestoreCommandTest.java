package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.BackupManager;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

/**
 * Contains integration tests (interaction with the Model) for {@code RestoreCommand}.
 */
public class RestoreCommandTest {

    @TempDir
    public Path temporaryFolder;

    @AfterEach
    public void cleanUpDefaultBackupDirectory() throws IOException {
        Path defaultBackupDirectory = Paths.get("backups");
        if (Files.exists(defaultBackupDirectory)) {
            Files.walk(defaultBackupDirectory)
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            System.err.println("Failed to delete file: " + path + " - " + e.getMessage());
                        }
                    });
        }
    }

    @Test
    public void execute_backupNotAvailable_displaysMessage() throws Exception {
        // Setup
        Model model = createModel();
        int backupIndex = 0;

        // Create RestoreCommand with backup index that doesn't exist
        RestoreCommand restoreCommand = new RestoreCommand(backupIndex, false);

        // Execute the command
        CommandResult result = restoreCommand.execute(model);

        // Verify that the correct message is displayed
        String expectedMessage = String.format(RestoreCommand.MESSAGE_BACKUP_NOT_AVAILABLE, backupIndex);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void equals() {
        RestoreCommand restoreCommand1 = new RestoreCommand(1, false);
        RestoreCommand restoreCommand2 = new RestoreCommand(1, true);
        RestoreCommand restoreCommand3 = new RestoreCommand(2, false);

        // Same object
        assertEquals(restoreCommand1, restoreCommand1);

        // Different objects, same index and confirmation
        RestoreCommand restoreCommand1Copy = new RestoreCommand(1, false);
        assertEquals(restoreCommand1, restoreCommand1Copy);

        // Different confirmation
        assertFalse(restoreCommand1.equals(restoreCommand2));

        // Different index
        assertFalse(restoreCommand1.equals(restoreCommand3));

        // Different types
        assertFalse(restoreCommand1.equals(null));
        assertFalse(restoreCommand1.equals("some string"));
    }

    // Helper method to create a Model with temporary storage
    private Model createModel() throws IOException {
        Path addressBookFilePath = temporaryFolder.resolve("addressBook.json");
        Path userPrefsFilePath = temporaryFolder.resolve("userPrefs.json");
        Path backupDirectoryPath = temporaryFolder.resolve("backups");
        Files.createDirectories(backupDirectoryPath);

        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressBookFilePath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsFilePath);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(addressBookFilePath);

        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        storage.setBackupManager(new BackupManager(backupDirectoryPath));

        return new ModelManager(new AddressBook(), userPrefs, storage);
    }
}
