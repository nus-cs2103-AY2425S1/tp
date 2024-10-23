package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RestoreCommand;

public class RestoreCommandParserTest {

    private final RestoreCommandParser parser = new RestoreCommandParser();

    @Test
    public void parse_emptyArgs_returnsRestoreCommandWithNoFilePath() throws Exception {
        RestoreCommand command = parser.parse("");
        assertTrue(command instanceof RestoreCommand);

        // Verify that the filePath is empty, which means it will restore from the most recent backup
        assertEquals(Optional.empty(), command.getFilePath());
    }

    @Test
    public void parse_specificFilePath_returnsRestoreCommandWithFilePath() throws Exception {
        String filePath = "backups/specificBackup.json";
        RestoreCommand command = parser.parse(filePath);

        // Verify that the filePath is present and matches the specified path
        assertEquals(Optional.of(Path.of(filePath)), command.getFilePath());
    }
}
