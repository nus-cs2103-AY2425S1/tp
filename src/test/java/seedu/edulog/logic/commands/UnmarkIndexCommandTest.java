package seedu.edulog.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edulog.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.edulog.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edulog.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.edulog.testutil.TypicalEdulog.getTypicalEduLog;
import static seedu.edulog.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.edulog.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.edulog.commons.core.index.Index;
import seedu.edulog.logic.Messages;
import seedu.edulog.model.Model;
import seedu.edulog.model.ModelManager;
import seedu.edulog.model.UserPrefs;
import seedu.edulog.model.student.Student;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnmarkCommand}.
 */
public class UnmarkIndexCommandTest {

    private Model model = new ModelManager(getTypicalEduLog(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Student studentToUnmark = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        UnmarkCommand unmarkCommand = new UnmarkIndexCommand(INDEX_FIRST_STUDENT);

        String expectedMessage = String.format(UnmarkCommand.MESSAGE_UNMARK_STUDENT_SUCCESS,
                Messages.format(studentToUnmark));

        ModelManager expectedModel = new ModelManager(model.getEduLog(), new UserPrefs());
        expectedModel.unmarkStudent(studentToUnmark);

        assertCommandSuccess(unmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        UnmarkCommand unmarkCommand = new UnmarkIndexCommand(outOfBoundIndex);

        assertCommandFailure(unmarkCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Student studentToUnmark = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        UnmarkCommand unmarkCommand = new UnmarkIndexCommand(INDEX_FIRST_STUDENT);

        String expectedMessage = String.format(UnmarkCommand.MESSAGE_UNMARK_STUDENT_SUCCESS,
                Messages.format(studentToUnmark));

        Model expectedModel = new ModelManager(model.getEduLog(), new UserPrefs());
        expectedModel.unmarkStudent(studentToUnmark);
        showStudentAtIndex(expectedModel, INDEX_FIRST_STUDENT);

        assertCommandSuccess(unmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Index outOfBoundIndex = INDEX_SECOND_STUDENT;
        // ensures that outOfBoundIndex is still in bounds of edulog book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEduLog().getStudentList().size());

        UnmarkCommand unmarkCommand = new UnmarkIndexCommand(outOfBoundIndex);

        assertCommandFailure(unmarkCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnmarkCommand unmarkFirstCommand = new UnmarkIndexCommand(INDEX_FIRST_STUDENT);
        UnmarkCommand unmarkSecondCommand = new UnmarkIndexCommand(INDEX_SECOND_STUDENT);

        // same object -> returns true
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));

        // same values -> returns true
        UnmarkCommand unmarkFirstCommandCopy = new UnmarkIndexCommand(INDEX_FIRST_STUDENT);
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
        Index targetIndex = Index.fromOneBased(1);
        UnmarkIndexCommand unmarkCommand = new UnmarkIndexCommand(targetIndex);
        String expected = UnmarkIndexCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unmarkCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoStudent(Model model) {
        model.updateFilteredStudentList(p -> false);

        assertTrue(model.getFilteredStudentList().isEmpty());
    }
}
