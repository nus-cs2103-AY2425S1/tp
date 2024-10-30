package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public void createIndexedBackup_multipleBackups_indicesRotate() throws IOException {
        // Create multiple backups to test index rotation
        for (int i = 0; i < 15; i++) { // Exceeds MAX_BACKUPS to test rotation
            int index = backupManager.createIndexedBackup(sourceFile, "action" + i);

            // Verify that the index cycles between 0 and 9
            assertEquals(i % 10, index, "Backup index should cycle between 0 and 9.");
        }
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



}
