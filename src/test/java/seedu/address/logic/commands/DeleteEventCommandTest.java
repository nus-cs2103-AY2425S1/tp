package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.DeleteEventCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.attendance.AttendanceEvent;

public class DeleteEventCommandTest {

    private Model model = new ModelManager();
    private DeleteEventCommandParser parser = new DeleteEventCommandParser();

    @Test
    public void execute_deleteExistingEvent_success() throws Exception {
        // Setup
        model.addAttendanceEvent(new AttendanceEvent("Event1"));

        String userInput = " e/Event1";
        DeleteEventCommand command = parser.parse(userInput);
        CommandResult result = command.execute(model);

        String expectedMessage = "Deleted attendance events: Event1";
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_deleteMultipleExistingEvents_success() throws Exception {
        // Setup
        model.addAttendanceEvent(new AttendanceEvent("Event1"));
        model.addAttendanceEvent(new AttendanceEvent("Event2"));
        model.addAttendanceEvent(new AttendanceEvent("Event3"));

        String userInput = " e/Event1 e/Event2 e/Event3";
        DeleteEventCommand command = parser.parse(userInput);
        CommandResult result = command.execute(model);

        String expectedMessage = "Deleted attendance events: Event1, Event2, Event3";
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_deleteMultipleEventsOneNonExisting_failure() throws Exception {
        // Setup
        model.addAttendanceEvent(new AttendanceEvent("Event1"));
        model.addAttendanceEvent(new AttendanceEvent("Event2"));
        // Event3 does not exist

        String userInput = " e/Event1 e/Event2 e/Event3";
        DeleteEventCommand command = parser.parse(userInput);

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals("Attendance event 'Event3' not found.", exception.getMessage());
    }

    @Test
    public void execute_deleteNonExistingEvent_failure() throws Exception {
        String userInput = " e/NonExistingEvent";
        DeleteEventCommand command = parser.parse(userInput);

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals("Attendance event 'NonExistingEvent' not found.", exception.getMessage());
    }

    @Test
    public void execute_deleteDuplicateEvents_caseInsensitivefailure() {
        String userInput = " e/Event1 e/event1";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertEquals("Duplicate event names detected.", exception.getMessage());
    }

    @Test
    public void execute_deleteEventWithEmptyName_failure() {
        String userInput = " e/";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertEquals("Event name cannot be empty.", exception.getMessage());
    }

    @Test
    public void execute_deleteEventWithSpacesOnly_failure() {
        String userInput = " e/   ";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertEquals("Event name cannot be empty.", exception.getMessage());
    }


    @Test
    public void execute_deleteEventMultipleTimes_failure() throws Exception {
        // Setup
        model.addAttendanceEvent(new AttendanceEvent("Event1"));

        String userInput = " e/Event1";
        DeleteEventCommand command = parser.parse(userInput);
        // First deletion should succeed
        CommandResult result = command.execute(model);
        assertEquals("Deleted attendance events: Event1", result.getFeedbackToUser());

        // Attempt to delete the same event again
        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals("Attendance event 'Event1' not found.", exception.getMessage());
    }
}
