package seedu.internbuddy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.logic.parser.exceptions.ParseException;

public class UpdateCommandParserTest {

    private final UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_invalidCompanyIndex_throwsParseException() {
        String invalidUserInput = "a 1 as/INTERVIEWED";
        assertThrows(ParseException.class, () -> parser.parse(invalidUserInput));
    }

    @Test
    public void parse_invalidApplicationIndex_throwsParseException() {
        String invalidUserInput = "1 a as/INTERVIEWED";
        assertThrows(ParseException.class, () -> parser.parse(invalidUserInput));
    }

    @Test
    public void parse_missingAppStatus_throwsParseException() {
        String invalidUserInput = "1 1";
        assertThrows(ParseException.class, () -> parser.parse(invalidUserInput));
    }

    @Test
    public void parse_invalidCommandFormat_throwsParseException() {
        String invalidUserInput = "update";
        assertThrows(ParseException.class, () -> parser.parse(invalidUserInput));
    }
}
