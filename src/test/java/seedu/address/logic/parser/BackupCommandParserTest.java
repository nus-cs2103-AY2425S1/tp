package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.BackupCommand;

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
}
