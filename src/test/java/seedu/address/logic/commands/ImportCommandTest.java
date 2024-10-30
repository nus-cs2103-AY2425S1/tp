package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPaths.DUPLICATE_IMPORT_FILE;
import static seedu.address.testutil.TypicalPaths.INVALID_IMPORT_FILE;
import static seedu.address.testutil.TypicalPaths.VALID_MISSING_DATA_IMPORT_FILE;
import static seedu.address.testutil.TypicalPaths.VALID_NO_DUPS_IMPORT_FILE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalPaths;

public class ImportCommandTest {
    private Model model;

    @Test
    public void executeImportSuccessfulNoDefects() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CommandResult commandResult = new ImportCommand(VALID_NO_DUPS_IMPORT_FILE).execute(model);
        assertEquals(ImportCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
    }

    @Test
    public void executeImportSuccessfulMissingData() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CommandResult commandResult = new ImportCommand(VALID_MISSING_DATA_IMPORT_FILE).execute(model);

        assertEquals(ImportCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());

    }

    @Test
    public void executeFileNotFoundFailure() {
        ImportCommand importCommand = new ImportCommand(TypicalPaths.getTypicalPath().resolve("adf.csv"));
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertThrows(CommandException.class,
            ImportCommand.MESSAGE_FILE_NOT_FOUND, () -> importCommand.execute(model));
    }

    @Test
    public void execute_parse_error() {
        ImportCommand importCommand = new ImportCommand(INVALID_IMPORT_FILE);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertThrows(CommandException.class,
            ImportCommand.MESSAGE_FILE_CORRUPTED, () -> importCommand.execute(model));
    }

    @Test
    public void execute_duplicatePersons_noAdditions() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        int addressBookBeforeTestSize = model.getAddressBook().getPersonList().size();
        ImportCommand importCommand = new ImportCommand(DUPLICATE_IMPORT_FILE);
        int addressBookAfterTestSize = model.getAddressBook().getPersonList().size();
        assertEquals(addressBookBeforeTestSize, addressBookAfterTestSize);
        assertThrows(CommandException.class,
            "Data imported.\nDuplicate persons found in file: [Shaun Lee, Shaun Lee]", () ->
                importCommand.execute(model));
    }

    @Test
    public void execute_csvError() {
        Path hybridPath = Path.of("src/test/data/ImportCommandTest/invalid.csv");
        ImportCommand importCommand = new ImportCommand(hybridPath);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertThrows(CommandException.class,
            ImportCommand.MESSAGE_FILE_CORRUPTED, () -> importCommand.execute(model));
    }

    @Test
    public void execute_toString() {
        ImportCommand importCommand = new ImportCommand(VALID_NO_DUPS_IMPORT_FILE);
        assertEquals(ImportCommand.class.getCanonicalName() + "{filepath: =" + VALID_NO_DUPS_IMPORT_FILE + "}",
            importCommand.toString());
    }

    @Test
    public void equals() {
        Path validPath = Path.of("src/test/data/ImportCommandTest/valid_noDups_importFile.csv");
        ImportCommand importCommand1 = new ImportCommand(validPath);
        ImportCommand importCommand2 = new ImportCommand(validPath);

        // same object
        assertEquals(importCommand1, importCommand1);

        // different object, same path
        assertEquals(importCommand1, importCommand2);

        // null
        assertNotEquals(importCommand1, null);

        // different object, different path
        Path differentPath = Path.of("src/test/data/ImportCommandTest/different.csv");
        ImportCommand importCommand3 = new ImportCommand(differentPath);
        assertNotEquals(importCommand1, importCommand3);
    }
}
