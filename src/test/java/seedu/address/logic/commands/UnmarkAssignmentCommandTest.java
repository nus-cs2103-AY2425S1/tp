package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.Student;
import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.StudentBuilder;

public class UnmarkAssignmentCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs());

    @Test
    public void execute_validStudentAndAssignmentIndex_unmarkSuccessful() {
        Assignment assignment = new AssignmentBuilder()
                .withHasSubmitted(true)
                .build();
        Student student = new StudentBuilder().withAssignments(new ArrayList<>()).build().addAssignment(assignment);
        model.addStudent(student);
        UnmarkAssignmentCommand unmarkAssignmentCommand = new UnmarkAssignmentCommand(
                INDEX_FIRST_STUDENT, INDEX_FIRST_ASSIGNMENT);
        String expectedMessage = String.format(UnmarkAssignmentCommand.MESSAGE_UNMARK_SUCCESS,
                "Math Homework", student.getName().fullName);
        Model expectedModel = new ModelManager(new AddressBook(), model.getUserPrefs());
        Assignment expectedAssignment = new AssignmentBuilder()
                .build();
        Student expectedStudent = new StudentBuilder().build().addAssignment(expectedAssignment);
        expectedModel.addStudent(expectedStudent);
        assertCommandSuccess(unmarkAssignmentCommand, model, expectedMessage, expectedModel);
        assertEquals(false, student.getAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased()).getHasSubmitted());
        assertEquals(0, student.getAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased()).getScore());
    }

    @Test
    public void execute_invalidStudentIndex_throwsCommandException() {
        Assignment assignment = new AssignmentBuilder()
                .withHasSubmitted(true)
                .build();
        Student student = new StudentBuilder().withAssignments(new ArrayList<>()).build().addAssignment(assignment);
        model.addStudent(student);
        UnmarkAssignmentCommand unmarkAssignmentCommand = new UnmarkAssignmentCommand(
                Index.fromOneBased(2), INDEX_FIRST_ASSIGNMENT);
        assertCommandFailure(unmarkAssignmentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidAssignmentIndex_throwsCommandException() {
        Assignment assignment = new AssignmentBuilder()
                .withHasSubmitted(true)
                .build();
        Student student = new StudentBuilder().withAssignments(new ArrayList<>()).build().addAssignment(assignment);
        model.addStudent(student);
        UnmarkAssignmentCommand unmarkAssignmentCommand = new UnmarkAssignmentCommand(
                INDEX_FIRST_STUDENT, Index.fromOneBased(2));
        assertCommandFailure(unmarkAssignmentCommand, model, Messages.MESSAGE_INVALID_ASSIGNMENT_INDEX);
    }

    @Test
    public void equals() {
        UnmarkAssignmentCommand unmarkFirstCommand = new UnmarkAssignmentCommand(INDEX_FIRST_STUDENT,
                INDEX_FIRST_ASSIGNMENT);
        UnmarkAssignmentCommand unmarkSecondCommand = new UnmarkAssignmentCommand(INDEX_SECOND_STUDENT,
                INDEX_FIRST_ASSIGNMENT);

        // same object -> returns true
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));

        // same values -> returns true
        UnmarkAssignmentCommand unmarkFirstCommandCopy = new UnmarkAssignmentCommand(INDEX_FIRST_STUDENT,
                INDEX_FIRST_ASSIGNMENT);
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(unmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unmarkFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(unmarkFirstCommand.equals(unmarkSecondCommand));
    }

    @Test
    public void toStringMethod() {
        UnmarkAssignmentCommand unmarkAssignmentCommand = new UnmarkAssignmentCommand(INDEX_FIRST_STUDENT,
                INDEX_FIRST_ASSIGNMENT);
        String expected = UnmarkAssignmentCommand.class.getCanonicalName() + "{studentIndex=" + INDEX_FIRST_STUDENT
                + ", assignmentIndex=" + INDEX_FIRST_ASSIGNMENT + "}";
        assertEquals(expected, unmarkAssignmentCommand.toString());
    }

}
