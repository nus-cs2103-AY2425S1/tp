package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;


class ViewCommandParserTest {
    @Test
    public void parse_validSingleDigitIndex_returnsViewCommand() throws Exception {
        ViewCommandParser parser = new ViewCommandParser();
        ViewCommand result = parser.parse("1");
        assertEquals(new ViewCommand(Index.fromOneBased(1)), result);
    }

    @Test
    public void parse_validInputWithWhitespace_returnsViewCommand() throws Exception {
        ViewCommandParser parser = new ViewCommandParser();
        ViewCommand result = parser.parse("   3   ");
        assertEquals(new ViewCommand(Index.fromOneBased(3)), result);
    }

    @Test
    public void parse_validMultiDigitIndex_returnsViewCommand() throws Exception {
        ViewCommandParser parser = new ViewCommandParser();
        ViewCommand result = parser.parse("15");
        assertEquals(new ViewCommand(Index.fromOneBased(15)), result);
    }

    @Test
    public void parse_invalidFloatIndex_throwsParseException() {
        ViewCommandParser parser = new ViewCommandParser();
        assertThrows(ParseException.class, () -> parser.parse("1.5"));
    }

    @Test
    public void parse_whitespaceOnlyInput_throwsParseException() {
        ViewCommandParser parser = new ViewCommandParser();
        assertThrows(ParseException.class, () -> parser.parse("    "));
    }

    @Test
    public void parse_nonNumericInput_throwsParseException() {
        ViewCommandParser parser = new ViewCommandParser();
        assertThrows(ParseException.class, () -> parser.parse("abc"));
    }

    @Test
    public void parse_negativeNumberInput_throwsParseException() {
        ViewCommandParser parser = new ViewCommandParser();
        assertThrows(ParseException.class, () -> parser.parse("-1"));
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        ViewCommandParser parser = new ViewCommandParser();
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

}
