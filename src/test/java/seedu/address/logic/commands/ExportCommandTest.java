package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertFileAccessCommandSuccess;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_SUCCESS;

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

public class ExportCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private Storage storage = new StorageStub();
    private Storage expectedStorage = storage;

    @Test
    public void execute_export_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS);
        assertFileAccessCommandSuccess(new ExportCommand(), model, storage, expectedCommandResult, expectedModel,
                expectedStorage);
    }

    /**
     * A Storage stub that can save address books.
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
            return;
        }
    }
}
