package seedu.edulog.logic.commands;

import static seedu.edulog.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edulog.testutil.TypicalEdulog.getTypicalEduLogWithCopiedStudents;

import org.junit.jupiter.api.Test;

import seedu.edulog.model.Model;
import seedu.edulog.model.ModelManager;
import seedu.edulog.model.UserPrefs;



public class RevenueCommandTest {


    @Test
    public void execute_noHasPaidOptionPaid_successful() {
        RevenueCommand command = new PaidRevenueCommand();
        Model model = new ModelManager(getTypicalEduLogWithCopiedStudents(), new UserPrefs());
        model.unmarkAllStudents();

        // none of the student has paid
        String expectedMessage = String.format(RevenueCommand.MESSAGE_SUCCESS, "paid", 0);
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_allHasPaidOptionPaid_successful() {
        RevenueCommand command = new PaidRevenueCommand();
        Model model = new ModelManager(getTypicalEduLogWithCopiedStudents(), new UserPrefs());
        model.markAllStudents();

        String expectedMessage = String.format(RevenueCommand.MESSAGE_SUCCESS, "paid", 700);
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_noHasPaidOptionUnpaid_successful() {
        RevenueCommand command = new UnpaidRevenueCommand();
        Model model = new ModelManager(getTypicalEduLogWithCopiedStudents(), new UserPrefs());
        model.unmarkAllStudents();

        // none of the student has paid
        String expectedMessage = String.format(RevenueCommand.MESSAGE_SUCCESS, "unpaid", 700);
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_allHasPaidOptionUnpaid_successful() {
        RevenueCommand command = new UnpaidRevenueCommand();
        Model model = new ModelManager(getTypicalEduLogWithCopiedStudents(), new UserPrefs());
        model.markAllStudents();

        String expectedMessage = String.format(RevenueCommand.MESSAGE_SUCCESS, "unpaid", 0);
        assertCommandSuccess(command, model, expectedMessage, model);
    }
}
