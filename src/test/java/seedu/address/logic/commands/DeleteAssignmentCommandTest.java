package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAssignments.MATH_ASSIGNMENT_SUBMITTED;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalStudents.HUGH;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.AssignmentQuery;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

public class DeleteAssignmentCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }
    @Test
    public void execute_deleteAssignment_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        HUGH.addAssignment(MATH_ASSIGNMENT_SUBMITTED);
        Student hughCopy = new StudentBuilder(HUGH).build();
        expectedModel.addStudent(hughCopy);

        model.addStudent(HUGH);
        assertCommandSuccess(new DeleteAssignmentCommand(HUGH.getName(),
                        new AssignmentQuery(MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), null, null,
                                null, null)),
                model,
                String.format(DeleteAssignmentCommand.MESSAGE_SUCCESS,
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), HUGH.getName()),
                expectedModel);
    }
}
