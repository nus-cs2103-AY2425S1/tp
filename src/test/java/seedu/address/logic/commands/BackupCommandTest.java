package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code BackupCommand}.
 */
public class BackupCommandTest {

    @TempDir
    public Path temporaryFolder;

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
