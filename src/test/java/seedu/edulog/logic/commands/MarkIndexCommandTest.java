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
 * {@code MarkCommand}.
 */
public class MarkIndexCommandTest {

    private Model model = new ModelManager(getTypicalEduLog(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Student studentToMark = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        MarkCommand markCommand = new MarkIndexCommand(INDEX_FIRST_STUDENT);

        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_STUDENT_SUCCESS,
                Messages.format(studentToMark));

        ModelManager expectedModel = new ModelManager(model.getEduLog(), new UserPrefs());
        expectedModel.markStudent(studentToMark);

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        MarkCommand markCommand = new MarkIndexCommand(outOfBoundIndex);

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Student studentToMark = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        MarkCommand markCommand = new MarkIndexCommand(INDEX_FIRST_STUDENT);

        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_STUDENT_SUCCESS,
                Messages.format(studentToMark));

        Model expectedModel = new ModelManager(model.getEduLog(), new UserPrefs());
        expectedModel.markStudent(studentToMark);
        showStudentAtIndex(expectedModel, INDEX_FIRST_STUDENT);

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Index outOfBoundIndex = INDEX_SECOND_STUDENT;
        // ensures that outOfBoundIndex is still in bounds of edulog book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEduLog().getStudentList().size());

        MarkCommand markCommand = new MarkIndexCommand(outOfBoundIndex);

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MarkCommand markFirstCommand = new MarkIndexCommand(INDEX_FIRST_STUDENT);
        MarkCommand markSecondCommand = new MarkIndexCommand(INDEX_SECOND_STUDENT);

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        MarkCommand markFirstCommandCopy = new MarkIndexCommand(INDEX_FIRST_STUDENT);
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        // different types -> returns false
        assertFalse(markFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        MarkIndexCommand markCommand = new MarkIndexCommand(targetIndex);
        String expected = MarkIndexCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, markCommand.toString());
    }
}
