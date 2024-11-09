package seedu.address.logic.commands.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BENSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Name;
import seedu.address.testutil.LessonBuilder;

public class MarkLessonParticipationCommandTest {

    private static final Index VALID_INDEX = Index.fromOneBased(1);
    private static final List<Name> VALID_NAMES = List.of(
            ALICE.getName(), BENSON.getName());
    private static final int VALID_PARTICIPATION = 1;
    private static final Lesson LESSON = new LessonBuilder().withStudent(ALICE).build();

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new MarkLessonParticipationCommand(null, VALID_NAMES, VALID_PARTICIPATION));
        assertThrows(NullPointerException.class, () ->
                new MarkLessonParticipationCommand(VALID_INDEX, null, VALID_PARTICIPATION));
        assertThrows(NullPointerException.class, () ->
                new MarkLessonParticipationCommand(null, null, VALID_PARTICIPATION));
    }

    @Test
    public void execute_setParticipation_successful() throws Exception {
        // The command uses a lot of methods from model, so making a stub is difficult
        ModelManager model = new ModelManager();
        // setup
        model.addStudent(ALICE);
        model.addLesson(LESSON);
        Lesson expectedLesson = new Lesson(LESSON);
        // should also set attendance of ALICE to true
        expectedLesson.setAttendance(ALICE, true);
        expectedLesson.setParticipation(ALICE, VALID_PARTICIPATION);

        MarkLessonParticipationCommand command = new MarkLessonParticipationCommand(
                Index.fromOneBased(1), List.of(ALICE.getName()), VALID_PARTICIPATION);
        CommandResult commandResult = command.execute(model);

        assertEquals(String.format(
                MarkLessonParticipationCommand.MESSAGE_SUCCESS, ALICE.getName(), VALID_PARTICIPATION),
                commandResult.getFeedbackToUser());
        assertTrue(model.hasLesson(expectedLesson));
    }

    @Test
    public void execute_setParticipationToZero_successful() throws Exception {
        ModelManager model = new ModelManager();
        model.addStudent(ALICE);
        model.addLesson(LESSON);
        Lesson expectedLesson = new Lesson(LESSON);
        expectedLesson.setParticipation(ALICE, 0); // note that attendance should remain false

        MarkLessonParticipationCommand command = new MarkLessonParticipationCommand(
                Index.fromOneBased(1), List.of(ALICE.getName()), 0);
        CommandResult commandResult = command.execute(model);

        assertEquals(String.format(MarkLessonParticipationCommand.MESSAGE_SUCCESS, ALICE.getName(), 0),
                commandResult.getFeedbackToUser());
        assertTrue(model.hasLesson(expectedLesson));
    }

    @Test
    public void execute_lessonIndexOutOfBounds_failure() {
        ModelManager model = new ModelManager();
        // setup
        model.addLesson(LESSON);

        MarkLessonParticipationCommand command = new MarkLessonParticipationCommand(
                Index.fromOneBased(100), List.of(ALICE.getName()), VALID_PARTICIPATION);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_studentNotInAddressBook_failure() {
        ModelManager model = new ModelManager();
        // setup
        model.addLesson(LESSON);

        MarkLessonParticipationCommand command = new MarkLessonParticipationCommand(
                Index.fromOneBased(1), List.of(ALICE.getName()), VALID_PARTICIPATION);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_studentNotInLesson_failure() {
        ModelManager model = new ModelManager();
        // setup
        model.addStudent(BENSON);
        model.addLesson(LESSON); // does not have BENSON

        MarkLessonParticipationCommand command = new MarkLessonParticipationCommand(
                Index.fromOneBased(1), List.of(BENSON.getName()), VALID_PARTICIPATION);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_invalidParticipationScore_failure() {
        ModelManager model = new ModelManager();
        model.addStudent(ALICE);
        model.addLesson(LESSON);

        MarkLessonParticipationCommand command = new MarkLessonParticipationCommand(
                VALID_INDEX, List.of(ALICE.getName()), -1);
        MarkLessonParticipationCommand command2 = new MarkLessonParticipationCommand(
                VALID_INDEX, List.of(ALICE.getName()), 101);

        assertThrows(AssertionError.class, () -> command.execute(model));
        assertThrows(AssertionError.class, () -> command2.execute(model));
    }

    @Test
    public void equals() {
        MarkLessonParticipationCommand command1 = new MarkLessonParticipationCommand(
                Index.fromOneBased(1), List.of(ALICE.getName()), VALID_PARTICIPATION);
        MarkLessonParticipationCommand command2 = new MarkLessonParticipationCommand(
                Index.fromOneBased(1), List.of(ALICE.getName()), VALID_PARTICIPATION);
        // different index
        MarkLessonParticipationCommand command3 = new MarkLessonParticipationCommand(
                Index.fromOneBased(2), List.of(ALICE.getName()), VALID_PARTICIPATION);
        // different student
        MarkLessonParticipationCommand command4 = new MarkLessonParticipationCommand(
                Index.fromOneBased(1), List.of(BENSON.getName()), VALID_PARTICIPATION);
        // different participation score
        MarkLessonParticipationCommand command5 = new MarkLessonParticipationCommand(
                Index.fromOneBased(1), List.of(ALICE.getName()),
                VALID_PARTICIPATION + 1);

        assertTrue(command1.equals(command1));
        assertTrue(command1.equals(command2));
        assertFalse(command1.equals(command3));
        assertFalse(command1.equals(command4));
        assertFalse(command1.equals(command5));
        assertFalse(command1.equals("hello codecov"));
    }

    @Test
    public void getCommandType_returnsCorrectType() {
        MarkLessonParticipationCommand command = new MarkLessonParticipationCommand(
                VALID_INDEX, VALID_NAMES, VALID_PARTICIPATION);
        assertEquals(MarkLessonParticipationCommand.COMMAND_TYPE, command.getCommandType());
    }
}
