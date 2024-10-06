package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.BackupCommand;
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

    @Test
    public void parse_validArgument_returnsBackupCommand() {
        BackupCommandParser parser = new BackupCommandParser();
        BackupCommand command = null;
        try {
            command = parser.parse("backup/validPath.json");
        } catch (ParseException e) {
            fail("Parsing failed for valid input: " + e.getMessage());
        }
        assertNotNull(command);
        assertEquals("backup/validPath.json", command.getDestinationPath());
    }

    @Test
    public void parse_emptyArgument_throwsParseException() {
        BackupCommandParser parser = new BackupCommandParser();
        assertThrows(ParseException.class, () -> parser.parse(""), "Expected ParseException for empty input");
    }


}
