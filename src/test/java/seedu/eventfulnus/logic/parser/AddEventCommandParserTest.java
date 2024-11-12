package seedu.eventfulnus.logic.parser;

import static seedu.eventfulnus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.eventfulnus.logic.parser.exceptions.ParseException;

public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();


    @Test
    void parse_missingName_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" "));
    }

    @Test
    void parse_invalidArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" n/"));
    }

    @Test
    void parse_duplicatePrefixes_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" n/CHESS_COM_NUSC n/SWIMMING_M_MED_DEN"));
    }

    @Test
    void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }
}
