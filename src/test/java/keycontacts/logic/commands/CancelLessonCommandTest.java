package keycontacts.logic.commands;

import static keycontacts.testutil.Assert.assertThrows;
import static keycontacts.testutil.TypicalStudents.ALICE;
import static keycontacts.testutil.TypicalStudents.getTypicalStudentDirectory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import keycontacts.commons.core.index.Index;
import keycontacts.logic.Messages;
import keycontacts.logic.commands.exceptions.CommandException;
import keycontacts.model.Model;
import keycontacts.model.ModelManager;
import keycontacts.model.UserPrefs;
import keycontacts.model.lesson.Date;
import keycontacts.model.lesson.Time;

public class CancelLessonCommandTest {

    private static final Date VALID_DATE = new Date("06-07-2022");
    private static final Time VALID_START_TIME = new Time("12:00");
    private static final Index VALID_INDEX = Index.fromOneBased(1);
    private Model model;

    @BeforeEach
    public void initialise() {
        model = new ModelManager(getTypicalStudentDirectory(), new UserPrefs());
    }

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new CancelLessonCommand(VALID_INDEX, null, VALID_START_TIME));
    }

    @Test
    public void constructor_nullTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new CancelLessonCommand(VALID_INDEX, VALID_DATE, null));
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new CancelLessonCommand(VALID_INDEX, VALID_DATE, VALID_START_TIME));
    }

    @Test
    public void execute_indexOutOfBounds_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        CancelLessonCommand command = new CancelLessonCommand(outOfBoundsIndex, VALID_DATE, VALID_START_TIME);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX, () -> command.execute(model));
    }

    @Test
    public void execute_lessonNotFound_throwsCommandException() {
        CancelLessonCommand command = new CancelLessonCommand(VALID_INDEX, new Date("01-01-2023"), new Time("14:00"));

        assertThrows(CommandException.class,
                String.format(CancelLessonCommand.MESSAGE_LESSON_NOT_FOUND, Messages.format(ALICE)), () ->
                        command.execute(model));
    }

    @Test
    public void equals() {
        CancelLessonCommand command1 = new CancelLessonCommand(VALID_INDEX, VALID_DATE, VALID_START_TIME);
        CancelLessonCommand command2 = new CancelLessonCommand(VALID_INDEX, VALID_DATE, VALID_START_TIME);
        CancelLessonCommand differentDateAndTimeCommand =
                new CancelLessonCommand(VALID_INDEX, new Date("01-01-2023"), new Time("14:00"));
        CancelLessonCommand differentTimeCommand =
                new CancelLessonCommand(VALID_INDEX, new Date("06-07-2022"), new Time("14:00"));
        CancelLessonCommand differentDateCommand =
                new CancelLessonCommand(VALID_INDEX, new Date("01-01-2023"), new Time("12:00"));

        assertEquals(command1, command1);
        assertEquals(command1, command2);
        assertEquals(command2, command1);
        assertNotEquals(command1, differentDateAndTimeCommand);
        assertNotEquals(command1, differentTimeCommand);
        assertNotEquals(command1, differentDateCommand);
        assertNotEquals(command1, null);
    }

    @Test
    public void toStringMethod() {
        CancelLessonCommand cancelLessonCommand = new CancelLessonCommand(VALID_INDEX, VALID_DATE, VALID_START_TIME);
        String expected = CancelLessonCommand.class.getCanonicalName() + "{date=" + VALID_DATE + ", "
                + "startTime=" + VALID_START_TIME + ", index=" + VALID_INDEX + "}";
        assertEquals(expected, cancelLessonCommand.toString());
    }
}
