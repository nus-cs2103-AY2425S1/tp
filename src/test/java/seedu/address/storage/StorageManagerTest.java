package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalClients.getTypicalClientBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
import seedu.address.model.ClientBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyClientBook;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        JsonClientBookStorage clientBookStorage = new JsonClientBookStorage(getTempFilePath("cb"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage, clientBookStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    // ====================== UserPrefs Tests ======================

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class. More extensive testing of UserPref saving/reading is done in
         * {@link JsonUserPrefsStorageTest}.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    // ====================== AddressBook Tests ======================

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonAddressBookStorage} class. More extensive testing of AddressBook saving/reading is done in
         * {@link JsonAddressBookStorageTest}.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    // ====================== ClientBook Tests ======================

    @Test
    public void clientBookReadSave() throws Exception {
        /*
         * This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonClientBookStorage} class. More extensive testing of ClientBook saving/reading is done in
         * {@link JsonClientBookStorageTest}.
         */
        ClientBook original = getTypicalClientBook();
        storageManager.saveClientBook(original);
        ReadOnlyClientBook retrieved = storageManager.readClientBook().get();
        assertEquals(original, new ClientBook(retrieved));
    }

    @Test
    public void getClientBookFilePath() {
        assertNotNull(storageManager.getClientBookFilePath());
    }
}
