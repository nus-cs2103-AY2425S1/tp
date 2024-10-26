package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SortCommandParserTest {

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validInputAscending_success() throws Exception {
        // Valid input 'a' should result in ascending order
        SortCommand command = parser.parse("a");
        assertNotNull(command);
        assertTrue(command.isAscending());
    }

    @Test
    public void parse_validInputDescending_success() throws Exception {
        // Valid input 'd' should result in descending order
        SortCommand command = parser.parse("d");
        assertNotNull(command);
        assertFalse(command.isAscending());
    }

    @Test
    public void parse_invalidInput_throwsParseException() {
        // Any invalid input should throw a ParseException
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse("x"));
        assertTrue(exception.getMessage().contains("Invalid sort order"));
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        // Empty input or whitespace should throw a ParseException
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse("  "));
        assertTrue(exception.getMessage().contains("Invalid sort order"));
    }

    @Test
    public void parse_inputWithWhitespace_success() throws Exception {
        // Input with leading or trailing whitespace should still work
        SortCommand command1 = parser.parse("  a  ");
        assertNotNull(command1);
        assertTrue(command1.isAscending());

        SortCommand command2 = parser.parse("  d  ");
        assertNotNull(command2);
        assertFalse(command2.isAscending());
    }

    @Test
    public void parse_mixedCaseInput_throwsParseException() {
        // Mixed-case input should not be valid
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse("A"));
        assertTrue(exception.getMessage().contains("Invalid sort order"));

        exception = assertThrows(ParseException.class, () -> parser.parse("D"));
        assertTrue(exception.getMessage().contains("Invalid sort order"));
    }
}
