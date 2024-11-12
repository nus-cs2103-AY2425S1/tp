package seedu.edulog.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edulog.logic.commands.CommandTestUtil.assertCommandFailure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.Model;
import seedu.edulog.model.ModelManager;
import seedu.edulog.model.calendar.Day;
import seedu.edulog.model.calendar.Description;
import seedu.edulog.model.calendar.Lesson;
import seedu.edulog.model.calendar.LessonTime;

public class AddLessonCommandTest {

    private Model model;
    private Lesson validLesson1;
    private Lesson validLesson2;
    private Lesson validLesson3;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        validLesson1 = new Lesson(new Description("Math"),
            new Day("Monday"), new LessonTime("1200"), new LessonTime("1300"));
        validLesson2 = new Lesson(new Description("Science"),
            new Day("Tuesday"), new LessonTime("0000"), new LessonTime("2359"));
        validLesson3 = new Lesson(new Description("Mathematics"),
            new Day("Tuesday"), new LessonTime("2300"), new LessonTime("0300"));
    }

    @Test
    public void execute_uniqueValidLessonTypical_success() throws CommandException {
        AddLessonCommand command = new AddLessonCommand(validLesson1);
        CommandResult result = command.execute(model);
        assertEquals(result.getFeedbackToUser(),
                String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson1));
        assertTrue(model.hasLesson(validLesson1));
    }

    @Test
    public void execute_uniqueValidLessonMaxDuration_success() throws CommandException {
        AddLessonCommand command = new AddLessonCommand(validLesson2);
        CommandResult result = command.execute(model);
        assertEquals(result.getFeedbackToUser(),
            String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson2));
        assertTrue(model.hasLesson(validLesson2));
    }

    @Test
    public void execute_uniqueValidLessonSpansMidnight_success() throws CommandException {
        AddLessonCommand command = new AddLessonCommand(validLesson3);
        CommandResult result = command.execute(model);
        assertEquals(result.getFeedbackToUser(),
            String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson3));
        assertTrue(model.hasLesson(validLesson3));
    }

    @Test
    public void execute_multipleUniqueValidLessons_success() throws CommandException {
        AddLessonCommand command = new AddLessonCommand(validLesson1);
        command.execute(model);

        command = new AddLessonCommand(validLesson2);
        command.execute(model);

        command = new AddLessonCommand(validLesson3);
        command.execute(model);

        assertTrue(model.hasLesson(validLesson1));
        assertTrue(model.hasLesson(validLesson2));
        assertTrue(model.hasLesson(validLesson3));
    }

    @Test
    public void execute_duplicateValidLesson_throwsCommandException() throws CommandException {
        model.addLesson(validLesson1);

        // note that the lesson only has identical descriptions as the validLesson
        Lesson duplicateLesson = new Lesson(new Description("Math"),
            validLesson1.getStartDay(), validLesson1.getStartTime(), validLesson1.getEndTime());
        AddLessonCommand command = new AddLessonCommand(duplicateLesson);

        assertCommandFailure(command, model, AddLessonCommand.MESSAGE_DUPLICATE_LESSON);
    }
}
