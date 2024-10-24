package keycontacts.logic.commands;

import static keycontacts.logic.commands.CommandTestUtil.assertCommandFailure;
import static keycontacts.logic.commands.CommandTestUtil.assertCommandSuccess;
import static keycontacts.testutil.Assert.assertThrows;
import static keycontacts.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static keycontacts.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static keycontacts.testutil.TypicalStudents.ALICE;
import static keycontacts.testutil.TypicalStudents.BENSON;
import static keycontacts.testutil.TypicalStudents.getTypicalStudentDirectory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import keycontacts.commons.core.index.Index;
import keycontacts.logic.Messages;
import keycontacts.model.Model;
import keycontacts.model.ModelManager;
import keycontacts.model.StudentDirectory;
import keycontacts.model.UserPrefs;
import keycontacts.model.lesson.RegularLesson;
import keycontacts.model.student.Student;
import keycontacts.testutil.RegularLessonBuilder;

public class ScheduleCommandTest {
    private final Model model = new ModelManager(getTypicalStudentDirectory(), new UserPrefs());
    private final RegularLesson regularLesson = model.getStudentList().get(INDEX_FIRST_STUDENT.getZeroBased())
            .getRegularLesson();
    private final String updatedEndTime = regularLesson.getEndTime().getTime().plusMinutes(1).toString();
    private final RegularLesson updatedRegularLesson =
            new RegularLessonBuilder(regularLesson).withEndTime(updatedEndTime).build();

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ScheduleCommand(null, regularLesson));
    }
    @Test
    public void constructor_nullRegularLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ScheduleCommand(INDEX_FIRST_STUDENT, null));
    }

    @Test
    public void execute_validIndexAndRegularLesson_success() throws Exception {
        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_STUDENT, updatedRegularLesson);

        Student studentToSchedule = model.getStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student scheduledStudent = studentToSchedule.withRegularLesson(updatedRegularLesson).withoutCancelledLessons();

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_SCHEDULE_LESSON_SUCCESS,
                updatedRegularLesson.toDisplay(), Messages.format(studentToSchedule));

        Model expectedModel = new ModelManager(new StudentDirectory(model.getStudentDirectory()), new UserPrefs());
        expectedModel.setStudent(model.getStudentList().get(0), scheduledStudent);

        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_indexOutOfBounds_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getStudentList().size() + 1);
        ScheduleCommand scheduleCommand = new ScheduleCommand(outOfBoundsIndex, updatedRegularLesson);

        assertCommandFailure(scheduleCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_lessonUnchanged_throwsCommandException() throws Exception {
        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_STUDENT, regularLesson);

        assertCommandFailure(scheduleCommand, model, ScheduleCommand.MESSAGE_LESSON_UNCHANGED);
    }

    @Test
    public void equals() {
        RegularLesson regularLesson1 = BENSON.getRegularLesson();
        RegularLesson regularLesson2 = ALICE.getRegularLesson();

        ScheduleCommand baseCommand = new ScheduleCommand(INDEX_FIRST_STUDENT, regularLesson1);

        // same object -> returns true
        assertTrue(baseCommand.equals(baseCommand));

        // same values -> returns true
        ScheduleCommand identicalCommand = new ScheduleCommand(INDEX_FIRST_STUDENT, regularLesson1);
        assertTrue(baseCommand.equals(identicalCommand));
        assertTrue(identicalCommand.equals(baseCommand));

        // different values -> returns false
        ScheduleCommand differentIndexCommand = new ScheduleCommand(INDEX_SECOND_STUDENT, regularLesson1);
        assertFalse(baseCommand.equals(differentIndexCommand));

        ScheduleCommand differentRegularLessonCommand = new ScheduleCommand(INDEX_FIRST_STUDENT, regularLesson2);
        assertFalse(baseCommand.equals(differentRegularLessonCommand));

        // null -> returns false
        assertFalse(baseCommand.equals(null));

        // not a ScheduleCommand -> returns false
        assertFalse(baseCommand.equals(new ClearCommand()));

    }
    @Test
    public void toStringMethod() {
        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_STUDENT, regularLesson);
        String expected = ScheduleCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_STUDENT + ", "
                + "regularLesson=" + regularLesson + "}";
        assertEquals(expected, scheduleCommand.toString());
    }
}
