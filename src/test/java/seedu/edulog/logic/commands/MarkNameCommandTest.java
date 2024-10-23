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
 * {@code MarkCommand}.
 */
public class MarkNameCommandTest {

    private Model model = new ModelManager(getTypicalEduLog(), new UserPrefs());

    @Test
    public void execute_validNameUnfilteredList_success() {
        Student studentToMark = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        MarkCommand markCommand = new MarkNameCommand(NAME_FIRST_STUDENT);

        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_STUDENT_SUCCESS,
                Messages.format(studentToMark));

        ModelManager expectedModel = new ModelManager(model.getEduLog(), new UserPrefs());
        expectedModel.markStudent(studentToMark);

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        Name invalidName = new Name("Invalid");
        MarkCommand markCommand = new MarkNameCommand(invalidName);

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_NAME);
    }

    @Test
    public void execute_validNameFilteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Student studentToMark = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        MarkCommand markCommand = new MarkNameCommand(NAME_FIRST_STUDENT);

        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_STUDENT_SUCCESS,
                Messages.format(studentToMark));

        Model expectedModel = new ModelManager(model.getEduLog(), new UserPrefs());
        expectedModel.markStudent(studentToMark);
        showStudentAtIndex(expectedModel, INDEX_FIRST_STUDENT);

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);
        List<Name> names = model.getEduLog().getStudentList().stream().map(Student::getName).toList();

        Name unFilteredStudent = NAME_SECOND_STUDENT;
        // ensures that unFilteredStudent is still in bounds of edulog book list
        assertTrue(names.contains(unFilteredStudent));

        MarkCommand markCommand = new MarkNameCommand(unFilteredStudent);

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_NAME);
    }

    @Test
    public void equals() {
        MarkCommand markFirstCommand = new MarkNameCommand(NAME_FIRST_STUDENT);
        MarkCommand markSecondCommand = new MarkNameCommand(NAME_SECOND_STUDENT);

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        MarkCommand markFirstCommandCopy = new MarkNameCommand(NAME_FIRST_STUDENT);
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
        Name targetName = new Name("targetName");
        MarkNameCommand markCommand = new MarkNameCommand(targetName);
        String expected = MarkNameCommand.class.getCanonicalName() + "{targetName=" + targetName + "}";
        assertEquals(expected, markCommand.toString());
    }
}
