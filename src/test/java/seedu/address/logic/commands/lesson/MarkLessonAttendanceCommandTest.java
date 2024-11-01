package seedu.address.logic.commands.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Name;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.TypicalStudents;

public class MarkLessonAttendanceCommandTest {

    private static final Index VALID_INDEX = Index.fromOneBased(1);
    private static final List<Name> VALID_NAMES = List.of(
            TypicalStudents.ALICE.getName(), TypicalStudents.BENSON.getName());
    private static final Lesson LESSON = new LessonBuilder().withStudent(TypicalStudents.ALICE).build();

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new MarkLessonAttendanceCommand(null, VALID_NAMES, false));
        assertThrows(NullPointerException.class, () ->
                new MarkLessonAttendanceCommand(VALID_INDEX, null, false));
        assertThrows(NullPointerException.class, () ->
                new MarkLessonAttendanceCommand(null, null, false));
    }

    @Test
    public void execute_setAttendance_successful() throws Exception {
        // The command uses a lot of methods from model, so making a stub is difficult
        ModelManager model = new ModelManager();
        // setup
        model.addStudent(TypicalStudents.ALICE);
        model.addLesson(LESSON);
        Lesson expectedLesson = new Lesson(LESSON);
        expectedLesson.setAttendance(TypicalStudents.ALICE, true);

        MarkLessonAttendanceCommand command = new MarkLessonAttendanceCommand(
                Index.fromOneBased(1), List.of(TypicalStudents.ALICE.getName()), true);
        CommandResult commandResult = command.execute(model);

        assertEquals(String.format(
                MarkLessonAttendanceCommand.MESSAGE_SUCCESS, TypicalStudents.ALICE.getName(), "true"),
                commandResult.getFeedbackToUser());
        assertTrue(model.hasLesson(expectedLesson));
    }

    @Test
    public void execute_lessonIndexOutOfBounds_failure() {
        ModelManager model = new ModelManager();
        // setup
        model.addLesson(LESSON);

        MarkLessonAttendanceCommand command = new MarkLessonAttendanceCommand(
                Index.fromOneBased(100), List.of(TypicalStudents.ALICE.getName()), true);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_studentNotInAddressBook_failure() {
        ModelManager model = new ModelManager();
        // setup
        model.addLesson(LESSON);

        MarkLessonAttendanceCommand command = new MarkLessonAttendanceCommand(
                Index.fromOneBased(1), List.of(TypicalStudents.ALICE.getName()), true);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_studentNotInLesson_failure() {
        ModelManager model = new ModelManager();
        // setup
        model.addStudent(TypicalStudents.BENSON);
        model.addLesson(LESSON); // does not have BENSON

        MarkLessonAttendanceCommand command = new MarkLessonAttendanceCommand(
                Index.fromOneBased(1), List.of(TypicalStudents.BENSON.getName()), true);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals() {
        MarkLessonAttendanceCommand command1 = new MarkLessonAttendanceCommand(
                Index.fromOneBased(1), List.of(TypicalStudents.ALICE.getName()), true);
        MarkLessonAttendanceCommand command2 = new MarkLessonAttendanceCommand(
                Index.fromOneBased(1), List.of(TypicalStudents.ALICE.getName()), true);
        // different index
        MarkLessonAttendanceCommand command3 = new MarkLessonAttendanceCommand(
                Index.fromOneBased(2), List.of(TypicalStudents.ALICE.getName()), true);
        // different student
        MarkLessonAttendanceCommand command4 = new MarkLessonAttendanceCommand(
                Index.fromOneBased(1), List.of(TypicalStudents.BENSON.getName()), true);
        // different attendance
        MarkLessonAttendanceCommand command5 = new MarkLessonAttendanceCommand(
                Index.fromOneBased(1), List.of(TypicalStudents.ALICE.getName()), false);

        assertTrue(command1.equals(command1));
        assertTrue(command1.equals(command2));
        assertFalse(command1.equals(command3));
        assertFalse(command1.equals(command4));
        assertFalse(command1.equals(command5));
        assertFalse(command1.equals("hello codecov"));
    }

    @Test
    public void getCommandType_returnsCorrectType() {
        MarkLessonAttendanceCommand command = new MarkLessonAttendanceCommand(
                VALID_INDEX, VALID_NAMES, true);
        assertEquals(MarkLessonAttendanceCommand.COMMAND_TYPE, command.getCommandType());
    }
}
