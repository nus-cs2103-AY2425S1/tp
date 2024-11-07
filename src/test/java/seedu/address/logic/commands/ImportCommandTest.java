package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FILE_TYPE_PATH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_JSON_FORMAT_PATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILE_PATH;
import static seedu.address.logic.commands.CommandTestUtil.assertFileAccessCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertFileAccessCommandSuccess;
import static seedu.address.logic.commands.ImportCommand.FILE_DATA_LOAD_ERROR_FORMAT;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.Storage;

public class ImportCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    private StorageStub storage = new StorageStub();
    private StorageStub expectedStorage = new StorageStub();

    public void execute_importValidFile_success() {
        ImportCommand importCommand = new ImportCommand(VALID_FILE_PATH);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, VALID_FILE_PATH);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertFileAccessCommandSuccess(importCommand, model, storage, expectedCommandResult,
                expectedModel, expectedStorage);
    }
    @Test
    public void execute_importInvalidFileType_failure() {
        String expectedMessage = String.format(FILE_DATA_LOAD_ERROR_FORMAT, INVALID_FILE_TYPE_PATH);
        ImportCommand importCommand = new ImportCommand(INVALID_FILE_TYPE_PATH);

        assertFileAccessCommandFailure(importCommand, model, storage, expectedMessage);
    }

    @Test
    public void equals() {
        final ImportCommand standardCommand = new ImportCommand(VALID_FILE_PATH);

        // same values -> returns true
        ImportCommand commandWithSameValues = new ImportCommand(VALID_FILE_PATH);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different file path -> returns false
        assertFalse(standardCommand.equals(new ImportCommand(INVALID_JSON_FORMAT_PATH)));
    }

    /**
     * A Storage stub that contains nothing.
     */
    private class StorageStub implements Storage {
        @Override
        public Path getUserPrefsFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
            return new JsonAddressBookStorage(filePath).readAddressBook(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
            throw new AssertionError("This method should not be called.");
        }
    }
}
