package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ImportCommandTest {
    private Model model;
    private Model expectedModel;

    // Initialize address book and user preferences
    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        expectedModel = new ModelManager(new AddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validJsonFile_success() {
        String validJsonContent = "{\"persons\": []}";
        ImportCommand importCommand = new ImportCommand(validJsonContent);

        CommandResult expected = new CommandResult(ImportCommand.MESSAGE_IMPORT_SUCCESS);
        assertCommandSuccess(importCommand, model, expected, expectedModel);
    }

    @Test
    public void execute_getFileContent_success() {
        String validJsonContent = "{\"persons\": []}";
        ImportCommand importCommand = new ImportCommand(validJsonContent);

        String retrievedJsonContent = importCommand.getFileContent();
        assertEquals(validJsonContent, retrievedJsonContent);
    }
}
