package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ArchiveCommandTest {
    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ArchiveCommand(), model, ArchiveCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        assertCommandSuccess(new ArchiveCommand(), model, ArchiveCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_failure() {
        Model model = new ModelManagerStubThrowingIOException();
        Model expectedModel = new ModelManagerStubThrowingIOException();

        assertCommandSuccess(new ArchiveCommand(), model, ArchiveCommand.MESSAGE_FAILURE, expectedModel);
    }

    /**
     * A ModelManager stub that always throws IOException when archiveAddressBook() is called.
     */
    private class ModelManagerStubThrowingIOException extends ModelManager {
        @Override
        public void archiveAddressBook() throws IOException {
            throw new IOException();
        }
    }
}
