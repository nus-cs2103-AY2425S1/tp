package seedu.edulog.logic.commands;

import static seedu.edulog.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edulog.testutil.TypicalEdulog.getTypicalEduLog;

import org.junit.jupiter.api.Test;

import seedu.edulog.model.Model;
import seedu.edulog.model.ModelManager;
import seedu.edulog.model.UserPrefs;



public class RevenueCommandTest {


    @Test
    public void execute_noHasPaid_successful() {
        RevenueCommand command = new RevenueCommand();
        Model model = new ModelManager(getTypicalEduLog(), new UserPrefs());
        model.unmarkAllStudents();

        // none of the student has paid
        String expectedMessage = String.format(RevenueCommand.COMMAND_SUCCESS, 0);
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_allHasPaid_successful() {
        RevenueCommand command = new RevenueCommand();
        Model model = new ModelManager(getTypicalEduLog(), new UserPrefs());
        model.markAllStudents();

        // all of the student has paid
        String expectedMessage = String.format(RevenueCommand.COMMAND_SUCCESS, 700);
        assertCommandSuccess(command, model, expectedMessage, model);
    }
}
