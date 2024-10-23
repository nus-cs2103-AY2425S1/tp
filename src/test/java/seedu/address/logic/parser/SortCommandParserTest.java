package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains unit tests for {@code SortCommandParser}.
 */
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validSortField_success() throws ParseException {
        assertEquals(new SortCommand("name"), parser.parse("name"));
        assertEquals(new SortCommand("deadline"), parser.parse("deadline"));
    }

    @Test
    public void parse_invalidSortField_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);

        assertThrows(ParseException.class, () -> parser.parse("invalidField"), expectedMessage);
        assertThrows(ParseException.class, () -> parser.parse(""), expectedMessage);
    }
}
