package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;


public class IncomeCommandTest {
    private final Model model = new ModelManager();
    @Test
    public void execute_emptyAddressBook_noPaidAmountNoOwedAmount() {
        CommandResult commandResult = new IncomeCommand().execute(model);
        assertEquals(commandResult.getFeedbackToUser(), Messages.getIncomeMessage(0, 0));
    }

    @Test
    public void execute_addStudent_showUpdatedPaidAmountAndOwedAmount() {
        Student validStudent = new StudentBuilder().build();
        model.addStudent(validStudent);
        CommandResult commandResult = new IncomeCommand().execute(model);

        assertEquals(commandResult.getFeedbackToUser(), Messages.getIncomeMessage(validStudent.getPaidAmountValue(),
                validStudent.getOwedAmountValue()));


    }
}
