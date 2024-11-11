package keycontacts.logic.commands;

import static keycontacts.logic.commands.CommandTestUtil.VALID_DATE;
import static keycontacts.logic.commands.CommandTestUtil.assertCommandFailure;
import static keycontacts.logic.commands.CommandTestUtil.assertCommandSuccess;
import static keycontacts.testutil.Assert.assertThrows;
import static keycontacts.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static keycontacts.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static keycontacts.testutil.TypicalStudents.getTypicalStudentDirectory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import keycontacts.commons.core.index.Index;
import keycontacts.logic.Messages;
import keycontacts.model.Model;
import keycontacts.model.ModelManager;
import keycontacts.model.StudentDirectory;
import keycontacts.model.UserPrefs;
import keycontacts.model.lesson.CancelledLesson;
import keycontacts.model.lesson.Date;
import keycontacts.model.student.Student;

public class UncancelLessonCommandTest {
    private Model model;
    private CancelledLesson cancelledLesson = new CancelledLesson(new Date(VALID_DATE));

    @BeforeEach
    public void initialise() {
        model = new ModelManager(getTypicalStudentDirectory(), new UserPrefs());
    }

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UncancelLessonCommand(INDEX_FIRST_STUDENT, null));
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UncancelLessonCommand(null, cancelledLesson));
    }

    @Test
    public void execute_validIndexAndCancelledLesson_success() throws Exception {
        UncancelLessonCommand command = new UncancelLessonCommand(INDEX_FIRST_STUDENT, cancelledLesson);

        Student studentToUncancel = model.getStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        // manually add the cancelled lesson to uncancel
        Student studentToUncancelWithCancelledLesson = studentToUncancel.withAddedCancelledLesson(cancelledLesson);
        model.setStudent(studentToUncancel, studentToUncancelWithCancelledLesson);

        String expectedMessage = String.format(UncancelLessonCommand.MESSAGE_SUCCESS,
                cancelledLesson.getLessonDate().toDisplay(), Messages.format(studentToUncancel));

        Model expectedModel = new ModelManager(new StudentDirectory(model.getStudentDirectory()), new UserPrefs());
        expectedModel.setStudent(studentToUncancelWithCancelledLesson, studentToUncancel);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_indexOutOfBounds_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getStudentList().size() + 1);
        UncancelLessonCommand command = new UncancelLessonCommand(outOfBoundsIndex, cancelledLesson);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_lessonNotFound_throwsCommandException() {
        UncancelLessonCommand command = new UncancelLessonCommand(INDEX_FIRST_STUDENT, cancelledLesson);

        String expectedMessage = String.format(UncancelLessonCommand.MESSAGE_LESSON_NOT_FOUND,
                Messages.format(model.getStudentList().get(INDEX_FIRST_STUDENT.getZeroBased())));
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void equals() {
        CancelledLesson cancelledLesson1 = new CancelledLesson(new Date("01-01-2024"));
        CancelledLesson cancelledLesson2 = new CancelledLesson(new Date("02-01-2024"));

        UncancelLessonCommand baseCommand = new UncancelLessonCommand(INDEX_FIRST_STUDENT, cancelledLesson1);

        // same object -> returns true
        assertTrue(baseCommand.equals(baseCommand));

        // same values -> returns true
        UncancelLessonCommand identicalCommand = new UncancelLessonCommand(INDEX_FIRST_STUDENT, cancelledLesson1);
        assertTrue(baseCommand.equals(identicalCommand));
        assertTrue(identicalCommand.equals(baseCommand));

        // different values -> returns false
        UncancelLessonCommand differentIndexCommand = new UncancelLessonCommand(INDEX_SECOND_STUDENT, cancelledLesson1);
        assertFalse(baseCommand.equals(differentIndexCommand));

        UncancelLessonCommand differentUncancelLessonCommand = new UncancelLessonCommand(INDEX_FIRST_STUDENT,
                cancelledLesson2);
        assertFalse(baseCommand.equals(differentUncancelLessonCommand));

        // null -> returns false
        assertFalse(baseCommand.equals(null));

        // not a UncancelLessonCommand -> returns false
        assertFalse(baseCommand.equals(new ClearCommand()));

    }
    @Test
    public void toStringMethod() {
        UncancelLessonCommand uncancelLessonCommand = new UncancelLessonCommand(INDEX_FIRST_STUDENT, cancelledLesson);
        String expected = UncancelLessonCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_STUDENT + ", "
                + "cancelledLesson=" + cancelledLesson + "}";
        assertEquals(expected, uncancelLessonCommand.toString());
    }
}
