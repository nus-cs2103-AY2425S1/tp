package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.ImportCommand.CORRECT_HEADER_USAGE;
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
    public void validImportCommandResult_success() throws CommandException {
        String projectDir = System.getProperty("user.dir");
        String filePath = projectDir + "/src/test/data/valid.csv";
        ImportCommand command = new ImportCommand(filePath);
        CommandResult commandResult = command.execute(model);
        assertEquals("Successfully imported 3 persons.", commandResult.getFeedbackToUser());
    }

    @Test
    public void invalidCsvHeaderExecution_fail() {
        String projectDir = System.getProperty("user.dir");
        String filePath = projectDir + "/src/test/data/invalidHeader.csv";
        ImportCommand command = new ImportCommand(filePath);
        String expectedMsg = "Error reading from the CSV file: " + "Header is defined incorrectly!\n"
            + CORRECT_HEADER_USAGE;
        assertCommandFailure(command, model, expectedMsg);
    }

    @Test
    public void extraCsvHeaders_fail() {
        String projectDir = System.getProperty("user.dir");
        String filePath = projectDir + "/src/test/data/extraHeader.csv";
        ImportCommand command = new ImportCommand(filePath);
        String expectedMsg = "Error reading from the CSV file: " + "There is an extra column!\n"
            + "Please ensure there is only be 8 corresponding header/data columns\n" + CORRECT_HEADER_USAGE;
        assertCommandFailure(command, model, expectedMsg);
    }

    @Test
    public void missingCsvHeadersEntry_fail() {
        String projectDir = System.getProperty("user.dir");
        String filePath = projectDir + "/src/test/data/missingHeaderEntry.csv";
        ImportCommand command = new ImportCommand(filePath);
        String expectedMsg = "Error reading from the CSV file: "
            + "There are lesser columns in header than expected!\n" + CORRECT_HEADER_USAGE;
        assertCommandFailure(command, model, expectedMsg);
    }

    @Test
    public void missingCsvHeader_fail() {
        String projectDir = System.getProperty("user.dir");
        String filePath = projectDir + "/src/test/data/missingHeader.csv";
        ImportCommand command = new ImportCommand(filePath);
        String expectedMsg = "Error reading from the CSV file: "
            + "CSV header is empty/contains empty values, please ensure"
            + " all headers are valid.\n"
            + CORRECT_HEADER_USAGE;
        assertCommandFailure(command, model, expectedMsg);
    }


}
