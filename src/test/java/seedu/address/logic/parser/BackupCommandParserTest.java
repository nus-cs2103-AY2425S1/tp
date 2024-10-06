package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains unit tests for {@code BackupCommandParser}.
 */
public class BackupCommandParserTest {
    private final BackupCommandParser parser = new BackupCommandParser();

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    public void parse_whitespaceArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("   "));
    }

    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parseCommandBackupInvalidArgsThrowsParseException() {
        BackupCommandParser parser = new BackupCommandParser();
        String invalidArgs = ""; // Empty argument

        assertThrows(ParseException.class, () -> parser.parse(invalidArgs));
    }

}
