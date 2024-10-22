package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Set<Index> firstIndexSet = new HashSet<>();
        firstIndexSet.add(INDEX_FIRST_STUDENT);
        DeleteCommand deleteCommand = new DeleteCommand(firstIndexSet);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                Messages.format(studentToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleValidIndexUnfilteredList_success() {
        ArrayList<Student> studentToDelete = new ArrayList<>();
        studentToDelete.add(model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased()));
        studentToDelete.add(model.getFilteredStudentList().get(INDEX_SECOND_STUDENT.getZeroBased()));
        Set<Index> indexSet = new HashSet<>();
        indexSet.add(INDEX_FIRST_STUDENT);
        indexSet.add(INDEX_SECOND_STUDENT);
        DeleteCommand deleteCommand = new DeleteCommand(indexSet);

        String formattedDeletedPeople = studentToDelete.stream()
                .map(Messages::format)
                .collect(Collectors.joining("\n"));
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                formattedDeletedPeople);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        studentToDelete.forEach(expectedModel::deleteStudent);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allInvalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        Index outOfBoundIndex2 = Index.fromOneBased(model.getFilteredStudentList().size() + 2);
        Set<Index> outOfBoundIndexSet = new HashSet<>();
        outOfBoundIndexSet.add(outOfBoundIndex);
        outOfBoundIndexSet.add(outOfBoundIndex2);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndexSet);

        String formattedOutOfBoundIndices = outOfBoundIndex.getOneBased() + ", " + outOfBoundIndex2.getOneBased();


        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                formattedOutOfBoundIndices));
    }

    @Test
    public void execute_someOfManyInvalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        Index outOfBoundIndex2 = Index.fromOneBased(model.getFilteredStudentList().size() + 2);
        Set<Index> outOfBoundIndexSet = new HashSet<>();
        outOfBoundIndexSet.add(outOfBoundIndex);
        outOfBoundIndexSet.add(outOfBoundIndex2);
        outOfBoundIndexSet.add(INDEX_FIRST_STUDENT);
        outOfBoundIndexSet.add(INDEX_SECOND_STUDENT);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndexSet);

        String formattedOutOfBoundIndices = outOfBoundIndex.getOneBased() + ", " + outOfBoundIndex2.getOneBased();

        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                formattedOutOfBoundIndices));
    }

    @Test
    public void execute_oneOfOneInvalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        Set<Index> outOfBoundIndexSet = new HashSet<>();
        outOfBoundIndexSet.add(outOfBoundIndex);
        outOfBoundIndexSet.add(INDEX_FIRST_STUDENT);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndexSet);

        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                String.valueOf(outOfBoundIndex.getOneBased())));
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Set<Index> firstIndexSet = new HashSet<>();
        firstIndexSet.add(INDEX_FIRST_STUDENT);
        DeleteCommand deleteCommand = new DeleteCommand(firstIndexSet);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                Messages.format(studentToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);
        showNoStudent(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Index outOfBoundIndex = INDEX_SECOND_STUDENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStudentList().size());
        Set<Index> outOfBoundIndexSet = new HashSet<>();
        outOfBoundIndexSet.add(INDEX_SECOND_STUDENT);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndexSet);

        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                String.valueOf(outOfBoundIndex.getOneBased())));
    }

    @Test
    public void equals() {
        Set<Index> firstIndexSet = new HashSet<>();
        firstIndexSet.add(INDEX_FIRST_STUDENT);
        Set<Index> secondIndexSet = new HashSet<>();
        secondIndexSet.add(INDEX_SECOND_STUDENT);
        DeleteCommand deleteFirstCommand = new DeleteCommand(firstIndexSet);
        DeleteCommand deleteSecondCommand = new DeleteCommand(secondIndexSet);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        Set<Index> firstIndexSetCopy = new HashSet<>();
        firstIndexSetCopy.add(INDEX_FIRST_STUDENT);
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(firstIndexSetCopy);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Set<Index> targetIndexSet = new HashSet<>();
        targetIndexSet.add(INDEX_FIRST_STUDENT);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndexSet);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndices=" + targetIndexSet + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    @Test
    public void getCommandTypeMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Set<Index> targetIndexSet = new HashSet<>();
        targetIndexSet.add(INDEX_FIRST_STUDENT);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndexSet);
        assertEquals(deleteCommand.getCommandType(), CommandType.DELETESTUDENT);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoStudent(Model model) {
        model.updateFilteredStudentList(p -> false);

        assertTrue(model.getFilteredStudentList().isEmpty());
    }
}
