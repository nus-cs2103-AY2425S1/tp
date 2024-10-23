package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ExportCommand}.
 */
public class ExportCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute_validPathNameUnfilteredList_success() throws IOException {
        String validPathName = "/Users/prishaprakash/tp/src/test/data/ExportCommandTest/validExportFile.txt";
        Files.deleteIfExists(Path.of(validPathName));
        File exportFile = new File(validPathName);
        File expectedExport = new File(
                "/Users/prishaprakash/tp/src/test/data/ExportCommandTest/expectedExportUnfilteredList.txt");
        ExportCommand exportCommand = new ExportCommand(exportFile);

        String expectedMessage = ExportCommand.MESSAGE_SUCCESS + exportFile.getAbsolutePath();

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(exportCommand, model, expectedMessage, expectedModel);
        Long match = Files.mismatch(exportFile.toPath(), expectedExport.toPath());
        assertEquals(-1L, match);
    }
    @Test
    public void execute_invalidPathNameUnfilteredList_throwsCommandException() {
        String invalidPathName = "";
        File exportFile = new File(invalidPathName);

        ExportCommand exportCommand = new ExportCommand(exportFile);

        assertCommandFailure(exportCommand, model, ExportCommand.FILE_WRITE_ERROR);
    }

    @Test
    public void execute_validPathNameFilteredList_success() throws IOException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        String validPathName = "/Users/prishaprakash/tp/src/test/data/ExportCommandTest/validExportFile.txt";
        Files.deleteIfExists(Path.of(validPathName));
        File exportFile = new File(validPathName);
        File expectedExport = new File(
                "/Users/prishaprakash/tp/src/test/data/ExportCommandTest/expectedExportFilteredList.txt");
        ExportCommand exportCommand = new ExportCommand(exportFile);

        String expectedMessage = ExportCommand.MESSAGE_SUCCESS + exportFile.getAbsolutePath();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertCommandSuccess(exportCommand, model, expectedMessage, expectedModel);
        Long match = Files.mismatch(exportFile.toPath(), expectedExport.toPath());
        assertEquals(-1L, match);
    }

    @Test
    public void execute_invalidPathNameFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        String invalidPathName = "";
        File exportFile = new File(invalidPathName);

        ExportCommand exportCommand = new ExportCommand(exportFile);

        assertCommandFailure(exportCommand, model, ExportCommand.FILE_WRITE_ERROR);
    }

    @Test
    public void equals() {
        String firstValidPathName = "/Users/prishaprakash/tp/src/test/data/ExportCommandTest/validExportFile.txt";
        String secondValidPathName = "/Users/prishaprakash/tp/src/test/data/ExportCommandTest/validExportFile2.txt";
        File firstExportFile = new File(firstValidPathName);
        File secondExportFile = new File(secondValidPathName);
        ExportCommand exportFirstCommand = new ExportCommand(firstExportFile);
        ExportCommand exportSecondCommand = new ExportCommand(secondExportFile);

        // same object -> returns true
        assertTrue(exportFirstCommand.equals(exportFirstCommand));

        // same values -> returns true
        ExportCommand exportFirstCommandCopy = new ExportCommand(firstExportFile);
        assertTrue(exportFirstCommand.equals(exportFirstCommandCopy));

        // different types -> returns false
        assertFalse(exportFirstCommand.equals(1));

        // null -> returns false
        assertFalse(exportFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(exportFirstCommand.equals(exportSecondCommand));
    }

    @Test
    public void toStringMethod() {
        String validPathName = "/Users/prishaprakash/tp/src/test/data/ExportCommandTest/validExportFile.txt";
        File exportFile = new File(validPathName);
        ExportCommand exportCommand = new ExportCommand(exportFile);
        String expected = ExportCommand.class.getCanonicalName() + "{exportFile=" + exportFile + "}";
        assertEquals(expected, exportCommand.toString());
    }
}
