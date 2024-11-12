package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_NEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIALID_NONEXISTENT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTutorials.getTypicalTutorialList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.AssignmentList;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialId;
import seedu.address.model.tut.TutorialList;
import seedu.address.testutil.EditStudentDescriptorBuilder;
import seedu.address.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
                new AssignmentList(), getTypicalTutorialList());

        // Assign students to their tutorials in the model
        for (Student student : model.getAddressBook().getStudentList()) {
            TutorialId tutorialId = student.getTutorialId();
            if (model.hasTutorial(tutorialId)) {
                model.assignStudent(student, tutorialId);
            }
        }
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Student editedStudent = new StudentBuilder().build();
        EditCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(editedStudent).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_STUDENT, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
                Messages.format(editedStudent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new AssignmentList(), new TutorialList());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), editedStudent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_STUDENT, new EditCommand.EditStudentDescriptor());
        Student editedStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
                Messages.format(editedStudent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new AssignmentList(), new TutorialList());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Student studentInFilteredList = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student editedStudent = new StudentBuilder(studentInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_STUDENT,
                new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
                Messages.format(editedStudent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new AssignmentList(), new TutorialList());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), editedStudent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        EditCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidStudentIndexFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);
        Index outOfBoundIndex = INDEX_SECOND_STUDENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStudentList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_editStudentIdUnfilteredList_success() {
        Index indexFirstStudent = Index.fromOneBased(1);
        Student firstStudent = model.getFilteredStudentList().get(indexFirstStudent.getZeroBased());

        StudentBuilder studentInList = new StudentBuilder(firstStudent);
        Student editedStudent = studentInList.withStudentId(VALID_STUDENTID_NEW).build();

        EditCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder()
                .withStudentId(VALID_STUDENTID_NEW).build();
        EditCommand editCommand = new EditCommand(indexFirstStudent, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
                Messages.format(editedStudent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new AssignmentList(), new TutorialList());
        expectedModel.setStudent(firstStudent, editedStudent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editDuplicateStudentIdUnfilteredList_failure() {
        Index indexFirstStudent = Index.fromOneBased(1);
        Index indexSecondStudent = Index.fromOneBased(2);
        Student firstStudent = model.getFilteredStudentList().get(indexFirstStudent.getZeroBased());
        Student secondStudent = model.getFilteredStudentList().get(indexSecondStudent.getZeroBased());

        // Attempt to change first student's id to second student's id
        EditCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder()
                .withStudentId(secondStudent.getStudentId().value).build();
        EditCommand editCommand = new EditCommand(indexFirstStudent, descriptor);

        String expectedMessage = EditCommand.MESSAGE_DUPLICATE_STUDENTID;

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_editNonExistentTutorialIdUnfilteredList_failure() {
        Index indexFirstStudent = Index.fromOneBased(1);

        // Use a tutorial id that does not exist in the model
        String nonExistentTutorialId = VALID_TUTORIALID_NONEXISTENT;

        EditCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder()
                .withTutorialId(nonExistentTutorialId).build();
        EditCommand editCommand = new EditCommand(indexFirstStudent, descriptor);

        String expectedMessage = EditCommand.MESSAGE_TUTORIAL_NOT_FOUND + nonExistentTutorialId;

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_STUDENT, DESC_AMY);

        // same values -> returns true
        EditCommand.EditStudentDescriptor copyDescriptor = new EditStudentDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_STUDENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_STUDENT, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_STUDENT, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditCommand.EditStudentDescriptor editStudentDescriptor = new EditCommand.EditStudentDescriptor();
        EditCommand editCommand = new EditCommand(index, editStudentDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editStudentDescriptor="
                + editStudentDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

    @Test
    public void execute_editWithoutChangingTutorialId_unassignNotCalled() {
        Index indexFirstStudent = Index.fromOneBased(1);
        Student firstStudent = model.getFilteredStudentList().get(indexFirstStudent.getZeroBased());

        // Create an edited student without changing tutorial ID
        Student editedStudent = new StudentBuilder(firstStudent).withName(VALID_NAME_BOB).build();

        // Create the descriptor with only the name changed
        EditCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();

        // Create the EditCommand
        EditCommand editCommand = new EditCommand(indexFirstStudent, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
                Messages.format(editedStudent));

        // Expected model remains the same except for the student's name
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new AssignmentList(), getTypicalTutorialList());
        expectedModel.setStudent(firstStudent, editedStudent);

        // No need to unassign or assign since tutorial ID hasn't changed
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

}
