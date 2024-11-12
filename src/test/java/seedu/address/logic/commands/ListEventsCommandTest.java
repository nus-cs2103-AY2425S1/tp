package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.attendance.AttendanceEvent;

public class ListEventsCommandTest {

    private Model model = new ModelManager();

    @Test
    public void execute_noEvents_throwsCommandException() {
        ListEventsCommand command = new ListEventsCommand();

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals("There are no attendance events to display.", exception.getMessage());
    }

    @Test
    public void execute_multipleEvents_showsAllEvents() throws CommandException {
        // Add events to the model
        model.addAttendanceEvent(new AttendanceEvent("Event 1"));
        model.addAttendanceEvent(new AttendanceEvent("Event 2"));

        ListEventsCommand command = new ListEventsCommand();
        CommandResult result = command.execute(model);

        String expectedMessage = "Attendance Events:\nEvent 1\nEvent 2";
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }
}
