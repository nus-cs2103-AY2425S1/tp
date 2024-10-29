package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

public class BackupCommandTest {

    @TempDir
    public Path temporaryFolder;

    @Test
    public void execute_backupSuccessful() throws Exception {
        // Set up the storage and model
        Path addressBookFilePath = temporaryFolder.resolve("addressBook.json");
        Path userPrefsFilePath = temporaryFolder.resolve("userPrefs.json");
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressBookFilePath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsFilePath);
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(addressBookFilePath);

        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        Model model = new ModelManager(new AddressBook(), userPrefs, storage);

        // Save the address book to ensure the file exists
        storage.saveAddressBook(model.getAddressBook());

        // Create BackupCommand with file name
        String fileName = "testBackup";
        BackupCommand backupCommand = new BackupCommand(fileName);

        // Execute the command
        CommandResult result = backupCommand.execute(model);

        // Check that the command result is as expected
        assertEquals(new CommandResult(BackupCommand.MESSAGE_SUCCESS), result);

        // Verify that the backup file exists
        Path backupDir = Paths.get("backups");
        assertTrue(Files.exists(backupDir), "Backup directory should exist.");

        try (Stream<Path> paths = Files.walk(backupDir)) {
            List<Path> backupFiles = paths.filter(Files::isRegularFile).collect(Collectors.toList());
            boolean backupFileExists = backupFiles.stream()
                    .anyMatch(path -> path.getFileName().toString().contains(fileName));
            assertTrue(backupFileExists, "Backup file with specified name should exist.");
        }
    }

    @Test
    public void execute_backupFails_throwsCommandException() throws Exception {
        // Initialize ModelManager without storage
        Model model = new ModelManager(new AddressBook(), new UserPrefs(), null);

        // Create BackupCommand
        String fileName = "testBackup";
        BackupCommand backupCommand = new BackupCommand(fileName);

        // Expect CommandException when storage is not initialized
        CommandException exception = assertThrows(CommandException.class, () -> backupCommand.execute(model));
        assertEquals("Failed to create manual backup: Storage is not initialized!", exception.getMessage());
    }

    @Test
    public void equals() {
        BackupCommand backupCommand1 = new BackupCommand("backup1");
        BackupCommand backupCommand2 = new BackupCommand("backup2");
        BackupCommand backupCommandNull1 = new BackupCommand(null);
        BackupCommand backupCommandNull2 = new BackupCommand(null);

        // Same object
        assertEquals(backupCommand1, backupCommand1);

        // Different objects, same fileName
        BackupCommand backupCommand1Copy = new BackupCommand("backup1");
        assertEquals(backupCommand1, backupCommand1Copy);

        // Different fileName
        assertTrue(!backupCommand1.equals(backupCommand2));

        // Null fileName
        assertEquals(backupCommandNull1, backupCommandNull2);

        // Different types
        assertTrue(!backupCommand1.equals(null));
        assertTrue(!backupCommand1.equals("some string"));
    }
}
