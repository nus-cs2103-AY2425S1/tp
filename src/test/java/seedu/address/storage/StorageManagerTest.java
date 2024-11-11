package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

/**
 * Test class for {@code StorageManager}.
 */
public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;
    private Path backupDir;

    @BeforeEach
    public void setUp() throws IOException {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("userPrefs.json"));
        backupDir = testFolder.resolve("backups");
        Files.createDirectories(backupDir);

        // Initialize StorageManager with the backup directory in the temp folder
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
        // Use the public setter to set the BackupManager
        storageManager.setBackupManager(new BackupManager(backupDir));
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up any backup files created during the test
        if (Files.exists(backupDir)) {
            Files.walk(backupDir)
                    .filter(Files::isRegularFile)
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
        original.setAddressBookFilePath(getTempFilePath("addressBook.json"));
        storageManager.saveUserPrefs(original);

        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    // ============== AddressBook Storage Tests =================

    @Test
    public void addressBookReadSave_success() throws Exception {
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);

        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    // ============== Backup and Restore Tests =================

    @Test
    public void createBackup_validActionDescription_backupCreated() throws Exception {
        // Save the address book to ensure the file exists
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);

        // Create a backup
        String actionDescription = "testBackup";
        int backupIndex = storageManager.createBackup(actionDescription);

        // Verify that the backup file exists
        assertTrue(backupIndex >= 0 && backupIndex < 10, "Backup index should be between 0 and 9.");

        // Check that a backup file with the correct index and description exists
        boolean backupFileExists = Files.list(backupDir)
                .anyMatch(path -> path
                        .getFileName()
                        .toString()
                        .matches(backupIndex + "_" + actionDescription + "_.*\\.json"));

        assertTrue(backupFileExists, "Backup file with specified index and description should exist.");
    }

    @Test
    public void restoreBackup_validIndex_restoresSuccessfully() throws Exception {
        // Save the address book to ensure the file exists
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);

        // Create a backup
        String actionDescription = "testRestore";
        int backupIndex = storageManager.createBackup(actionDescription);

        // Modify the address book
        AddressBook modifiedAddressBook = new AddressBook();
        storageManager.saveAddressBook(modifiedAddressBook);

        // Restore the backup
        Path backupPath = storageManager.restoreBackup(backupIndex);

        // Read the restored data
        Optional<ReadOnlyAddressBook> restoredData = storageManager.readAddressBook(backupPath);
        assertTrue(restoredData.isPresent(), "Restored data should be present.");

        // Save the restored data to the main address book file
        storageManager.saveAddressBook(restoredData.get());

        // Verify that the address book matches the original
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved), "Restored address book should match the original.");
    }

    @Test
    public void restoreBackup_invalidIndex_throwsIoException() {
        int invalidIndex = 9; // Assuming index 9 has no backup

        IOException exception = assertThrows(IOException.class, () -> {
            storageManager.restoreBackup(invalidIndex);
        });

        assertEquals("Backup with index " + invalidIndex + " not found.", exception.getMessage());
    }

    @Test
    public void cleanOldBackups_validMaxBackups_removesOldBackups() throws Exception {
        // Save the address book to ensure the file exists
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);

        // Create multiple backups
        for (int i = 0; i < 5; i++) {
            storageManager.createBackup("testCleanup" + i);
            // Wait a bit to ensure different timestamps
            Thread.sleep(10);
        }

        // Ensure there are 5 backups
        try (Stream<Path> paths = Files.list(backupDir)) {
            long backupCount = paths.count();
            assertEquals(5, backupCount, "There should be 5 backups before cleanup.");
        }

        // Clean old backups, keeping only 2
        storageManager.cleanOldBackups(2);

        // Verify that only 2 backups remain
        try (Stream<Path> paths = Files.list(backupDir)) {
            long backupCount = paths.count();
            assertEquals(2, backupCount, "There should be 2 backups after cleanup.");
        }
    }


    @Test
    public void cleanOldBackups_invalidMaxBackups_throwsIllegalArgumentException() {
        // Expect IllegalArgumentException when maxBackups is less than 1
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            storageManager.cleanOldBackups(0);
        });

        assertEquals("maxBackups must be at least 1.", exception.getMessage());
    }
}
