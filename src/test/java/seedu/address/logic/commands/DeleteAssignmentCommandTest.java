package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAssignments.MATH_ASSIGNMENT_SUBMITTED;
import static seedu.address.testutil.TypicalAssignments.SCIENCE_ASSIGNMENT_GRADED;
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
        Student hughCopy = new StudentBuilder(HUGH).build();
        expectedModel.addStudent(hughCopy);
        HUGH.addAssignment(MATH_ASSIGNMENT_SUBMITTED);

        model.addStudent(HUGH);
        assertCommandSuccess(new DeleteAssignmentCommand(HUGH.getName(),
                        new AssignmentQuery(MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), null, null,
                                null, null)),
                model,
                String.format(DeleteAssignmentCommand.MESSAGE_SUCCESS,
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), HUGH.getName()),
                expectedModel);
    }

    @Test
    public void undo_after_execute() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Student hughCopy = new StudentBuilder(HUGH).build();
        hughCopy.addAssignment(MATH_ASSIGNMENT_SUBMITTED);
        expectedModel.addStudent(HUGH);

        model.addStudent(hughCopy);
        DeleteAssignmentCommand deleteAssignmentCommand = new DeleteAssignmentCommand(HUGH.getName(),
                new AssignmentQuery(MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), null, null,
                        null, null));
        assertCommandSuccess(deleteAssignmentCommand, model,
                String.format(DeleteAssignmentCommand.MESSAGE_SUCCESS,
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), HUGH.getName()),
                expectedModel);

        assertTrue(deleteAssignmentCommand.undo(model));
        assertTrue(hughCopy.getAssignments().size() == 1);
        assertTrue(hughCopy.getAssignments().get(0).equals(MATH_ASSIGNMENT_SUBMITTED));
    }

    @Test
    public void undo_before_execute() {
        Student hughCopy = new StudentBuilder(HUGH).build();
        hughCopy.addAssignment(MATH_ASSIGNMENT_SUBMITTED);
        hughCopy.addAssignment(SCIENCE_ASSIGNMENT_GRADED);

        model.addStudent(hughCopy);
        DeleteAssignmentCommand deleteAssignmentCommand = new DeleteAssignmentCommand(hughCopy.getName(),
                new AssignmentQuery(MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), null, null,
                        null, null));
        assertFalse(deleteAssignmentCommand.undo(model));
        assertTrue(hughCopy.getAssignments().size() == 2); // No change to the assignments list
    }
}
