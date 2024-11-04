package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PHYSICS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_SCORE_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_SCORE_MATH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.logic.commands.EditAssignmentCommand.MESSAGE_EDIT_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBookWithAssignments;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.student.Student;
import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;
import seedu.address.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditAssignmentCommand.
 */
public class EditAssignmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithAssignments(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Assignment editedAssignment = new AssignmentBuilder().withAssignmentName(VALID_ASSIGNMENT_NAME_CS)
                        .withMaxScore(VALID_MAX_SCORE_CS).build();
        Student studentToEdit = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(INDEX_FIRST_STUDENT,
                INDEX_FIRST_ASSIGNMENT,
                new EditAssignmentDescriptorBuilder(editedAssignment).build());
        Student editedStudent = new StudentBuilder(studentToEdit).buildWithAssignment()
                .updateAssignment(INDEX_FIRST_ASSIGNMENT,
                editedAssignment);
        String expectedMessage = String.format(MESSAGE_EDIT_SUCCESS, INDEX_FIRST_ASSIGNMENT.getOneBased(),
                editedStudent.getName().fullName, editedAssignment.getName(), editedAssignment.getMaxScore());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased()), editedStudent);
        expectedModel.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        assertCommandSuccess(editAssignmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Assignment editedAssignment = new AssignmentBuilder().withAssignmentName(VALID_ASSIGNMENT_NAME_CS).build();
        Index indexLastStudent = Index.fromOneBased(model.getFilteredStudentList().size());
        Student lastStudent = model.getFilteredStudentList().get(indexLastStudent.getZeroBased());
        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder(editedAssignment).build();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(
                Index.fromOneBased(model.getFilteredStudentList().size()),
                INDEX_SECOND_ASSIGNMENT,
                descriptor);
        Student editedStudent = new StudentBuilder(lastStudent).buildWithAssignment()
                .updateAssignment(INDEX_SECOND_ASSIGNMENT, editedAssignment);

        String expectedMessage = String.format(MESSAGE_EDIT_SUCCESS, INDEX_SECOND_ASSIGNMENT.getOneBased(),
                lastStudent.getName().fullName, editedAssignment.getName(), editedAssignment.getMaxScore());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(indexLastStudent.getZeroBased()), editedStudent);
        expectedModel.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        assertCommandSuccess(editAssignmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(INDEX_FIRST_STUDENT,
                INDEX_FIRST_ASSIGNMENT,
                new EditAssignmentCommand.EditAssignmentDescriptor());
        Student editedStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        String expectedMessage = String.format(MESSAGE_EDIT_SUCCESS, INDEX_FIRST_ASSIGNMENT.getOneBased(),
                editedStudent.getName().fullName,
                editedStudent.getAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased()).getName(),
                editedStudent.getAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased()).getMaxScore());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editAssignmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Student studentInFilteredList = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Assignment assignmentToEdit = studentInFilteredList.getAssignmentList()
                .get(INDEX_FIRST_ASSIGNMENT.getZeroBased());
        Assignment editedAssignment = new AssignmentBuilder(assignmentToEdit)
                .withAssignmentName(VALID_ASSIGNMENT_NAME_MATH).withMaxScore(VALID_MAX_SCORE_MATH).build();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(INDEX_FIRST_STUDENT,
                INDEX_FIRST_ASSIGNMENT,
                new EditAssignmentDescriptorBuilder().withAssignmentName(new AssignmentName(VALID_ASSIGNMENT_NAME_MATH))
                        .withMaxScore(VALID_MAX_SCORE_MATH).build());

        Student editedStudent = new StudentBuilder(studentInFilteredList).buildWithAssignment()
                .updateAssignment(INDEX_FIRST_ASSIGNMENT, editedAssignment);
        String expectedMessage = String.format(MESSAGE_EDIT_SUCCESS, INDEX_FIRST_ASSIGNMENT.getOneBased(),
                studentInFilteredList.getName().fullName,
                editedAssignment.getName(),
                editedAssignment.getMaxScore());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), editedStudent);

        assertCommandSuccess(editAssignmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateAssignmentUnfilteredList_failure() {
        Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Assignment firstAssignment = firstStudent.getAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased());
        Assignment secondAssignment = firstStudent.getAssignmentList().get(INDEX_SECOND_ASSIGNMENT.getZeroBased());
        EditAssignmentCommand.EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder(firstAssignment)
                .withAssignmentName(secondAssignment.getAssignmentName())
                .withMaxScore(secondAssignment.getMaxScore()).build();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(INDEX_FIRST_STUDENT,
                INDEX_FIRST_ASSIGNMENT, descriptor);

        assertCommandFailure(editAssignmentCommand, model, EditAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT);
    }

    @Test
    public void execute_duplicateAssignmentFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Student studentInList = model.getAddressBook().getStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Assignment firstAssignment = studentInList.getAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased());
        Assignment secondAssignment = studentInList.getAssignmentList().get(INDEX_SECOND_ASSIGNMENT.getZeroBased());
        EditAssignmentCommand.EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder(firstAssignment)
                .withAssignmentName(secondAssignment.getAssignmentName())
                .withMaxScore(secondAssignment.getMaxScore()).build();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(INDEX_FIRST_STUDENT,
                INDEX_FIRST_ASSIGNMENT, descriptor);

        assertCommandFailure(editAssignmentCommand, model, EditAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        EditAssignmentCommand.EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder().build();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(outOfBoundIndex,
                INDEX_FIRST_ASSIGNMENT, descriptor);

        assertCommandFailure(editAssignmentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidStudentIndexFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);
        Index outOfBoundIndex = INDEX_SECOND_STUDENT;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStudentList().size());

        EditAssignmentCommand.EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder().build();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(outOfBoundIndex,
                INDEX_FIRST_ASSIGNMENT, descriptor);

        assertCommandFailure(editAssignmentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidAssignmentIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList()
                .get(INDEX_FIRST_STUDENT.getZeroBased())
                .getAssignmentList().size() + 1);
        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder().withAssignmentName(new AssignmentName(VALID_ASSIGNMENT_NAME_MATH))
                        .withMaxScore(VALID_MAX_SCORE_MATH).build();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(INDEX_FIRST_STUDENT, outOfBoundIndex,
                descriptor);

        assertCommandFailure(editAssignmentCommand, model, Messages.MESSAGE_INVALID_ASSIGNMENT_INDEX);
    }

    @Test
    public void equals() {
        final EditAssignmentCommand standardCommand = new EditAssignmentCommand(INDEX_FIRST_STUDENT,
                INDEX_FIRST_ASSIGNMENT, DESC_MATH);
        // same values -> returns true
        EditAssignmentCommand.EditAssignmentDescriptor copyDescriptor =
                new EditAssignmentCommand.EditAssignmentDescriptor(DESC_MATH);
        EditAssignmentCommand commandWithSameValues = new EditAssignmentCommand(INDEX_FIRST_STUDENT,
                INDEX_FIRST_ASSIGNMENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditAssignmentCommand(INDEX_FIRST_STUDENT,
                INDEX_SECOND_ASSIGNMENT, DESC_MATH)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditAssignmentCommand(INDEX_FIRST_STUDENT,
                INDEX_SECOND_ASSIGNMENT, DESC_PHYSICS)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditAssignmentCommand.EditAssignmentDescriptor editAssignmentDescriptor =
                new EditAssignmentCommand.EditAssignmentDescriptor();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(index, index, editAssignmentDescriptor);
        String expected = EditAssignmentCommand.class.getCanonicalName() + "{studentIndex=" + index + ", "
                + "assignmentIndex="
                + index
                + ", editAssignmentDescriptor="
                + editAssignmentDescriptor + "}";
        assertEquals(expected, editAssignmentCommand.toString());
    }
}
