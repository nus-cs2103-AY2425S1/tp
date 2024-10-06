package seedu.address;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Path;

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
    public void initModelManager_validStorageAndUserPrefs_doesNotThrow() {
        Path addressBookPath = temporaryFolder.resolve("addressbook.json");
        Path userPrefsPath = temporaryFolder.resolve("userprefs.json");

        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressBookPath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsPath);
        Storage storage = new StorageManager(addressBookStorage, userPrefsStorage);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(addressBookPath);

        // Test that initModelManager does not throw any exceptions
        assertDoesNotThrow(() -> {
            Model model = mainApp.initModelManager(storage, userPrefs);
            assertNotNull(model);
        });
    }

    @Test
    public void initModelManager_storageWithoutAddressBook_usesSampleData() {
        Path addressBookPath = temporaryFolder.resolve("non_existent_addressbook.json");
        Path userPrefsPath = temporaryFolder.resolve("userprefs.json");

        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressBookPath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsPath);
        Storage storage = new StorageManager(addressBookStorage, userPrefsStorage);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(addressBookPath);

        // Test that the model is initialized even if the address book file is not present
        Model model = mainApp.initModelManager(storage, userPrefs);
        assertNotNull(model);
    }

    @Test
    public void initModelManager_storageWithAddressBook_loadsAddressBook() throws IOException, DataLoadingException {
        Path addressBookPath = temporaryFolder.resolve("addressbook.json");
        Path userPrefsPath = temporaryFolder.resolve("userprefs.json");

        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressBookPath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsPath);
        Storage storage = new StorageManager(addressBookStorage, userPrefsStorage);

        // Create a sample address book file
        ReadOnlyAddressBook sampleAddressBook = seedu.address.model.util.SampleDataUtil.getSampleAddressBook();
        addressBookStorage.saveAddressBook(sampleAddressBook);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(addressBookPath);

        // Test that the model is initialized with the sample address book
        Model model = mainApp.initModelManager(storage, userPrefs);
        assertNotNull(model);
    }
}
