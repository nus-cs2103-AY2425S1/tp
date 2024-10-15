package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for BackupManager.
 */
public class BackupManagerTest {

    private static final Path TEMP_BACKUP_DIR = Paths.get("test-backups");
    private static final Path TEMP_FILE = TEMP_BACKUP_DIR.resolve("test-addressbook.json");
    private BackupManager backupManager;

    @BeforeEach
    public void setUp() throws IOException {
        // Create a new backup manager for each test
        if (!Files.exists(TEMP_BACKUP_DIR)) {
            Files.createDirectories(TEMP_BACKUP_DIR);
        }
        backupManager = new BackupManager(TEMP_BACKUP_DIR);

        // Create a temporary file to simulate the address book file
        Files.writeString(TEMP_FILE, "Sample AddressBook Data");
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up test directory and its contents
        Files.walk(TEMP_BACKUP_DIR)
                .sorted((path1, path2) -> path2.compareTo(path1)) // Delete files before directories
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    public void testBackupCreation() throws IOException {
        // Test that a backup is created successfully
        backupManager.saveBackup(TEMP_FILE);

        long backupCount = Files.list(TEMP_BACKUP_DIR).count();
        assertEquals(2, backupCount, "Backup file should be created in the directory.");
    }

    @Test
    public void testBackupThrowsExceptionIfFileNotFound() {
        // Test that saveBackup() throws an exception for non-existent file
        Path nonExistentFile = TEMP_BACKUP_DIR.resolve("non-existent-file.json");
        assertThrows(IOException.class, () -> backupManager.saveBackup(nonExistentFile));
    }

    @Test
    public void testRestoreMostRecentBackup() throws IOException {
        // Create multiple backups and test restoring the latest one
        backupManager.saveBackup(TEMP_FILE);
        Files.writeString(TEMP_FILE, "Updated AddressBook Data");
        backupManager.saveBackup(TEMP_FILE);

        Optional<Path> restoredBackup = backupManager.restoreMostRecentBackup();
        assertTrue(restoredBackup.isPresent(), "A recent backup should exist.");

        // Verify that the restored backup contains the latest data
        String restoredData = Files.readString(restoredBackup.get());
        assertEquals("Updated AddressBook Data", restoredData);
    }

    @Test
    public void testRestoreMostRecentBackupWhenNoBackupsExist() throws IOException {
        // Delete all backups and attempt to restore
        Files.deleteIfExists(TEMP_FILE);

        Optional<Path> restoredBackup = backupManager.restoreMostRecentBackup();
        assertFalse(restoredBackup.isPresent(), "No backup should be available for restoration.");
    }

    @Test
    public void testCleanOldBackups() throws IOException {
        // Create multiple backups
        backupManager.saveBackup(TEMP_FILE);
        backupManager.saveBackup(TEMP_FILE);
        backupManager.saveBackup(TEMP_FILE);

        // Clean backups, keeping only 1
        backupManager.cleanOldBackups(1);

        long backupCount = Files.list(TEMP_BACKUP_DIR).count();
        assertEquals(1, backupCount, "Only one backup should remain after cleanup.");
    }

    @Test
    public void testCleanOldBackupsThrowsExceptionForInvalidMaxBackups() {
        // Test that cleanOldBackups throws IllegalArgumentException for invalid input
        assertThrows(IllegalArgumentException.class, () -> backupManager.cleanOldBackups(0));
    }
}
