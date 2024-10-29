package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    private static final Path BACKUP_DIR = Paths.get("backups");
    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() throws IOException {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("userPrefs.json"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
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

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    // ============== User Preferences Tests =================

    @Test
    public void prefsReadSave_success() throws Exception {
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);

        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void prefsRead_whenFileMissing_returnsEmptyOptional() throws Exception {
        JsonUserPrefsStorage missingUserPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("missingPrefs.json"));
        StorageManager missingStorage = new StorageManager(
                new JsonAddressBookStorage(getTempFilePath("addressBook.json")), missingUserPrefsStorage);

        Optional<UserPrefs> prefs = missingStorage.readUserPrefs();
        assertFalse(prefs.isPresent(), "Expected no UserPrefs to be found.");
    }

    // ============== AddressBook Storage Tests =================

    @Test
    public void addressBookReadSave_success() throws Exception {
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);

        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath_returnsCorrectPath() {
        Path expectedPath = getTempFilePath("addressBook.json");
        assertEquals(expectedPath, storageManager.getAddressBookFilePath());
    }

    // ============== Backup and Restore Tests =================

    @Test
    public void backupAddressBook_createsBackupFile() throws IOException {
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);

        Path backupFile = getTempFilePath("backup.json");
        storageManager.saveAddressBook(original, backupFile);

        assertTrue(Files.exists(backupFile), "Backup file should be created.");
    }

    @Test
    public void cleanOldBackups_invalidMaxBackups_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, (
                ) -> storageManager.cleanOldBackups(0),
                "Expected IllegalArgumentException for maxBackups < 1.");

        assertEquals("maxBackups must be at least 1.", exception.getMessage());
    }

}
