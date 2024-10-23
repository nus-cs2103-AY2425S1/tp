package seedu.edulog.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edulog.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.edulog.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edulog.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.edulog.testutil.TypicalEdulog.getTypicalEduLog;
import static seedu.edulog.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.edulog.testutil.TypicalNames.NAME_FIRST_STUDENT;
import static seedu.edulog.testutil.TypicalNames.NAME_SECOND_STUDENT;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.edulog.logic.Messages;
import seedu.edulog.model.Model;
import seedu.edulog.model.ModelManager;
import seedu.edulog.model.UserPrefs;
import seedu.edulog.model.student.Name;
import seedu.edulog.model.student.Student;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnmarkCommand}.
 */
public class UnmarkNameCommandTest {

    private Model model = new ModelManager(getTypicalEduLog(), new UserPrefs());

    @Test
    public void execute_validNameUnfilteredList_success() {
        Student studentToUnmark = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        UnmarkCommand unmarkCommand = new UnmarkNameCommand(NAME_FIRST_STUDENT);

        String expectedMessage = String.format(UnmarkCommand.MESSAGE_UNMARK_STUDENT_SUCCESS,
                Messages.format(studentToUnmark));

        ModelManager expectedModel = new ModelManager(model.getEduLog(), new UserPrefs());
        expectedModel.unmarkStudent(studentToUnmark);

        assertCommandSuccess(unmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        Name invalidName = new Name("Invalid");
        UnmarkCommand unmarkCommand = new UnmarkNameCommand(invalidName);

        assertCommandFailure(unmarkCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_NAME);
    }

    @Test
    public void execute_validNameFilteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Student studentToUnmark = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        UnmarkCommand unmarkCommand = new UnmarkNameCommand(NAME_FIRST_STUDENT);

        String expectedMessage = String.format(UnmarkCommand.MESSAGE_UNMARK_STUDENT_SUCCESS,
                Messages.format(studentToUnmark));

        Model expectedModel = new ModelManager(model.getEduLog(), new UserPrefs());
        expectedModel.unmarkStudent(studentToUnmark);
        showStudentAtIndex(expectedModel, INDEX_FIRST_STUDENT);

        assertCommandSuccess(unmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);
        List<Name> names = model.getEduLog().getStudentList().stream().map(Student::getName).toList();

        Name unFilteredStudent = NAME_SECOND_STUDENT;
        // ensures that unFilteredStudent is still in bounds of edulog book list
        assertTrue(names.contains(unFilteredStudent));

        UnmarkCommand unmarkCommand = new UnmarkNameCommand(unFilteredStudent);

        assertCommandFailure(unmarkCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_NAME);
    }

    @Test
    public void equals() {
        UnmarkCommand unmarkFirstCommand = new UnmarkNameCommand(NAME_FIRST_STUDENT);
        UnmarkCommand unmarkSecondCommand = new UnmarkNameCommand(NAME_SECOND_STUDENT);

        // same object -> returns true
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));

        // same values -> returns true
        UnmarkCommand unmarkFirstCommandCopy = new UnmarkNameCommand(NAME_FIRST_STUDENT);
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
        Name targetName = new Name("targetName");
        UnmarkNameCommand unmarkCommand = new UnmarkNameCommand(targetName);
        String expected = UnmarkNameCommand.class.getCanonicalName() + "{targetName=" + targetName + "}";
        assertEquals(expected, unmarkCommand.toString());
    }
}
