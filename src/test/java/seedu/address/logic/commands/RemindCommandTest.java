package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Days;
import seedu.address.model.student.Student;

public class RemindCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_noStudentsScheduledForToday_showsNoClassMessage() {
        model = new ModelManager();
        List<Student> reminder = model.getScheduledStudents(Days.SATURDAY);
        assertTrue(reminder.isEmpty());

        RemindCommand remindCommand = new RemindCommand();
        CommandResult result = remindCommand.execute(model);

        assertEquals(result.getFeedbackToUser(), "Congratulations! You have no class for today!");
    }

    @Test
    public void execute_studentsScheduledForToday_showsReminderMessage() {
        List<Student> reminder = model.getScheduledStudents(RemindCommand.TODAY);
        assertTrue(!reminder.isEmpty());

        RemindCommand remindCommand = new RemindCommand();
        CommandResult result = remindCommand.execute(model);

        String expectedMessage = Messages.getReminderMessage(RemindCommand.TODAY, reminder);
        assertEquals(result.getFeedbackToUser(), expectedMessage);
    }



}
