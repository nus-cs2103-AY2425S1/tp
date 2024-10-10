package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.AddressBookBuilder;

/**
 * Contains unit tests for {@code ModelManager}.
 */
public class ModelManagerTest {

    @TempDir
    public Path temporaryFolder;
    private ModelManager modelManager;

    /**
     * Sets up the test environment with the required storage.
     */
    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

        modelManager = new ModelManager(new AddressBook(), new UserPrefs(), storage);
    }

    /**
     * Tests whether the constructor initializes the model correctly.
     */
    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManagerCopy =
                new ModelManager(addressBook, userPrefs, modelManager.getStorage());
        ModelManager modelManagerWithSameValues =
                new ModelManager(addressBook, userPrefs, modelManager.getStorage());
        assertEquals(modelManagerCopy, modelManagerWithSameValues);

        // same object -> returns true
        assertEquals(modelManagerCopy, modelManagerCopy);

        // null -> returns false
        assertNotEquals(null, modelManagerCopy);

        // different types -> returns false
        assertNotEquals(5, modelManagerCopy);

        // different addressBook -> returns false
        assertNotEquals(modelManagerCopy,
                new ModelManager(differentAddressBook, userPrefs, modelManager.getStorage()));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManagerCopy.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        ModelManager modelWithFilteredPersons =
                new ModelManager(addressBook, userPrefs, modelManager.getStorage());
        assertNotEquals(modelManagerCopy, modelWithFilteredPersons);

        // resets modelManager to initial state for upcoming tests
        modelManagerCopy.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertNotEquals(modelManagerCopy,
                new ModelManager(addressBook, differentUserPrefs, modelManager.getStorage()));
    }

    @Test
    public void backupData_validPath_success() throws Exception {
        Path backupPath = temporaryFolder.resolve("backup.json");
        modelManager.backupData(backupPath.toString());

        // Assert that the file was created successfully.
        assertTrue(backupPath.toFile().exists(), "Backup file should have been created successfully.");
    }

    @Test
    public void modelManager_initialization_validStorage() {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(Paths.get("data/addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(Paths.get("data/userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

        ModelManager modelManager = new ModelManager(new AddressBook(), new UserPrefs(), storage);
        assertNotNull(modelManager.getStorage(), "Storage should not be null.");
        assertEquals(storage, modelManager.getStorage(), "Storage should be initialized properly.");
    }

    @Test
    public void backupData_withValidStorage_noException() {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(Paths.get("data/addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(Paths.get("data/userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

        ModelManager modelManager = new ModelManager(new AddressBook(), new UserPrefs(), storage);
        String backupPath = "data/backup.json";

        try {
            modelManager.backupData(backupPath);
        } catch (IOException e) {
            fail("Backup should not throw IOException with valid storage: " + e.getMessage());
        }
    }

    @Test
    public void getStorage_returnsValidStorage() {
        assertNotNull(modelManager.getStorage(), "Storage should not be null.");
        assertEquals(modelManager.getStorage().getClass(),
                StorageManager.class, "Storage should be an instance of StorageManager.");
    }

    /**
     * Tests the constructor that initializes ModelManager with null storage.
     */
    @Test
    public void constructor_nullStorage_success() {
        ModelManager modelManagerWithoutStorage = new ModelManager(new AddressBook(), new UserPrefs(), null);
        assertNotNull(modelManagerWithoutStorage,
                "ModelManager should be created successfully even with null storage.");
    }

    /**
     * Tests the backupData method when storage is null.
     */
    @Test
    public void backupData_nullStorage_throwsIoException() {
        ModelManager modelManagerWithoutStorage = new ModelManager(new AddressBook(), new UserPrefs(), null);
        String backupPath = temporaryFolder.resolve("backup.json").toString();

        assertThrows(IOException.class, () -> modelManagerWithoutStorage.backupData(backupPath),
                "Expected IOException when trying to back up with null storage.");
    }

}
