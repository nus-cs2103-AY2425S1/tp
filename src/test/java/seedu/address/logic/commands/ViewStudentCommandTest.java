package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class ViewStudentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        ViewStudentCommand viewStudentCommand = new ViewStudentCommand(INDEX_FIRST_STUDENT);

        String expectedMessage = String.format(ViewStudentCommand.MESSAGE_VIEW_STUDENT_SUCCESS,
                INDEX_FIRST_STUDENT.getOneBased());

        assertCommandSuccess(viewStudentCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        ViewStudentCommand viewStudentCommand = new ViewStudentCommand(outOfBoundIndex);

        assertCommandFailure(viewStudentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        ViewStudentCommand viewStudentCommand = new ViewStudentCommand(INDEX_FIRST_STUDENT);

        String expectedMessage = String.format(ViewStudentCommand.MESSAGE_VIEW_STUDENT_SUCCESS,
                INDEX_FIRST_STUDENT.getOneBased());

        assertCommandSuccess(viewStudentCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Index outOfBoundIndex = INDEX_SECOND_STUDENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStudentList().size());

        ViewStudentCommand viewStudentCommand = new ViewStudentCommand(outOfBoundIndex);

        assertCommandFailure(viewStudentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewStudentCommand viewFirstCommand = new ViewStudentCommand(INDEX_FIRST_STUDENT);
        ViewStudentCommand viewSecondCommand = new ViewStudentCommand(INDEX_SECOND_STUDENT);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewStudentCommand viewFirstCommandCopy = new ViewStudentCommand(INDEX_FIRST_STUDENT);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ViewStudentCommand viewStudentCommand = new ViewStudentCommand(targetIndex);
        String expected = ViewStudentCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, viewStudentCommand.toString());
    }

}
