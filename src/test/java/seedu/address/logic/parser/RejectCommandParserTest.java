package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;


public class RejectCommandParserTest {

    private RejectCommandParser parser = new RejectCommandParser();


    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("Amy Bee"));
    }

    @Test
    public void parse_missingName_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" Software Engineer"));
    }

    @Test
    public void parse_missingJob_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("Amy Bee "));
    }
}
