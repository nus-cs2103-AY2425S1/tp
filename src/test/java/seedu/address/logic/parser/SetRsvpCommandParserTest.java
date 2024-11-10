package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class SetRsvpCommandParserTest {
    private final SetRsvpCommandParser parser = new SetRsvpCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid index: 0
        assertThrows(ParseException.class, () -> parser.parse("0 s/1"));

        // Invalid index: negative number
        assertThrows(ParseException.class, () -> parser.parse("-1 s/1"));

        // Invalid status (non-numeric)
        assertThrows(NumberFormatException.class, () -> parser.parse("1 s/abc"));

        // Missing status prefix
        assertThrows(ParseException.class, () -> parser.parse("1 1"));

        // Missing index
        assertThrows(ParseException.class, () -> parser.parse("s/1"));

        // Extra arguments
        assertThrows(ParseException.class, () -> parser.parse("1 s/1 extra"));
    }

}
