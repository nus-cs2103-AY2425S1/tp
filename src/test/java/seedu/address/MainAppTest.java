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

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
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
    public void init_invalidConfigPath_usesDefaultConfig() throws IOException {
        Path invalidConfigPath = temporaryFolder.resolve("invalidConfig.json");
        Files.writeString(invalidConfigPath, "{ invalid json }"); // Corrupt config file

        Config config = mainApp.initConfig(invalidConfigPath);
        assertNotNull(config, "Default config should be used if the config file is invalid.");
    }

    @Test
    public void stop_savesUserPrefsSuccessfully() throws IOException, DataLoadingException {
        Path userPrefsPath = temporaryFolder.resolve("userprefs.json");
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsPath);
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(temporaryFolder.resolve("addressbook.json"));

        // Initialize storage with valid user preferences
        Storage storage = new StorageManager(
                new JsonAddressBookStorage(userPrefs.getAddressBookFilePath()), userPrefsStorage);
        mainApp.storage = (StorageManager) storage;
        mainApp.model = new ModelManager(new AddressBook(), userPrefs, storage);

        // Stop the application and ensure user preferences are saved successfully
        assertDoesNotThrow(() -> mainApp.stop());
        Optional<UserPrefs> savedPrefs = userPrefsStorage.readUserPrefs();
        assertTrue(savedPrefs.isPresent(), "User preferences should be saved successfully.");
    }

}
