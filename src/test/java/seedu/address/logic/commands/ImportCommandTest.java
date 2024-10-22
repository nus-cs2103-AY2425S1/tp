package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleAssignmentsUtil;

public class ImportCommandTest {
    private final Model model = new ModelManager(
        getTypicalAddressBook(),
        new UserPrefs(),
        SampleAssignmentsUtil.getSamplePredefinedAssignments());


    @Test
    public void validImportCommandExecution_success() throws CommandException {
        ImportCommand command = new ImportCommand(
            "C:\\Users\\User\\Documents\\tp\\src\\test\\data\\testImport.csv");
        CommandResult commandResult = command.execute(model);
        assertEquals("Successfully imported 1 persons.", commandResult.getFeedbackToUser());
    }

    @Test
    public void invalidImportCommandExecution_fail() {
        ImportCommand command = new ImportCommand(
            "C:\\Users\\User\\Documents\\tp\\src\\test\\data\\testImp.csv");
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void invalidImportCsvExecution_fail() {
        ImportCommand command = new ImportCommand(
            "C:\\Users\\User\\Documents\\tp\\src\\test\\data\\invalidImport.csv");
        assertThrows(CommandException.class, () -> command.execute(model));
    }


}
