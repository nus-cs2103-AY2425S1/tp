package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains unit tests for {@code RestoreCommandParser}.
 */
public class RestoreCommandParserTest {

    private final RestoreCommandParser parser = new RestoreCommandParser();

    @Test
    public void parse_validIndex_returnsRestoreCommand() throws Exception {
        int index = 3;
        RestoreCommand expectedCommand = new RestoreCommand(index);
        RestoreCommand actualCommand = parser.parse(" " + index);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Non-integer input
        assertThrows(ParseException.class, () -> parser.parse("abc"));

        // Negative index
        assertThrows(ParseException.class, () -> parser.parse("-1"));

        // Index out of range
        assertThrows(ParseException.class, () -> parser.parse("10"));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
        assertThrows(ParseException.class, () -> parser.parse("   "));
    }
}
