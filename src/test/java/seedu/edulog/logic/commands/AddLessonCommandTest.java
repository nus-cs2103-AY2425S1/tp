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
import seedu.edulog.model.calendar.EdulogCalendar;
import seedu.edulog.model.calendar.Lesson;
import seedu.edulog.model.calendar.LessonTime;

public class AddLessonCommandTest {

    private Model model;
    private Lesson validLesson;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        validLesson = new Lesson(new Description("Math"),
            new Day("Monday"), new LessonTime("1200"), new LessonTime("1300"));
    }

    @Test
    public void execute_uniqueValidLesson_success() throws CommandException {
        AddLessonCommand command = new AddLessonCommand(validLesson);
        CommandResult result = command.execute(model);
        assertEquals(result.getFeedbackToUser(),
                String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson));
        assertTrue(model.hasLesson(validLesson));
    }

    @Test
    public void execute_duplicateValidLesson_throwsCommandException() throws CommandException {
        model.addLesson(validLesson);

        // note that the lesson only has identical descriptions as the validLesson
        Lesson duplicateLesson = new Lesson(new Description("Math"),
            validLesson.getStartDay(), validLesson.getStartTime(), validLesson.getEndTime());
        AddLessonCommand command = new AddLessonCommand(duplicateLesson);

        assertCommandFailure(command, model, AddLessonCommand.MESSAGE_DUPLICATE_LESSON);
    }

    @Test
    public void execute_overloadIdenticalTimingLesson_throwsCommandException() throws CommandException {
        String startDay = "Monday";
        String startTime = "1200";
        String endTime = "1300";

        for (int i = 0; i < EdulogCalendar.MAX_SIMULTANEOUS_TIMING; i++) {
            Lesson lesson = new Lesson(new Description("Math" + i),
                new Day(startDay), new LessonTime(startTime), new LessonTime(endTime));
            AddLessonCommand command = new AddLessonCommand(lesson);
            CommandResult result = command.execute(model);
            assertEquals(result.getFeedbackToUser(),
                    String.format(AddLessonCommand.MESSAGE_SUCCESS, lesson));
        }

        Lesson lesson = new Lesson(new Description("Math" + EdulogCalendar.MAX_SIMULTANEOUS_TIMING),
            new Day("Monday"), new LessonTime(startTime), new LessonTime(endTime));
        AddLessonCommand command = new AddLessonCommand(lesson);
        assertCommandFailure(command, model, EdulogCalendar.OVERLOAD_SIMULTANEOUS_TIMING);
    }
}
