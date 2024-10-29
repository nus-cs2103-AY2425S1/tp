package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.BackupCommand;

public class BackupCommandParserTest {

    private BackupCommandParser parser = new BackupCommandParser();

    @Test
    public void parse_noArguments_returnsBackupCommandWithNullFileName() throws Exception {
        BackupCommand expectedCommand = new BackupCommand(null);
        BackupCommand actualCommand = parser.parse("");
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_withFileName_returnsBackupCommandWithFileName() throws Exception {
        String fileName = "myBackup";
        BackupCommand expectedCommand = new BackupCommand(fileName);
        BackupCommand actualCommand = parser.parse(" " + fileName);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_withWhitespaceOnly_returnsBackupCommandWithNullFileName() throws Exception {
        BackupCommand expectedCommand = new BackupCommand(null);
        BackupCommand actualCommand = parser.parse("   ");
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }
}
