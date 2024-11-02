package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
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
    public void constructor_nullStudentAndAssignmentDescriptor_throwsNullPointerException() {
        assertThrows(AssertionError.class, () -> new AddAssignmentCommand(null, null));
    }

    @Test
    public void execute_validStudentIndexUnfilteredList_success() {
        AddAssignmentCommand.AssignmentDescriptor toAddDescriptor =
                new AddAssignmentCommand.AssignmentDescriptor(AssignmentBuilder.DEFAULT_MAX_SCORE,
                        new AssignmentName(AssignmentBuilder.DEFAULT_ASSIGNMENT_NAME));
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(INDEX_FIRST_STUDENT,
                toAddDescriptor);
        Student studentToAddAssignmentTo = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Assignment expectedAssignment = new AssignmentBuilder().build();
        Student studentWithAssignment = studentToAddAssignmentTo.addAssignment(expectedAssignment);

        String expectedMessage = String.format(AddAssignmentCommand.MESSAGE_SUCCESS,
                Messages.format(expectedAssignment));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setStudent(studentToAddAssignmentTo, studentWithAssignment);

        assertCommandSuccess(addAssignmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        AddAssignmentCommand.AssignmentDescriptor toAddDescriptor =
                new AddAssignmentCommand.AssignmentDescriptor(AssignmentBuilder.DEFAULT_MAX_SCORE,
                        new AssignmentName(AssignmentBuilder.DEFAULT_ASSIGNMENT_NAME));
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(outOfBoundIndex,
                toAddDescriptor);

        assertCommandFailure(addAssignmentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validStudentIndexFilteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        AddAssignmentCommand.AssignmentDescriptor toAddDescriptor =
                new AddAssignmentCommand.AssignmentDescriptor(AssignmentBuilder.DEFAULT_MAX_SCORE,
                        new AssignmentName(AssignmentBuilder.DEFAULT_ASSIGNMENT_NAME));
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(INDEX_FIRST_STUDENT,
                toAddDescriptor);
        Student studentToAddAssignmentTo = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Assignment expectedAssignment = new AssignmentBuilder().build();
        Student studentWithAssignment = studentToAddAssignmentTo.addAssignment(expectedAssignment);

        String expectedMessage = String.format(AddAssignmentCommand.MESSAGE_SUCCESS,
                Messages.format(expectedAssignment));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showStudentAtIndex(expectedModel, INDEX_FIRST_STUDENT);
        expectedModel.setStudent(studentToAddAssignmentTo, studentWithAssignment);

        assertCommandSuccess(addAssignmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentIndexFilteredList_throwsCommandException() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Index outOfBoundIndex = INDEX_SECOND_STUDENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStudentList().size());

        AddAssignmentCommand.AssignmentDescriptor toAddDescriptor =
                new AddAssignmentCommand.AssignmentDescriptor(AssignmentBuilder.DEFAULT_MAX_SCORE,
                        new AssignmentName(AssignmentBuilder.DEFAULT_ASSIGNMENT_NAME));
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(outOfBoundIndex,
                toAddDescriptor);

        assertCommandFailure(addAssignmentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateAssignment_throwsCommandException() throws Exception {
        AddAssignmentCommand.AssignmentDescriptor toAddDescriptor =
                new AddAssignmentCommand.AssignmentDescriptor(AssignmentBuilder.DEFAULT_MAX_SCORE,
                        new AssignmentName(AssignmentBuilder.DEFAULT_ASSIGNMENT_NAME));
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(INDEX_FIRST_STUDENT,
                toAddDescriptor);
        addAssignmentCommand.execute(model);

        AddAssignmentCommand.AssignmentDescriptor duplicateToAddDescriptor =
                new AddAssignmentCommand.AssignmentDescriptor(AssignmentBuilder.DEFAULT_MAX_SCORE,
                        new AssignmentName(AssignmentBuilder.DEFAULT_ASSIGNMENT_NAME));
        AddAssignmentCommand addAssignmentCommandCopy = new AddAssignmentCommand(INDEX_FIRST_STUDENT,
                duplicateToAddDescriptor);

        assertCommandFailure(addAssignmentCommandCopy, model, AddAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT);
    }

    @Test
    public void execute_assignmentNameExceedsLimit_throwsCommandException() throws Exception {
        String invalidAssignmentName = AssignmentBuilder
            .DEFAULT_ASSIGNMENT_NAME.repeat(AssignmentName.MAXIMUM_NAME_LENGTH);
        assertTrue(invalidAssignmentName.length() > AssignmentName.MAXIMUM_NAME_LENGTH);

        AddAssignmentCommand.AssignmentDescriptor toAddDescriptor =
                new AddAssignmentCommand.AssignmentDescriptor(AssignmentBuilder.DEFAULT_MAX_SCORE,
                        new AssignmentName(invalidAssignmentName));
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(INDEX_FIRST_STUDENT,
                toAddDescriptor);

        assertCommandFailure(addAssignmentCommand, model,
                String.format(AssignmentName.MESSAGE_NAME_TOO_LONG, AssignmentName.MAXIMUM_NAME_LENGTH));
    }

    @Test
    public void equalsDescriptor() {
        AddAssignmentCommand.AssignmentDescriptor toAddDescriptor =
                new AddAssignmentCommand.AssignmentDescriptor(AssignmentBuilder.DEFAULT_MAX_SCORE,
                        new AssignmentName(AssignmentBuilder.DEFAULT_ASSIGNMENT_NAME));

        // same object -> returns true
        assertTrue(toAddDescriptor.equals(toAddDescriptor));

        // same values -> returns true
        AddAssignmentCommand.AssignmentDescriptor toAddDescriptorCopy = new AddAssignmentCommand
                .AssignmentDescriptor(toAddDescriptor);
        assertTrue(toAddDescriptor.equals(toAddDescriptorCopy));

        // different types -> returns false
        assertFalse(toAddDescriptor.equals(1));

        // null -> returns false
        assertFalse(toAddDescriptor.equals(null));

        // different assignment -> returns false
        AddAssignmentCommand.AssignmentDescriptor toAddSecondDescriptor =
                new AddAssignmentCommand.AssignmentDescriptor(AssignmentBuilder.DEFAULT_MAX_SCORE,
                        new AssignmentName("NOT" + AssignmentBuilder.DEFAULT_ASSIGNMENT_NAME));
        assertFalse(toAddDescriptor.equals(toAddSecondDescriptor));
    }

    @Test
    public void equalsAssignment() {
        AddAssignmentCommand.AssignmentDescriptor toAddDescriptor =
                new AddAssignmentCommand.AssignmentDescriptor(AssignmentBuilder.DEFAULT_MAX_SCORE,
                        new AssignmentName(AssignmentBuilder.DEFAULT_ASSIGNMENT_NAME));
        AddAssignmentCommand addAssignmentFirstCommand = new AddAssignmentCommand(INDEX_FIRST_STUDENT,
                toAddDescriptor);

        // same object -> returns true
        assertTrue(addAssignmentFirstCommand.equals(addAssignmentFirstCommand));

        // same values -> returns true
        AddAssignmentCommand addAssignmentFirstCommandCopy = new AddAssignmentCommand(INDEX_FIRST_STUDENT,
                toAddDescriptor);
        assertTrue(addAssignmentFirstCommand.equals(addAssignmentFirstCommandCopy));

        // different types -> returns false
        assertFalse(addAssignmentFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addAssignmentFirstCommand.equals(null));

        // different assignment -> returns false
        AddAssignmentCommand addAssignmentSecondCommand = new AddAssignmentCommand(INDEX_SECOND_STUDENT,
                toAddDescriptor);
        assertFalse(addAssignmentFirstCommand.equals(addAssignmentSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        AddAssignmentCommand.AssignmentDescriptor toAddDescriptor =
                new AddAssignmentCommand.AssignmentDescriptor(AssignmentBuilder.DEFAULT_MAX_SCORE,
                        new AssignmentName(AssignmentBuilder.DEFAULT_ASSIGNMENT_NAME));
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(index,
                toAddDescriptor);
        String assignmentName = toAddDescriptor.getAssignmentName().toString();
        String assignmentMaxScore = toAddDescriptor.getScore().toString();
        String expected = AddAssignmentCommand.class.getCanonicalName() + "{index=" + index + ", toAddDescriptor="
                + toAddDescriptor + "}";
        assertEquals(expected, addAssignmentCommand.toString());
    }
}
