package seedu.address;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

public class MainAppTest {

    @TempDir
    public Path temporaryFolder;
    private MainApp mainApp;

    @BeforeEach
    public void setUp() {
        mainApp = new MainApp();
    }

    @Test
    public void initModelManager_validStorageAndUserPrefs_doesNotThrow() throws IOException {
        Path addressBookPath = temporaryFolder.resolve("addressbook.json");
        Path userPrefsPath = temporaryFolder.resolve("userprefs.json");

        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressBookPath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsPath);
        Storage storage = new StorageManager(addressBookStorage, userPrefsStorage);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(addressBookPath);

        // Test that initModelManager does not throw exceptions and returns a valid model.
        assertDoesNotThrow(() -> {
            Model model = mainApp.initModelManager(storage, userPrefs);
            assertNotNull(model, "Model should be initialized successfully.");
        });
    }

    @Test
    public void initModelManager_storageWithoutAddressBook_usesSampleData() throws IOException {
        Path nonExistentAddressBookPath = temporaryFolder.resolve("non_existent_addressbook.json");
        Path userPrefsPath = temporaryFolder.resolve("userprefs.json");

        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(nonExistentAddressBookPath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsPath);
        Storage storage = new StorageManager(addressBookStorage, userPrefsStorage);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(nonExistentAddressBookPath);

        // Test that the model initializes with sample data when the address book file is not present.
        Model model = mainApp.initModelManager(storage, userPrefs);
        assertNotNull(model, "Model should be initialized with sample data.");
        assertTrue(model.getAddressBook().getPersonList().size() > 0, "Sample data should be loaded.");
    }

    @Test
    public void initModelManager_storageWithAddressBook_loadsAddressBook() throws IOException, DataLoadingException {
        Path addressBookPath = temporaryFolder.resolve("addressbook.json");
        Path userPrefsPath = temporaryFolder.resolve("userprefs.json");

        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressBookPath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsPath);
        Storage storage = new StorageManager(addressBookStorage, userPrefsStorage);

        // Create and save a sample address book file.
        ReadOnlyAddressBook sampleAddressBook = seedu.address.model.util.SampleDataUtil.getSampleAddressBook();
        addressBookStorage.saveAddressBook(sampleAddressBook);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(addressBookPath);

        // Test that the model is initialized with the saved address book.
        Model model = mainApp.initModelManager(storage, userPrefs);
        assertNotNull(model, "Model should be initialized with the address book data.");
        assertEquals(sampleAddressBook, model.getAddressBook(), "Address book data should match.");
    }

    @Test
    public void initModelManager_invalidStorage_initializesWithEmptyAddressBook() throws IOException {
        Path invalidAddressBookPath = temporaryFolder.resolve("invalid_addressbook.json");
        Files.writeString(invalidAddressBookPath, "{ invalid json }"); // Corrupt the address book file.

        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(invalidAddressBookPath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userprefs.json"));
        Storage storage = new StorageManager(addressBookStorage, userPrefsStorage);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(invalidAddressBookPath);

        // Test that the model initializes with an empty address book if the file is invalid.
        Model model = mainApp.initModelManager(storage, userPrefs);
        assertNotNull(model, "Model should be initialized successfully.");
        assertTrue(model.getAddressBook().getPersonList().isEmpty(), "Address book should be empty.");
    }

    @Test
    public void restoreBackup_withValidBackup_successfullyRestores() throws IOException, DataLoadingException {
        Path addressBookPath = temporaryFolder.resolve("addressbook.json");
        Path userPrefsPath = temporaryFolder.resolve("userprefs.json");
        Path backupPath = temporaryFolder.resolve("backups/addressbook-backup.json");

        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressBookPath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsPath);
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

        // Create backup directory and ensure it exists
        Files.createDirectories(backupPath.getParent());

        // Create and save a sample address book
        ReadOnlyAddressBook sampleAddressBook = seedu.address.model.util.SampleDataUtil.getSampleAddressBook();
        addressBookStorage.saveAddressBook(sampleAddressBook);
        storage.saveAddressBook(sampleAddressBook, backupPath); // Save backup

        // Test that restoreBackup restores the valid backup
        Optional<Path> restoredBackup = storage.restoreBackup();
        assertTrue(restoredBackup.isPresent(), "A backup should be restored.");

        ReadOnlyAddressBook restoredAddressBook = addressBookStorage.readAddressBook(restoredBackup.get()).get();
        assertEquals(sampleAddressBook, restoredAddressBook, "Restored address book should match the original.");
    }
}
