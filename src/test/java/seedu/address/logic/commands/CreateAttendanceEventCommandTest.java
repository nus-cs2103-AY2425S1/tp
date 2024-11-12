package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.CreateAttendanceEventCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CreateAttendanceEventCommandTest {

    private Model model = new ModelManager();
    private CreateAttendanceEventCommandParser parser = new CreateAttendanceEventCommandParser();

    @Test
    public void execute_createSingleEvent_success() throws Exception {
        String userInput = " e/Event1";
        CreateAttendanceEventCommand command = parser.parse(userInput);
        CommandResult result = command.execute(model);

        String expectedMessage = "Created attendance events: Event1";
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_createMultipleEvents_success() throws Exception {
        String userInput = " e/Event1 e/Event2 e/Event3";
        CreateAttendanceEventCommand command = parser.parse(userInput);
        CommandResult result = command.execute(model);

        String expectedMessage = "Created attendance events: Event1, Event2, Event3";
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }


    @Test
    public void execute_createDuplicateEvents_caseInsensitivefailure() {
        String userInput = " e/Event1 e/event1";

        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertEquals("Duplicate event names detected.", exception.getMessage());
    }


    @Test
    public void execute_createEventWithSlashInName_failure() {
        String userInput = " e/Invalid/Event";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertEquals("Event name cannot contain '/'.", exception.getMessage());
    }

    @Test
    public void execute_createEventWithEmptyName_failure() {
        String userInput = "e/";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateAttendanceEventCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

    @Test
    public void execute_createEventWithSpacesOnly_failure() {
        String userInput = "e/   ";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateAttendanceEventCommand.MESSAGE_USAGE),
                exception.getMessage());
    }
}
