package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ClearCommand}.
 */
public class ClearCommandTest {

    @TempDir
    public Path temporaryFolder;

    private StorageManager storage;

    /**
     * Sets up the test environment with the required model and storage.
     */
    @BeforeEach
    public void setUp() throws IOException {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        storage = new StorageManager(addressBookStorage, userPrefsStorage);
    }

    private Model createModelWithAddressBook(AddressBook addressBook) throws IOException {
        return new ModelManagerWithBackupCount(addressBook, new UserPrefs(), storage);
    }

    /**
     * Tests the successful execution of clearing an empty address book, ensuring a backup is created.
     */
    @Test
    public void execute_emptyAddressBook_success() throws IOException {
        ModelManagerWithBackupCount model =
                (ModelManagerWithBackupCount) createModelWithAddressBook(new AddressBook());
        ModelManagerWithBackupCount expectedModel =
                (ModelManagerWithBackupCount) createModelWithAddressBook(new AddressBook());

        String description = "clear";
        int expectedBackupIndex = 1;

        // Increment backup count in expected model
        expectedModel.backupData(description);

        String expectedMessage = String.format(ClearCommand.MESSAGE_SUCCESS, expectedBackupIndex, description);

        assertCommandSuccess(new ClearCommand(), model, expectedMessage, expectedModel);

        // Verify a backup was created before clearing
        assertEquals(1, model.getBackupCount(), "Backup should be created once before clearing");
    }

    /**
     * Tests the successful execution of clearing a non-empty address book, ensuring a backup is created.
     */
    @Test
    public void execute_nonEmptyAddressBook_success() throws IOException {
        ModelManagerWithBackupCount model =
                (ModelManagerWithBackupCount) createModelWithAddressBook(getTypicalAddressBook());
        ModelManagerWithBackupCount expectedModel =
                (ModelManagerWithBackupCount) createModelWithAddressBook(getTypicalAddressBook());
        expectedModel.setAddressBook(new AddressBook());

        String description = "clear";
        int expectedBackupIndex = 1;

        // Increment backup count in expected model
        expectedModel.backupData(description);

        String expectedMessage = String.format(ClearCommand.MESSAGE_SUCCESS, expectedBackupIndex, description);

        assertCommandSuccess(new ClearCommand(), model, expectedMessage, expectedModel);

        // Verify a backup was created before clearing
        assertEquals(1, model.getBackupCount(), "Backup should be created once before clearing");
    }

    /**
     * A custom ModelManager that counts backup calls.
     */
    private static class ModelManagerWithBackupCount extends ModelManager {
        private int backupCount = 0;

        ModelManagerWithBackupCount(AddressBook addressBook, UserPrefs userPrefs, StorageManager storage)
                throws IOException {
            super(addressBook, userPrefs, storage);
        }

        @Override
        public int backupData(String actionDescription) {
            backupCount++;
            return backupCount;
        }

        public int getBackupCount() {
            return backupCount;
        }
    }
}
