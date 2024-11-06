package seedu.address.logic.parser.eventcommandparser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.eventcommands.EventDeleteCommand;
import seedu.address.logic.commands.eventcommands.EventNewCommand;
import seedu.address.logic.commands.eventcommands.EventViewCommand;
import seedu.address.logic.commands.eventcommands.FindEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class EventCommandParserTest {

    private EventCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new EventCommandParser();
    }

    @Test
    public void parseCommand_newEventCommand_success() throws Exception {
        String userInputWithoutEventCommandIndicator = "new n/Coding Exam l/LT 28 d/2024-12-12 s/19:00 e/21:00 des/Final Exam";
        Command command = parser.parseCommand(userInputWithoutEventCommandIndicator);
        assertTrue(command instanceof EventNewCommand);
    }

    @Test
    public void parseCommand_deleteEventCommand_success() throws Exception {
        String userInputWithoutEventCommandIndicator = "del 1";
        Command command = parser.parseCommand(userInputWithoutEventCommandIndicator);
        assertTrue(command instanceof EventDeleteCommand);
    }

    @Test
    public void parseCommand_viewEventCommand_success() throws Exception {
        String userInputWithoutEventCommandIndicator = "view 1";
        Command command = parser.parseCommand(userInputWithoutEventCommandIndicator);
        assertTrue(command instanceof EventViewCommand);
    }

    @Test
    public void parseCommand_findEventCommand_success() throws Exception {
        String userInputWithoutEventCommandIndicator = "find Forest";
        Command command = parser.parseCommand(userInputWithoutEventCommandIndicator);
        assertTrue(command instanceof FindEventCommand);
    }

    @Test
    public void parseCommand_invalidCommandFormat_throwsParseException() {
        String invalidUserInput = "new n/";
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parseCommand(invalidUserInput));
        System.out.println(thrown.getMessage());
        assertTrue(thrown.getMessage().contains("Invalid command format"));
    }


    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        String unknownUserInput = "unknowncommand";
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parseCommand(unknownUserInput));
        assertTrue(thrown.getMessage().contains(MESSAGE_UNKNOWN_COMMAND));
    }

    @Test
    public void parseCommand_emptyInput_throwsParseException() {
        String emptyUserInput = "";
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parseCommand(emptyUserInput));
        assertTrue(thrown.getMessage().contains(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE)));
    }
}

