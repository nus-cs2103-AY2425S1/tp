package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

public class RestoreCommandTest {

    @TempDir
    public Path temporaryFolder;

    private Model model;
    private Storage storage;

    @BeforeEach
    public void setUp() throws IOException {
        Path addressBookPath = temporaryFolder.resolve("addressBook.json");
        Path userPrefsPath = temporaryFolder.resolve("userPrefs.json");

        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressBookPath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsPath);
        storage = new StorageManager(addressBookStorage, userPrefsStorage);

        model = new ModelManager(new AddressBook(), new UserPrefs(), storage);
    }

    @Test
    public void execute_restoreFromMostRecentBackup_success() throws Exception {
        // Setup - Create a backup file to restore from
        Path backupPath = temporaryFolder.resolve("backup.json");
        AddressBook backupData = new AddressBook();
        storage.saveAddressBook(backupData, backupPath);

        // Perform the restore
        RestoreCommand restoreCommand = new RestoreCommand(Optional.of(backupPath));
        CommandResult result = restoreCommand.execute(model);

        // Assert the successful restoration
        assertEquals(
                String.format(
                        RestoreCommand.MESSAGE_RESTORE_SUCCESS, backupPath.toString()), result.getFeedbackToUser());
    }

    @Test
    public void execute_restoreFromSpecificPath_success() throws Exception {
        // Setup - Create a backup file to restore from
        Path specificPath = temporaryFolder.resolve("specificBackup.json");
        AddressBook specificBackupData = new AddressBook();
        storage.saveAddressBook(specificBackupData, specificPath);

        // Perform the restore from a specific path
        RestoreCommand restoreCommand = new RestoreCommand(Optional.of(specificPath));
        CommandResult result = restoreCommand.execute(model);

        // Assert the successful restoration
        assertEquals(
                String.format(
                        RestoreCommand.MESSAGE_RESTORE_SUCCESS, specificPath.toString()), result.getFeedbackToUser());
    }

    @Test
    public void execute_restoreBackupFileNotFound_failure() throws Exception {
        // Setup - No backup file exists
        Path nonexistentBackupPath = temporaryFolder.resolve("nonexistentBackup.json");

        RestoreCommand restoreCommand = new RestoreCommand(Optional.of(nonexistentBackupPath));

        // Assert that trying to restore from a nonexistent backup file throws an exception
        assertThrows(CommandException.class, () -> restoreCommand.execute(model),
                RestoreCommand.MESSAGE_RESTORE_FAILURE);
    }

    @Test
    public void execute_restoreFromInvalidPath_failure() throws Exception {
        // Setup - Create an invalid/corrupt backup file
        Path invalidPath = temporaryFolder.resolve("invalidBackup.json");
        Files.writeString(invalidPath, "{ invalid json }");

        // Try to restore from the invalid backup file
        RestoreCommand restoreCommand = new RestoreCommand(Optional.of(invalidPath));

        // Assert that trying to restore from an invalid file throws an exception
        assertThrows(CommandException.class, () -> restoreCommand.execute(model),
                RestoreCommand.MESSAGE_RESTORE_FAILURE);
    }
}
