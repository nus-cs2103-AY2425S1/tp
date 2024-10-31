package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPaths.DUPLICATE_IMPORT_FILE;
import static seedu.address.testutil.TypicalPaths.EMPTY_IMPORT_FILE;
import static seedu.address.testutil.TypicalPaths.INVALID_IMPORT_FILE;
import static seedu.address.testutil.TypicalPaths.INVALID_MISSING_NAME_FILE;
import static seedu.address.testutil.TypicalPaths.VALID_MISSING_CLASS_FILE;
import static seedu.address.testutil.TypicalPaths.VALID_MISSING_DATA_IMPORT_FILE;
import static seedu.address.testutil.TypicalPaths.VALID_MISSING_PHONE_FILE;
import static seedu.address.testutil.TypicalPaths.VALID_MISSING_TAGS_FILE;
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
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, 7), commandResult.getFeedbackToUser());
    }

    @Test
    public void executeImportSuccessfulMissingData() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CommandResult commandResult = new ImportCommand(VALID_MISSING_DATA_IMPORT_FILE).execute(model);

        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, 7), commandResult.getFeedbackToUser());
    }

    @Test
    public void executeImportInvalidMissingName() throws Exception {
        ImportCommand importCommand = new ImportCommand(INVALID_MISSING_NAME_FILE);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertThrows(CommandException.class,
                ImportCommand.MESSAGE_FILE_CORRUPTED, () -> importCommand.execute(model));
    }

    @Test
    public void executeImportSuccessfulMissingClass() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CommandResult commandResult = new ImportCommand(VALID_MISSING_CLASS_FILE).execute(model);
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, 7), commandResult.getFeedbackToUser());
    }

    @Test
    public void executeImportSuccessfulMissingPhone() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CommandResult commandResult = new ImportCommand(VALID_MISSING_PHONE_FILE).execute(model);
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, 7), commandResult.getFeedbackToUser());
    }

    @Test
    public void executeImportSuccessfulMissingTags() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CommandResult commandResult = new ImportCommand(VALID_MISSING_TAGS_FILE).execute(model);
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, 7), commandResult.getFeedbackToUser());
    }

    @Test
    public void executeImportInvalidEmptyCsv() throws Exception {
        ImportCommand importCommand = new ImportCommand(EMPTY_IMPORT_FILE);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertThrows(CommandException.class,
                ImportCommand.MESSAGE_FILE_CORRUPTED, () -> importCommand.execute(model));
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
        String msg = "Data imported with 1 students added."
            + "\n2 Duplicate person(s) found in file: [Shaun Lee, Shaun Lee]";
        assertThrows(CommandException.class, msg, () -> importCommand.execute(model));
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
}
