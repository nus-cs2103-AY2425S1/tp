package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test class for BackupManager.
 */
public class BackupManagerTest {

    @TempDir
    public Path temporaryFolder;
    private BackupManager backupManager;
    private Path backupDirectory;
    private Path sourceFile;

    private static final DateTimeFormatter FILE_TIMESTAMP_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
    private static final String BACKUP_FILE_REGEX =
            "(\\d+)_(.*?)_(\\d{4}-\\d{2}-\\d{2}_\\d{2}-\\d{2}-\\d{2}-\\d{3})\\.json";
    private static final Pattern BACKUP_FILE_PATTERN =
            Pattern.compile(BACKUP_FILE_REGEX);


    @BeforeEach
    public void setUp() throws IOException {
        // Set up the backup directory and source file in the temporary folder
        backupDirectory = temporaryFolder.resolve("backups");
        Files.createDirectories(backupDirectory);

        sourceFile = temporaryFolder.resolve("addressBook.json");
        Files.writeString(sourceFile, "Sample AddressBook Data");

        backupManager = new BackupManager(backupDirectory);
    }

    /**
     * Cleans up the backup directory after each test by deleting any files created.
     */
    @AfterEach
    public void cleanUpDefaultBackupDirectory() throws IOException {
        Path defaultBackupDirectory = Paths.get("backups");
        if (Files.exists(defaultBackupDirectory)) {
            Files.walk(defaultBackupDirectory)
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            System.err.println("Failed to delete file: " + path + " - " + e.getMessage());
                        }
                    });
        }
    }

    @Test
    public void createIndexedBackup_validParameters_backupCreated() throws IOException {
        String actionDescription = "testAction";
        int index = backupManager.createIndexedBackup(sourceFile, actionDescription);

        // Verify that the backup file exists
        assertTrue(index >= 0 && index < 10, "Backup index should be between 0 and 9.");

        // Check that a backup file with the correct index and description exists
        boolean backupFileExists = Files.list(backupDirectory)
                .anyMatch(path -> path
                        .getFileName()
                        .toString()
                        .matches(index + "_" + actionDescription + "_.*\\.json"));

        assertTrue(backupFileExists, "Backup file with specified index and description should exist.");
    }

    @Test
    public void createIndexedBackup_nullActionDescription_backupCreated() throws IOException {
        int index = backupManager.createIndexedBackup(sourceFile, null);

        // Verify that the backup file exists
        assertTrue(index >= 0 && index < 10, "Backup index should be between 0 and 9.");

        // Check that a backup file with the correct index exists
        boolean backupFileExists = Files.list(backupDirectory)
                .anyMatch(path -> path.getFileName().toString().matches(index + "_null_.*\\.json"));

        assertTrue(backupFileExists, "Backup file with specified index should exist.");
    }

    @Test
    public void restoreBackupByIndex_validIndex_backupRestored() throws IOException {
        // Create a backup at index 0
        int index = backupManager.createIndexedBackup(sourceFile, "restoreTest");

        // Attempt to restore the backup
        Path restoredPath = backupManager.restoreBackupByIndex(index);

        // Verify that the restored path exists and matches the backup file
        assertNotNull(restoredPath, "Restored path should not be null.");
        assertTrue(Files.exists(restoredPath), "Restored backup file should exist.");
    }

    @Test
    public void restoreBackupByIndex_invalidIndex_throwsIoException() {
        int invalidIndex = 9; // Assuming index 9 has no backup

        // Expect IOException when restoring a non-existent backup
        IOException exception = assertThrows(IOException.class, () -> backupManager.restoreBackupByIndex(invalidIndex));
        assertEquals("Backup with index " + invalidIndex + " not found.", exception.getMessage());
    }

    @Test
    public void backupManager_initializationWithNullPath_throwsIoException() {
        assertThrows(IOException.class, () -> new BackupManager(null));
    }

    @Test
    public void getFormattedBackupList_withBackups_returnsFormattedList() throws IOException {
        // Create a few backups with different descriptions
        backupManager.createIndexedBackup(sourceFile, "backup1");
        backupManager.createIndexedBackup(sourceFile, "backup2");
        backupManager.createIndexedBackup(sourceFile, "backup3");

        String formattedList = backupManager.getFormattedBackupList();

        // Check that the formatted list contains each description in the correct format
        assertTrue(formattedList.contains("[backup1]"));
        assertTrue(formattedList.contains("[backup2]"));
        assertTrue(formattedList.contains("[backup3]"));
        assertTrue(formattedList.contains("Created on:")); // Ensure timestamp is included
    }

    @Test
    public void extractIndex_validFilename_returnsCorrectIndex() {
        // Manually create paths to simulate different backup files
        Path backupPath = backupDirectory.resolve("3_testAction_2024-10-30_15-45-00-000.json");
        int index = backupManager.extractIndex(backupPath);

        assertEquals(3, index, "Extracted index should be 3");
    }

    @Test
    public void extractIndex_invalidFilename_returnsNegativeOne() {
        // Provide an incorrectly formatted filename
        Path invalidPath = backupDirectory.resolve("invalid_backup_name.json");
        int index = backupManager.extractIndex(invalidPath);

        assertEquals(-1, index, "Invalid filenames should return -1 as index");
    }

    @Test
    public void extractActionDescription_validFilename_returnsCorrectDescription() {
        Path backupPath = backupDirectory.resolve("2_actionDescription_2024-10-30_15-45-00-000.json");
        String actionDescription = backupManager.extractActionDescription(backupPath);

        assertEquals("actionDescription", actionDescription, "Extracted description should match");
    }

    @Test
    public void extractActionDescription_invalidFilename_returnsUnknown() {
        Path invalidPath = backupDirectory.resolve("wrong_format.json");
        String actionDescription = backupManager.extractActionDescription(invalidPath);

        assertEquals("Unknown", actionDescription, "Invalid filenames should return 'Unknown' as action description");
    }

    @Test
    public void createIndexedBackup_descriptionTooLong_throwsIoException() throws IOException {
        // Create a description that causes the file name to exceed MAX_FILENAME_LENGTH
        String longDescription = "a".repeat(300); // Exceeds 250 characters

        // Attempt to create backup with long description
        IOException exception = assertThrows(IOException.class, () -> {
            backupManager.createIndexedBackup(sourceFile, longDescription);
        });

        // Check that the exception message is correct
        assertEquals("Backup file name exceeds the maximum length of 250 characters. Please shorten your description.",
                exception.getMessage());
    }

    @Test
    public void isBackupAvailable_backupExists_returnsTrue() throws IOException {
        // Create a backup at index 0
        int backupIndex = backupManager.createIndexedBackup(sourceFile, "testAction");

        // Check if backup is available
        assertTrue(backupManager.isBackupAvailable(backupIndex));
    }

    @Test
    public void isBackupAvailable_backupDoesNotExist_returnsFalse() {
        // Check for a backup at index 0 when none exists
        assertFalse(backupManager.isBackupAvailable(0));
    }

    @Test
    public void createIndexedBackup_maxBackupsReached_overwritesOldestBackup() throws IOException {
        // Create MAX_BACKUPS backups to reach the limit
        for (int i = 0; i < 10; i++) {
            backupManager.createIndexedBackup(sourceFile, "action" + i);
        }

        // Capture the oldest backup's index and timestamp
        Path oldestBackup = Files.list(backupDirectory)
                .min(Comparator.comparing(this::getFileTimestamp))
                .orElseThrow(() -> new IOException("No backups found"));

        int oldestIndex = backupManager.extractIndex(oldestBackup);

        // Create one more backup to trigger overwrite
        int newIndex = backupManager.createIndexedBackup(sourceFile, "newAction");

        // Verify that the oldest backup was overwritten
        assertEquals(oldestIndex, newIndex, "New backup should overwrite the oldest backup's index");

        // Ensure that the total number of backups does not exceed MAX_BACKUPS
        long backupCount = Files.list(backupDirectory).count();
        assertEquals(10, backupCount, "Total number of backups should not exceed 10");
    }

    /**
     * Helper method to get the timestamp of a backup file for comparison.
     */
    private LocalDateTime getFileTimestamp(Path backupPath) {
        String filename = backupPath.getFileName().toString();
        Matcher matcher = BACKUP_FILE_PATTERN.matcher(filename);
        if (matcher.matches()) {
            String timestampStr = matcher.group(3);
            return LocalDateTime.parse(timestampStr, FILE_TIMESTAMP_FORMATTER);
        }
        return LocalDateTime.MIN;
    }
    
    @Test
    public void isBackupAvailable_invalidIndex_returnsFalse() {
        // Check for a backup with a negative index
        assertFalse(backupManager.isBackupAvailable(-1));
    }

}
