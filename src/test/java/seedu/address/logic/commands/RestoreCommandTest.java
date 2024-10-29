package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

/**
 * Contains integration tests (interaction with the Model) for {@code RestoreCommand}.
 */
public class RestoreCommandTest {

    @TempDir
    public Path temporaryFolder;

    @Test
    public void execute_restoreSuccessful() throws Exception {
        // Set up the storage and model with temporary directory
        Path addressBookFilePath = temporaryFolder.resolve("addressBook.json");
        Path userPrefsFilePath = temporaryFolder.resolve("userPrefs.json");
        Path backupDirectoryPath = temporaryFolder.resolve("backups");
        Files.createDirectories(backupDirectoryPath);

        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressBookFilePath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsFilePath);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(addressBookFilePath);

        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

        Model model = new ModelManager(new AddressBook(), userPrefs, storage);

        // Save the address book to ensure the file exists
        storage.saveAddressBook(model.getAddressBook());

        // Simulate creating a backup at index 0
        int backupIndex = 0;
        String actionDescription = "testBackup";
        model.backupData(actionDescription);

        // Create RestoreCommand with the backup index
        RestoreCommand restoreCommand = new RestoreCommand(backupIndex);

        // Execute the command
        CommandResult result = restoreCommand.execute(model);

        // Check that the command result is as expected
        String expectedMessage = String.format(RestoreCommand.MESSAGE_RESTORE_SUCCESS, backupIndex);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void equals() {
        RestoreCommand restoreCommand1 = new RestoreCommand(1);
        RestoreCommand restoreCommand2 = new RestoreCommand(2);

        // Same object
        assertEquals(restoreCommand1, restoreCommand1);

        // Different objects, same index
        RestoreCommand restoreCommand1Copy = new RestoreCommand(1);
        assertEquals(restoreCommand1, restoreCommand1Copy);

        // Different index
        assertEquals(false, restoreCommand1.equals(restoreCommand2));

        // Different types
        assertEquals(false, restoreCommand1.equals(null));
        assertEquals(false, restoreCommand1.equals("some string"));
    }
}
