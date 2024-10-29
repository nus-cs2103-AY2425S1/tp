package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Optional;
import java.util.stream.Stream;

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
        // Ensure backup directory exists before each test
        Files.createDirectories(TEMP_BACKUP_DIR);
        backupManager = new BackupManager(TEMP_BACKUP_DIR);

        // Create a temporary file to simulate address book data
        Files.writeString(TEMP_FILE, "Sample AddressBook Data");
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up any backup files created during the test
        try (Stream<Path> paths = Files.walk(TEMP_BACKUP_DIR)) {
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

    @Test
    public void cleanOldBackups_throwsExceptionForInvalidMaxBackups() throws IOException {
        try {
            backupManager.cleanOldBackups(0); // Should trigger IllegalArgumentException
            fail("Expected IllegalArgumentException to be thrown for maxBackups < 1.");
        } catch (IllegalArgumentException e) {
            assertEquals("maxBackups must be at least 1.", e.getMessage(),
                    "The exception message should match the expected message.");
        }
    }

    @Test
    public void backupDirectoryInitialization_createsDirectorySuccessfully() throws IOException {
        Path newBackupDir = TEMP_BACKUP_DIR.resolve("new-backup-dir");
        new BackupManager(newBackupDir);
        assertTrue(Files.exists(newBackupDir), "Backup directory should be created successfully.");
    }

    @Test
    public void backupDirectoryInitialization_throwsExceptionForNullPath() {
        assertThrows(IOException.class, () -> new BackupManager(null));
    }

    @Test
    public void restoreMostRecentBackup_noBackupAvailable_returnsEmptyOptional() throws IOException {
        Files.deleteIfExists(TEMP_FILE); // Ensure no backups exist
        Optional<Path> restoredBackup = backupManager.restoreMostRecentBackup();
        assertFalse(restoredBackup.isPresent(), "No backup should be available.");
    }

    @Test
    public void getFileCreationTime_fileNotFound_returnsCurrentTime() {
        // Provide a non-existent path to simulate IOException
        Path nonExistentPath = TEMP_BACKUP_DIR.resolve("non-existent-file.json");

        // Call the method and capture the result
        FileTime result = backupManager.getFileCreationTime(nonExistentPath);

        // Verify that the fallback time is set to the current time
        long now = System.currentTimeMillis();
        assertTrue(Math.abs(result.toMillis() - now) < 1000,
                "The returned FileTime should be close to the current system time.");
    }

    @Test
    public void triggerBackup_createsBackupFileWithCorrectFormat() throws IOException {
        // Use triggerBackup to create a backup with a specific description.
        String description = "delete ALICE";
        backupManager.triggerBackup(TEMP_FILE, description);

        // Verify the backup file is created in the directory with the correct format.
        boolean backupFileExists = Files.list(TEMP_BACKUP_DIR).anyMatch(path ->
                path.getFileName().toString().contains(description)
                        && path.getFileName().toString().endsWith(".json"));
        assertTrue(backupFileExists, "The backup file should exist and be named with the correct format.");
    }

}
