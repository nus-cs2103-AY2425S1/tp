package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.calendar.Lesson;

public class AddLessonCommandTest {

    private Model model;
    private Lesson validLesson;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        validLesson = new Lesson("Math", DayOfWeek.MONDAY, LocalTime.of(12, 0), LocalTime.of(13, 0));
    }

    @Test
    public void addValidLesson() throws CommandException {
        AddLessonCommand command = new AddLessonCommand(validLesson);
        CommandResult result = command.execute(model);
        assertEquals(result.getFeedbackToUser(),
                String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson));
        assertTrue(model.hasLesson(validLesson));
    }

    @Test
    public void addDuplicateLesson() throws CommandException {
        model.addLesson(validLesson);

        // note that the lesson only has identical descriptions as the validLesson
        Lesson duplicateLesson = new Lesson("Math", DayOfWeek.TUESDAY, LocalTime.of(13, 0), LocalTime.of(14, 0));
        AddLessonCommand command = new AddLessonCommand(duplicateLesson);

        assertCommandFailure(command, model, AddLessonCommand.MESSAGE_DUPLICATE_LESSON);
    }
}
