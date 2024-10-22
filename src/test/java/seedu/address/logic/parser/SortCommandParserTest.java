package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SortCommandParserTest {

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgsName_returnsSortCommand() throws Exception {
        // Test valid sorting by name
        SortCommand expectedCommand = new SortCommand(SortCommand.SortType.NAME);
        SortCommand result = parser.parse("name");
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_validArgsAppointment_returnsSortCommand() throws Exception {
        // Test valid sorting by appointment
        SortCommand expectedCommand = new SortCommand(SortCommand.SortType.APPOINTMENT);
        SortCommand result = parser.parse("appointment");
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test invalid sorting argument
        assertThrows(ParseException.class, () -> parser.parse("invalid"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        // Test empty argument
        assertThrows(ParseException.class, () -> parser.parse(""),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceArgs_throwsParseException() {
        // Test whitespace-only argument
        assertThrows(ParseException.class, () -> parser.parse("   "),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
