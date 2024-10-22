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

public class ExportCommandTest {
    private final Model model = new ModelManager(
        getTypicalAddressBook(),
        new UserPrefs(),
        SampleAssignmentsUtil.getSamplePredefinedAssignments());


    @Test
    public void validImportCommandExecution_success() throws CommandException {
        ExportCommand command = new ExportCommand(
            "C:\\Users\\User\\Documents\\tp\\src\\test\\data\\testExport.csv");
        CommandResult commandResult = command.execute(model);
        assertEquals("Exported " + getTypicalAddressBook().getPersonList().size()
            + " persons to CSV.", commandResult.getFeedbackToUser());
    }

    @Test
    public void invalidExportCommandExecution_fail() {
        ImportCommand command = new ImportCommand(
            "C:\\Users\\User\\Documents\\tp\\src\\test\\data\\testImp.xxx");
        assertThrows(CommandException.class, () -> command.execute(model));
    }


}
