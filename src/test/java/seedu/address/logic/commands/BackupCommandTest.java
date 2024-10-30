package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.BackupManager;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

/**
 * Contains integration tests (interaction with the Model) for {@code BackupCommand}.
 */
public class BackupCommandTest {

    @TempDir
    public Path temporaryFolder;

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
    public void execute_backupFails_throwsCommandException() throws Exception {
        // Initialize ModelManager without storage
        Model model = new ModelManager(new AddressBook(), new UserPrefs(), null);

        // Create BackupCommand
        String actionDescription = "testBackup";
        BackupCommand backupCommand = new BackupCommand(actionDescription);

        // Expect CommandException when storage is not initialized
        CommandException exception = assertThrows(CommandException.class, () -> backupCommand.execute(model));
        assertEquals("Failed to create backup: Storage is not initialized!", exception.getMessage());
    }

    @Test
    public void execute_backupSuccessful() throws Exception {
        // Set up the storage and model with a temporary directory
        Path addressBookFilePath = temporaryFolder.resolve("addressBook.json");
        Path userPrefsFilePath = temporaryFolder.resolve("userPrefs.json");
        Path backupDirectoryPath = temporaryFolder.resolve("backups");
        Files.createDirectories(backupDirectoryPath);

        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressBookFilePath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsFilePath);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(addressBookFilePath);

        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        // Set BackupManager with the temporary backup directory
        storage.setBackupManager(new BackupManager(backupDirectoryPath));

        Model model = new ModelManager(new AddressBook(), userPrefs, storage);

        // Save the address book to ensure the file exists
        storage.saveAddressBook(model.getAddressBook());

        // Create BackupCommand with a test action description
        String actionDescription = "testBackup";
        BackupCommand backupCommand = new BackupCommand(actionDescription);

        // Execute the command
        CommandResult result = backupCommand.execute(model);

        // Verify that the command result is as expected
        String expectedMessagePrefix = "Backup ";
        String expectedMessageSuffix = " is created successfully.";
        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.startsWith(expectedMessagePrefix), "Feedback should start with 'Backup '");
        assertTrue(feedback.endsWith(expectedMessageSuffix), "Feedback should end with ' is created successfully.'");

        // Verify that the backup file was created in the backup directory
        try (Stream<Path> files = Files.list(backupDirectoryPath)) {
            List<Path> backupFiles = files.collect(Collectors.toList());
            assertEquals(1, backupFiles.size(), "There should be exactly one backup file in the backup directory.");

            // Optionally, verify that the backup file has the correct name pattern
            String backupFileName = backupFiles.get(0).getFileName().toString();
            assertTrue(backupFileName.matches("\\d+_testBackup_.*\\.json"),
                    "Backup file name should match the expected pattern.");
        }
    }

    @Test
    public void hashCode_sameActionDescription_sameHashCode() {
        BackupCommand command1 = new BackupCommand("backup1");
        BackupCommand command2 = new BackupCommand("backup1");

        assertEquals(command1.hashCode(),
                command2.hashCode(),
                "Commands with the same actionDescription should have the same hash code.");
    }

    @Test
    public void hashCode_differentActionDescription_differentHashCode() {
        BackupCommand command1 = new BackupCommand("backup1");
        BackupCommand command2 = new BackupCommand("backup2");

        assertNotEquals(command1.hashCode(),
                command2.hashCode(),
                "Commands with different actionDescriptions should have different hash codes.");
    }

    @Test
    public void equals() {
        BackupCommand backupCommand1 = new BackupCommand("backup1");
        BackupCommand backupCommand2 = new BackupCommand("backup2");
        BackupCommand backupCommandNull1 = new BackupCommand(null);
        BackupCommand backupCommandNull2 = new BackupCommand(null);

        // Same object
        assertEquals(backupCommand1, backupCommand1);

        // Different objects, same actionDescription
        BackupCommand backupCommand1Copy = new BackupCommand("backup1");
        assertEquals(backupCommand1, backupCommand1Copy);

        // Different actionDescription
        assertEquals(false, backupCommand1.equals(backupCommand2));

        // Null actionDescription
        assertEquals(backupCommandNull1, backupCommandNull2);

        // Different types
        assertEquals(false, backupCommand1.equals(null));
        assertEquals(false, backupCommand1.equals("some string"));
    }

    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
