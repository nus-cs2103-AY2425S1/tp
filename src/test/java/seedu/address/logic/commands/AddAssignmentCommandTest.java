package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.student.Student;
import seedu.address.testutil.AssignmentBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AddAssignmentCommand}.
 */
public class AddAssignmentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullStudentAndAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAssignmentCommand(null, null));
    }

    @Test
    public void execute_assignmentAcceptedByModel_addSuccessful() throws Exception {
        Student studentToAssignAssignmentTo = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Assignment assignmentToAdd = new AssignmentBuilder(studentToAssignAssignmentTo).build();
        AddAssignmentCommand.AssignmentDescriptor toAddDescriptor =
                new AddAssignmentCommand.AssignmentDescriptor(AssignmentBuilder.DEFAULT_MAXSCORE,
                        new AssignmentName(AssignmentBuilder.DEFAULT_ASSIGNMENTNAME));
        CommandResult commandResult = new AddAssignmentCommand(INDEX_FIRST_STUDENT, toAddDescriptor).execute(model);

        assertEquals(String.format(AddAssignmentCommand.MESSAGE_SUCCESS, Messages.format(assignmentToAdd)),
                commandResult.getFeedbackToUser());
        // To add more here
    }
}
