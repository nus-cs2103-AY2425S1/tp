package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains unit tests for {@code ModelManager}.
 */
public class ModelManagerTest {

    private static final Path BACKUP_DIR = Paths.get("backups");
    @TempDir
    public Path temporaryFolder;
    private ModelManager modelManager;
    private StorageManager storage;
    private UserPrefs userPrefs;
    private Calendar calendar;

    /**
     * Sets up the test environment with the required storage.
     */
    @BeforeEach
    public void setUp() throws IOException {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));

        storage = new StorageManager(addressBookStorage, userPrefsStorage); // Initialize storage
        userPrefs = new UserPrefs(); // Initialize userPrefs
        calendar = new Calendar(new AddressBook()); // initialize calendar

        modelManager = new ModelManager(new AddressBook(), new UserPrefs(), storage);
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up any backup files created during the test
        try (Stream<Path> paths = Files.walk(BACKUP_DIR)) {
            paths.filter(Files::isRegularFile)
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            System.err.println("Failed to delete file: " + path);
                            e.printStackTrace();
                        }
                    });
        }
    }

    /**
     * Tests whether the constructor initializes the model correctly.
     */
    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(new Calendar(new AddressBook()), modelManager.getCalendar());
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
    public void hasAppointment_appointmentInCalendar_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasAppointment(new PersonBuilder(BOB).withAppointment(ALICE.getAppointment().dateTime)
                .build()));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(modelManager.equals(modelManager), "Comparing the same object should return true.");
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        assertFalse(modelManager.equals(null), "Comparing with null should return false.");
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        assertFalse(modelManager.equals("not a ModelManager"),
                "Comparing with an object of different class should return false.");
    }

    @Test
    public void equals_differentAddressBook_returnsFalse() throws IOException {
        ModelManager differentModel = new ModelManager(new AddressBook(), userPrefs, storage);
        differentModel.addPerson(ALICE); // Modify to ensure difference
        assertFalse(modelManager.equals(differentModel),
                "Comparing with a different address book should return false.");
    }

    @Test
    public void equals_identicalModelManager_returnsTrue() throws IOException {
        ModelManager identicalModel = new ModelManager(new AddressBook(), userPrefs, storage);
        assertTrue(modelManager.equals(identicalModel), "Comparing with an identical ModelManager should return true.");
    }

    /*@Test
    public void backupData_defaultPath_success() throws Exception {
        // Set up the expected backup directory
        Path backupDir = Path.of("backups");
        Files.createDirectories(backupDir); // Ensure the directory exists

        // Run the backup operation
        assertDoesNotThrow(() -> modelManager.backupData(null)); // Should not throw an exception

        // Wait briefly to allow the backup process to complete
        Thread.sleep(500); // Ensure the backup file is written before checking

        // Verify that the backup directory contains at least one file
        List<Path> backupFiles = Files.list(backupDir).toList();
        assertFalse(backupFiles.isEmpty(), "No backup files found, but one was expected.");

        // Verify the backup file follows the correct format
        String expectedPrefix = "clinicbuddy-backup-"; // Update the prefix to match what your BackupManager is using
        boolean validBackupExists = backupFiles.stream()
                .anyMatch(file -> {
                    String filename = file.getFileName().toString();
                    return filename.startsWith(expectedPrefix) && filename.endsWith(".json");
                });

        assertTrue(validBackupExists, "Expected a backup file with the correct timestamp format.");
    }*/

    @Test
    public void modelManager_initialization_validStorage() throws IOException {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(Paths.get("data/addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(Paths.get("data/userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

        ModelManager modelManager = new ModelManager(new AddressBook(), new UserPrefs(), storage);
        assertNotNull(modelManager.getStorage(), "Storage should not be null.");
        assertEquals(storage, modelManager.getStorage(), "Storage should be initialized properly.");
    }

    /*@Test
    public void backupData_withValidStorage_noException() throws IOException {
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
    }*/

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
    public void constructor_nullStorage_success() throws IOException {
        ModelManager modelManagerWithoutStorage = new ModelManager(new AddressBook(), new UserPrefs(), null);
        assertNotNull(modelManagerWithoutStorage,
                "ModelManager should be created successfully even with null storage.");
    }


    /*@Test
    public void backupData_nullStorage_throwsIoException() throws IOException {
        // Create a ModelManager without a storage instance
        ModelManager modelManagerWithoutStorage = new ModelManager(new AddressBook(), new UserPrefs(), null);

        // Verify that calling backupData throws an IOException when the storage is not initialized
        assertThrows(IOException.class, (
                ) -> modelManagerWithoutStorage.backupData(null),
                "Expected IOException when trying to back up with null storage.");
    }

    @Test
    public void backupData_withValidStorageAndFilePath_success() throws IOException {
        // Ensure modelManager is initialized with valid storage
        modelManager.backupData("backups/clinicbuddy-backup-somepath.json");

        // Ensure backup exists in the backup folder
        Path backupDir = Path.of("backups");

        try (Stream<Path> backups = Files.list(backupDir)) {
            boolean backupExists = backups.anyMatch(path ->
                    path.getFileName().toString().startsWith("clinicbuddy-backup-")
                            && path.toString().endsWith(".json")
            );
            assertTrue(backupExists, "Backup file should be created successfully.");
        }
    }*/

    @Test
    public void cleanOldBackups_nullStorage_throwsIoException() throws IOException {
        ModelManager modelWithoutStorage = new ModelManager(new AddressBook(), new UserPrefs(), null);

        assertThrows(IOException.class, () -> modelWithoutStorage.cleanOldBackups(5),
                "Expected IOException when storage is not initialized.");
    }

    @Test
    public void cleanOldBackups_storageNotInitialized_throwsIoException() throws IOException {
        ModelManager modelWithoutStorage = new ModelManager(new AddressBook(), new UserPrefs(), null);
        IOException exception = assertThrows(IOException.class, (
                ) -> modelWithoutStorage.cleanOldBackups(5),
                "Expected IOException when storage is not initialized.");
        assertEquals("Storage is not initialized!", exception.getMessage());
    }

    @Test
    public void cleanOldBackups_validStorage_executesSuccessfully() throws IOException {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

        ModelManager modelManagerWithStorage = new ModelManager(new AddressBook(), new UserPrefs(), storage);
        assertDoesNotThrow(() -> modelManagerWithStorage.cleanOldBackups(5),
                "Cleaning old backups with valid storage should not throw any exception.");
    }

    @Test
    public void triggerBackup_backupSucceeds_createsBackupFile() throws Exception {
        // Set up the storage with a valid address book file path
        Path addressBookFilePath = temporaryFolder.resolve("addressBook.json");
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressBookFilePath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(addressBookFilePath);
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

        // Initialize ModelManager with storage and userPrefs
        ModelManager modelManager = new ModelManager(new AddressBook(), userPrefs, storage);

        // Add a person to the address book
        Person person = new PersonBuilder().withName("Test Person").build();
        modelManager.addPerson(person);

        // **Save the address book to ensure the file exists**
        storage.saveAddressBook(modelManager.getAddressBook());

        // Delete the person to trigger the backup
        modelManager.deletePerson(person);

        // Verify that the backup directory exists
        Path backupDir = Paths.get("backups");
        assertTrue(Files.exists(backupDir), "Backup directory should exist.");

        // Verify that at least one backup file exists
        try (Stream<Path> paths = Files.walk(backupDir)) {
            List<Path> backupFiles = paths.filter(Files::isRegularFile).collect(Collectors.toList());
            assertFalse(backupFiles.isEmpty(), "Backup files should exist.");
        }
    }

    @Test
    public void backupData_validFileName_backupSuccessful() throws Exception {
        // Set up the storage with a valid address book file path
        Path addressBookFilePath = temporaryFolder.resolve("addressBook.json");
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressBookFilePath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(addressBookFilePath);
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

        // Initialize ModelManager with storage and userPrefs
        ModelManager modelManager = new ModelManager(new AddressBook(), userPrefs, storage);

        // Save the address book to ensure the file exists
        storage.saveAddressBook(modelManager.getAddressBook());

        // Call backupData
        String backupFileName = "testBackup";
        assertDoesNotThrow(() -> modelManager.backupData(backupFileName));

        // Verify that the backup directory exists
        Path backupDir = Paths.get("backups");
        assertTrue(Files.exists(backupDir), "Backup directory should exist.");

        // Verify that at least one backup file exists with the specified name
        try (Stream<Path> paths = Files.walk(backupDir)) {
            List<Path> backupFiles = paths.filter(Files::isRegularFile).collect(Collectors.toList());
            boolean backupFileExists = backupFiles.stream()
                    .anyMatch(path -> path.getFileName().toString().contains(backupFileName));
            assertTrue(backupFileExists, "Backup file with specified name should exist.");
        }
    }

    @Test
    public void backupData_nullFileName_backupSuccessful() throws Exception {
        // Set up the storage with a valid address book file path
        Path addressBookFilePath = temporaryFolder.resolve("addressBook.json");
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressBookFilePath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(addressBookFilePath);
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

        // Initialize ModelManager with storage and userPrefs
        ModelManager modelManager = new ModelManager(new AddressBook(), userPrefs, storage);

        // Save the address book to ensure the file exists
        storage.saveAddressBook(modelManager.getAddressBook());

        // Call backupData with null file name
        assertDoesNotThrow(() -> modelManager.backupData(null));

        // Verify that the backup directory exists
        Path backupDir = Paths.get("backups");
        assertTrue(Files.exists(backupDir), "Backup directory should exist.");

        // Verify that at least one backup file exists (with generated timestamped name)
        try (Stream<Path> paths = Files.walk(backupDir)) {
            List<Path> backupFiles = paths.filter(Files::isRegularFile).collect(Collectors.toList());
            assertFalse(backupFiles.isEmpty(), "Backup files should exist.");
        }
    }

}
