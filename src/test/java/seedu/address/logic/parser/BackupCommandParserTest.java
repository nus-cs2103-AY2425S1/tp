package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;


/**
 * Contains unit tests for {@code BackupCommandParser}.
 */
public class BackupCommandParserTest {

    private final BackupCommandParser parser = new BackupCommandParser();

    @Test
    public void parse_noArguments_returnsBackupCommandWithNullActionDescription() throws Exception {
        BackupCommand expectedCommand = new BackupCommand(null);
        BackupCommand actualCommand = parser.parse("");
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_withActionDescription_returnsBackupCommandWithActionDescription() throws Exception {
        String actionDescription = "myBackup";
        BackupCommand expectedCommand = new BackupCommand(actionDescription);
        BackupCommand actualCommand = parser.parse(" " + actionDescription);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_withWhitespaceOnly_returnsBackupCommandWithNullActionDescription() throws Exception {
        BackupCommand expectedCommand = new BackupCommand(null);
        BackupCommand actualCommand = parser.parse("   ");
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_descriptionTooLong_throwsParseException() {
        // Create a description longer than the maximum allowed length
        String longDescription = "a".repeat(300); // Exceeds 250 characters

        // Attempt to parse the command with the long description
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parse(longDescription);
        });

        // Check that the exception message is correct
        assertEquals(
                "Failed to create backup!! Backup file name exceeds the maximum length of 250 characters. Please shorten your description.",
                exception.getMessage()
        );
    }
}
