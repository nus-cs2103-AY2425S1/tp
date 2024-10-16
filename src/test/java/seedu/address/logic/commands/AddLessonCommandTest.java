package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.calendar.Lesson;

public class AddLessonCommandTest {

    @Test
    public void addValidLesson() throws CommandException {
        Lesson lesson = new Lesson("Math", DayOfWeek.MONDAY, LocalTime.of(12, 0), LocalTime.of(13, 0));
        Model model = new ModelManager();
        model.addLesson(lesson);

        AddLessonCommand command = new AddLessonCommand(lesson);
        CommandResult result = command.execute(model);
        assertEquals(result.getFeedbackToUser(),
                String.format(AddLessonCommand.MESSAGE_SUCCESS, lesson));
        assertTrue(model.hasLesson(lesson));
    }
}
