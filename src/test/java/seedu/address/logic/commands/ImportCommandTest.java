package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Name;

/**
 * Contains unit tests for {@code ImportCommand}.
 */
public class ImportCommandTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvImportTest");
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

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
        StringBuilder expectedMessageBuilder = new StringBuilder();
        expectedMessageBuilder.append(String.format(ImportCommand.MESSAGE_SUCCESS, 0));
        expectedMessageBuilder.append(String.format(ImportCommand.MESSAGE_FAILED, "Row 1: "
                + Name.MESSAGE_CONSTRAINTS + ". "));
        String expectedMessage = expectedMessageBuilder.toString();

        assertCommandSuccess(importCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_import_duplicatePersons() throws CommandException {
        Path duplicatePersons = TEST_DATA_FOLDER.resolve("duplicatePersonsCsv.csv");
        ImportCommand importCommand = new ImportCommand(duplicatePersons.toString());
        expectedModel.addPerson(ALICE);
        expectedModel.commitAddressBook();
        StringBuilder expectedMessageBuilder = new StringBuilder();
        expectedMessageBuilder.append(String.format(ImportCommand.MESSAGE_SUCCESS, 1));
        expectedMessageBuilder.append(String.format(ImportCommand.MESSAGE_DUPLICATES, "[2]"));
        String expectedMessage = expectedMessageBuilder.toString();

        assertCommandSuccess(importCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Path typicalPersons = TEST_DATA_FOLDER.resolve("typicalPersonsCsv.csv");
        Path failedPersons = TEST_DATA_FOLDER.resolve("failedPersonsCsv.csv");
        ImportCommand importCommand = new ImportCommand(typicalPersons.toString());
        ImportCommand otherImportCommand = new ImportCommand(failedPersons.toString());

        // same object -> returns true
        assertTrue(importCommand.equals(importCommand));

        // same values -> returns true
        ImportCommand importCommandCopy = new ImportCommand(typicalPersons.toString());
        assertTrue(importCommand.equals(importCommandCopy));

        // different types -> returns false
        assertFalse(importCommand.equals(1));

        // null -> returns false
        assertFalse(importCommand.equals(null));

        // different import command -> returns false
        assertFalse(importCommand.equals(otherImportCommand));
    }
}
