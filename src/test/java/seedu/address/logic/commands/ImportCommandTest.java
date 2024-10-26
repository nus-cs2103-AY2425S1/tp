package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Contains unit tests for {@code ImportCommand}.
 */
public class ImportCommandTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvImportTest");
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Disabled
    @Test
    public void execute_import_success() throws CommandException {
        Path typicalPersons = TEST_DATA_FOLDER.resolve("typicalPersonsCsv.csv");
        ImportCommand importCommand = new ImportCommand(typicalPersons.toString());
        expectedModel.addPerson(ALICE);
        expectedModel.commitAddressBook();
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, 1);

        assertCommandSuccess(importCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_import_noPersonsAdded() throws CommandException {
        Path failedPersons = TEST_DATA_FOLDER.resolve("failedPersonsCsv.csv");
        ImportCommand importCommand = new ImportCommand(failedPersons.toString());
        String expectedMessage = String.format(ImportCommand.MESSAGE_FAILED, "[1]");

        assertCommandSuccess(importCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Disabled
    @Test
    public void execute_import_duplicatePersons() throws CommandException {
        Path duplicatePersons = TEST_DATA_FOLDER.resolve("duplicatePersonsCsv.csv");
        ImportCommand importCommand = new ImportCommand(duplicatePersons.toString());
        expectedModel.addPerson(ALICE);
        expectedModel.commitAddressBook();
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, 1) + " "
                + String.format(ImportCommand.MESSAGE_FAILED, "[2]");

        assertCommandSuccess(importCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
