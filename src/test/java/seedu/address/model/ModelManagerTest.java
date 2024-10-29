package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.storage.BackupManager;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains unit tests for {@code ModelManager}.
 */
public class ModelManagerTest {

    @TempDir
    public Path temporaryFolder;
    private ModelManager modelManager;
    private StorageManager storage;
    private UserPrefs userPrefs;

    /**
     * Sets up the test environment with the required storage.
     */
    @BeforeEach
    public void setUp() throws IOException {
        Path addressBookPath = temporaryFolder.resolve("addressBook.json");
        Path userPrefsPath = temporaryFolder.resolve("userPrefs.json");

        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressBookPath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsPath);

        storage = new StorageManager(addressBookStorage, userPrefsStorage); // Initialize storage
        userPrefs = new UserPrefs(); // Initialize userPrefs

        // Replace the BackupManager with one that uses the temp backup directory
        Path backupDirectoryPath = temporaryFolder.resolve("backups");
        Files.createDirectories(backupDirectoryPath);
        storage.setBackupManager(new BackupManager(backupDirectoryPath));

        modelManager = new ModelManager(new AddressBook(), userPrefs, storage);
    }

    /**
     * Tests whether the constructor initializes the model correctly.
     */
    @Test
    public void constructor() throws IOException {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertNotNull(modelManager.getCalendar());
    }

    @Test
    public void backupData_nullActionDescription_backupSuccessful() throws CommandException, IOException {
        // Save the address book to ensure the file exists
        storage.saveAddressBook(modelManager.getAddressBook());

        // Perform backup with null action description
        int backupIndex = modelManager.backupData(null);

        // Verify backup index
        assertTrue(backupIndex >= 0 && backupIndex < 10, "Backup index should be between 0 and 9.");
    }

    @Test
    public void backupData_storageNotInitialized_throwsCommandException() throws IOException {
        // Initialize ModelManager without storage
        ModelManager modelWithoutStorage = new ModelManager(new AddressBook(), new UserPrefs(), null);

        // Expect CommandException when storage is not initialized
        CommandException exception = assertThrows(CommandException.class, () -> modelWithoutStorage.backupData("test"));
        assertEquals("Failed to create backup: Storage is not initialized!", exception.getMessage());
    }

    @Test
    public void restoreBackup_validIndex_restorationSuccessful() throws Exception {
        // Save the address book to ensure the file exists
        storage.saveAddressBook(modelManager.getAddressBook());

        // Perform backup to create a backup at index 0
        String actionDescription = "testRestore";
        int backupIndex = modelManager.backupData(actionDescription);

        // Modify the address book to ensure restoration changes it
        Person person = new PersonBuilder().withName("Test Person").build();
        modelManager.addPerson(person);
        storage.saveAddressBook(modelManager.getAddressBook());

        // Restore from backup
        Path restoredPath = modelManager.restoreBackup(backupIndex);

        // Verify that the restored data matches the original (before modification)
        AddressBook restoredAddressBook = new AddressBook(storage.readAddressBook(restoredPath).get());
        assertEquals(restoredAddressBook, modelManager.getAddressBook(), "Restored data should match backup data.");
    }

    @Test
    public void restoreBackup_storageNotInitialized_throwsIoException() throws IOException {
        // Initialize ModelManager without storage
        ModelManager modelWithoutStorage = new ModelManager(new AddressBook(), new UserPrefs(), null);

        // Expect IOException when storage is not initialized
        IOException exception = assertThrows(IOException.class, () -> modelWithoutStorage.restoreBackup(0));
        assertEquals("Storage is not initialized!", exception.getMessage());
    }
}
