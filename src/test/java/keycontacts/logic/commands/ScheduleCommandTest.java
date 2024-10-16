package keycontacts.logic.commands;

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
import keycontacts.logic.commands.exceptions.CommandException;
import keycontacts.model.Model;
import keycontacts.model.ModelManager;
import keycontacts.model.StudentDirectory;
import keycontacts.model.UserPrefs;
import keycontacts.model.lesson.RegularLesson;
import keycontacts.model.student.Student;
import keycontacts.testutil.RegularLessonBuilder;

public class ScheduleCommandTest {
    private Model model = new ModelManager(getTypicalStudentDirectory(), new UserPrefs());
    private RegularLesson regularLesson = new RegularLessonBuilder().build();

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
        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_STUDENT, regularLesson);

        Student studentToSchedule = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student scheduledStudent = studentToSchedule.withRegularLesson(regularLesson);

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_SCHEDULE_LESSON_SUCCESS,
                Messages.format(studentToSchedule));

        Model expectedModel = new ModelManager(new StudentDirectory(model.getStudentDirectory()), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), scheduledStudent);

        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_indexOutOfBounds_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        ScheduleCommand command = new ScheduleCommand(outOfBoundsIndex, regularLesson);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX, () -> command.execute(model));
    }

    @Test
    public void execute_lessonUnchanged_throwsCommandException() throws Exception {ALICE.getRegularLesson();
        Student studentToSchedule = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());

        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_STUDENT, studentToSchedule.getRegularLesson());

        assertThrows(CommandException.class,
                ScheduleCommand.MESSAGE_LESSON_UNCHANGED, () -> scheduleCommand.execute(model));
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
