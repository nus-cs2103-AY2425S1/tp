package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.filename.Filename;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ArchiveCommandTest {
    @BeforeEach
    public void setUp() throws IOException {
        FileUtil.createIfMissing(new ModelManager().getAddressBookFilePath());
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(new ModelManager().getAddressBookFilePath());

        // Delete all files in the archive directory
        Files.walk(new ModelManager().getAddressBookFilePath().getParent())
                .map(java.nio.file.Path::toFile)
                .forEach(file -> {
                    if (!file.delete()) {
                        file.deleteOnExit();
                    }
                });
    }

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ArchiveCommand(new Filename("")), model, ArchiveCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        assertCommandSuccess(new ArchiveCommand(new Filename("")), model, ArchiveCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_failure() {
        Model model = new ModelManagerStubThrowingIoException();
        Model expectedModel = new ModelManagerStubThrowingIoException();

        assertCommandSuccess(new ArchiveCommand(new Filename("")), model, ArchiveCommand.MESSAGE_FAILURE,
                expectedModel);
    }

    @Test
    public void equals() {
        ArchiveCommand archiveFirstCommand = new ArchiveCommand(new Filename("test1"));
        ArchiveCommand archiveSecondCommand = new ArchiveCommand(new Filename("test2"));

        // same object -> returns true
        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        // same values -> returns true
        ArchiveCommand archiveFirstCommandCopy = new ArchiveCommand(new Filename("test1"));
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveFirstCommand.equals("test1"));

        // null -> returns false
        assertFalse(archiveFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));
    }

    /**
     * A ModelManager stub that always throws IOException when archiveAddressBook() is called.
     */
    private static class ModelManagerStubThrowingIoException extends ModelManager {
        @Override
        public void archiveAddressBook(Filename filename) throws IOException {
            throw new IOException();
        }
    }
}
