package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ImportCommandTest {
    private Model model;

    @Test
    public void executeImportSuccessfulNoDefects() throws Exception {
        Path projectPath = Path.of(System.getProperty("user.dir"));

        Path validPath = projectPath.resolve("src").resolve("test").resolve("data")
            .resolve("ImportCommandTest").resolve("valid_noDups_importFile.csv");
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CommandResult commandResult = new ImportCommand(validPath).execute(model);

        assertEquals(ImportCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
    }

    @Test
    public void executeImportSuccessfulMissingData() throws Exception {
        Path missingDataPath = Path.of("src/test/data/ImportCommandTest/valid_missing_data.csv");
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CommandResult commandResult = new ImportCommand(missingDataPath).execute(model);

        assertEquals(ImportCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());

    }

    @Test
    public void executeFileNotFoundFailure() {
        Path invalidPath = Path.of("src/test/data/ImportCommandTest/adf.csv");
        ImportCommand importCommand = new ImportCommand(invalidPath);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertThrows(CommandException.class,
            ImportCommand.MESSAGE_FILE_NOT_FOUND, () -> importCommand.execute(model));
    }

    @Test
    public void execute_parse_error() {
        Path invalidPath = Path.of("src/test/data/ImportCommandTest/invalid.csv");
        ImportCommand importCommand = new ImportCommand(invalidPath);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertThrows(CommandException.class,
            ImportCommand.MESSAGE_FILE_CORRUPTED, () -> importCommand.execute(model));
    }

    @Test
    public void execute_duplicatePersons_noAdditions() {
        Path duplicatePath = Path.of("src/test/data/ImportCommandTest/dups.csv");
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        int addressBookBeforeTestSize = model.getAddressBook().getPersonList().size();
        ImportCommand importCommand = new ImportCommand(duplicatePath);
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
        Path validPath = Path.of("src/test/data/ImportCommandTest/valid_noDups_importFile.csv");
        ImportCommand importCommand = new ImportCommand(validPath);
        assertEquals(ImportCommand.class.getCanonicalName() + "{filepath: =" + validPath + "}",
            importCommand.toString());
    }
}
