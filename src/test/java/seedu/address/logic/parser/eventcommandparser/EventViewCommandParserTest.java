package seedu.address.logic.parser.eventcommandparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.eventcommands.EventViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class EventViewCommandParserTest {

    private final EventViewCommandParser parser = new EventViewCommandParser();

    @Test
    public void parse_validArgs_returnsEventViewCommand() throws Exception {
        EventViewCommand expectedCommand = new EventViewCommand(Index.fromOneBased(1));
        EventViewCommand actualCommand = parser.parse("1");
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("a"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventViewCommand.MESSAGE_USAGE));

        assertThrows(ParseException.class, () -> parser.parse(" "),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_indexOutOfBounds_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("-1"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventViewCommand.MESSAGE_USAGE));

        assertThrows(ParseException.class, () -> parser.parse("0"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventViewCommand.MESSAGE_USAGE));
    }
}
