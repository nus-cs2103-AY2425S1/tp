package seedu.edulog.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edulog.logic.commands.CommandTestUtil.assertCommandFailure;

import org.junit.jupiter.api.Test;

import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.Model;
import seedu.edulog.model.ModelManager;
import seedu.edulog.model.calendar.Day;
import seedu.edulog.model.calendar.Description;
import seedu.edulog.model.calendar.Lesson;
import seedu.edulog.model.calendar.LessonTime;

public class DeleteLessonCommandTest {

    @Test
    public void deleteValidLesson() throws CommandException {
        Lesson lesson = new Lesson(new Description("Math"),
            new Day("Monday"), new LessonTime("1200"), new LessonTime("1300"));;
        Model model = new ModelManager();
        model.addLesson(lesson);

        DeleteLessonCommand command = new DeleteLessonCommand(new Description("Math"));
        CommandResult result = command.execute(model);
        assertEquals(result.getFeedbackToUser(),
                String.format(DeleteLessonCommand.MESSAGE_DELETE_STUDENT_SUCCESS, lesson));
    }

    @Test
    public void deleteNonExistentLesson_throwsException() {
        Lesson lesson = new Lesson(new Description("Math"),
            new Day("Monday"), new LessonTime("1200"), new LessonTime("1300"));;
        Model model = new ModelManager();
        model.addLesson(lesson);

        DeleteLessonCommand command = new DeleteLessonCommand(new Description("Physics"));
        assertCommandFailure(command, model, DeleteLessonCommand.MESSAGE_NONEXISTENT_LESSON);
    }

    @Test
    public void testEqual() {
        DeleteLessonCommand command1 = new DeleteLessonCommand(new Description("Bio"));
        DeleteLessonCommand command2 = new DeleteLessonCommand(new Description("Bio"));
        assertTrue(command1.equals(command2));
    }

    @Test
    public void testNotEqual() {
        DeleteLessonCommand command1 = new DeleteLessonCommand(new Description("Bio"));
        DeleteLessonCommand command2 = new DeleteLessonCommand(new Description("Chem"));
        assertFalse(command1.equals(command2));
    }
}
