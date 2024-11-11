package keycontacts.logic.commands;

import static keycontacts.logic.commands.CommandTestUtil.VALID_DATE;
import static keycontacts.logic.commands.CommandTestUtil.VALID_END_TIME;
import static keycontacts.logic.commands.CommandTestUtil.VALID_START_TIME;
import static keycontacts.logic.commands.CommandTestUtil.assertCommandSuccess;
import static keycontacts.testutil.Assert.assertThrows;
import static keycontacts.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static keycontacts.testutil.TypicalStudents.getTypicalStudentDirectory;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import keycontacts.commons.core.index.Index;
import keycontacts.logic.Messages;
import keycontacts.logic.commands.exceptions.CommandException;
import keycontacts.model.Model;
import keycontacts.model.ModelManager;
import keycontacts.model.StudentDirectory;
import keycontacts.model.UserPrefs;
import keycontacts.model.lesson.Date;
import keycontacts.model.lesson.MakeupLesson;
import keycontacts.model.lesson.Time;
import keycontacts.model.student.Student;

public class MakeupLessonCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalStudentDirectory(), new UserPrefs());
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        MakeupLesson makeupLesson = new MakeupLesson(new Date(VALID_DATE), new Time(VALID_START_TIME),
                new Time(VALID_END_TIME));
        assertThrows(NullPointerException.class, () -> new MakeupLessonCommand(null, makeupLesson));
    }

    @Test
    public void constructor_nullMakeupLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MakeupLessonCommand(INDEX_FIRST_STUDENT, null));
    }

    @Test
    public void execute_indexOutOfBounds_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getStudentList().size() + 1);
        MakeupLesson makeupLesson = new MakeupLesson(new Date(VALID_DATE), new Time(VALID_START_TIME),
                new Time(VALID_END_TIME));
        MakeupLessonCommand command = new MakeupLessonCommand(outOfBoundsIndex, makeupLesson);

        assertThrows(CommandException.class,
            Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX, () -> command.execute(model));
    }

    @Test
    public void execute_duplicateMakeupLesson_throwsCommandException() {
        Student studentToUpdate = model.getStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        MakeupLesson makeupLesson = new MakeupLesson(new Date(VALID_DATE), new Time(VALID_START_TIME),
                new Time(VALID_END_TIME));

        Student studentWithMakeupLesson = studentToUpdate.withAddedMakeupLesson(makeupLesson);
        model.setStudent(studentToUpdate, studentWithMakeupLesson);

        MakeupLessonCommand command = new MakeupLessonCommand(INDEX_FIRST_STUDENT, makeupLesson);

        assertThrows(CommandException.class,
            MakeupLessonCommand.MESSAGE_DUPLICATE_MAKEUP_LESSON, () -> command.execute(model));
    }

    @Test
    public void execute_validInputs_success() {
        MakeupLesson makeupLesson = new MakeupLesson(new Date(VALID_DATE), new Time(VALID_START_TIME),
                new Time(VALID_END_TIME));
        MakeupLessonCommand command = new MakeupLessonCommand(INDEX_FIRST_STUDENT, makeupLesson);
        Student studentToUpdate = model.getStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student expectedUpdatedStudent = studentToUpdate.withAddedMakeupLesson(makeupLesson);

        Model expectedModel = new ModelManager(new StudentDirectory(model.getStudentDirectory()), new UserPrefs());
        expectedModel.setStudent(studentToUpdate, expectedUpdatedStudent);

        CommandResult expectedCommandResult = new CommandResult(
                String.format(MakeupLessonCommand.MESSAGE_SUCCESS, makeupLesson.toDisplay(),
                        Messages.format(expectedUpdatedStudent)));

        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        MakeupLesson makeupLesson = new MakeupLesson(new Date(VALID_DATE), new Time(VALID_START_TIME),
                new Time(VALID_END_TIME));
        MakeupLesson differentMakeupLesson = new MakeupLesson(new Date("01-01-2025"), new Time("10:00"),
                new Time("11:00"));
        MakeupLessonCommand command = new MakeupLessonCommand(INDEX_FIRST_STUDENT, makeupLesson);
        MakeupLessonCommand commandDuplicate = new MakeupLessonCommand(INDEX_FIRST_STUDENT, makeupLesson);
        MakeupLessonCommand differentLessonCommand = new MakeupLessonCommand(
            INDEX_FIRST_STUDENT, differentMakeupLesson);
        MakeupLessonCommand differentIndexCommand = new MakeupLessonCommand(Index.fromOneBased(2), makeupLesson);

        assertTrue(command.equals(command));
        assertTrue(command.equals(commandDuplicate));
        assertFalse(command.equals(differentLessonCommand));
        assertTrue(command.equals(differentIndexCommand));
        assertFalse(command.equals(null));
    }
}
