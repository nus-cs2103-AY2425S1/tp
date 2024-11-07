package seedu.address.logic.parser.eventcommandparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.eventcommands.FindEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class FindEventCommandParserTest {

    private final FindEventCommandParser parser = new FindEventCommandParser();

    @Test
    public void parse_validArgs_returnsFindEventCommand() throws Exception {
        String keyword = "meeting";
        FindEventCommand expectedCommand = new FindEventCommand(keyword);
        FindEventCommand actualCommand = parser.parse("meeting");
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_multipleKeywords_returnsFindEventCommand() throws Exception {
        String keywords = "project meeting";
        FindEventCommand expectedCommand = new FindEventCommand(keywords);
        FindEventCommand actualCommand = parser.parse("project meeting");
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));

        assertThrows(ParseException.class, () -> parser.parse("   "),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
    }
}
