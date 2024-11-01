package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ExportCommandParserTest {
    @TempDir
    public Path temporaryFolder;

    private ExportCommandParser parser;
    private Path dataDir;

    @BeforeEach
    public void setUp() throws IOException {
        parser = new ExportCommandParser();
        dataDir = temporaryFolder.resolve("data");
        Files.createDirectories(dataDir);
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up any files created during tests
        if (Files.exists(dataDir)) {
            Files.walk(dataDir)
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            // Ignore deletion errors during cleanup
                        }
                    });
        }
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ExportCommand.MESSAGE_USAGE), () -> parser.parse("     "));
    }

    @Test
    public void parse_invalidFilename_throwsParseException() {
        // filename with period
        assertThrows(ParseException.class,
                ExportCommandParser.MESSAGE_INVALID_FILENAME, () -> parser.parse("test.file"));

        // filename with slash
        assertThrows(ParseException.class,
                ExportCommandParser.MESSAGE_INVALID_FILENAME, () -> parser.parse("test/file"));

        // filename with backslash
        assertThrows(ParseException.class,
                ExportCommandParser.MESSAGE_INVALID_FILENAME, () -> parser.parse("test\\file"));
    }

    @Test
    public void parse_validArgs_returnsExportCommand() throws ParseException {
        // normal filename
        String filename = "testfile";
        ExportCommand expectedCommand = new ExportCommand(filename, false, dataDir);
        assertEquals(expectedCommand, parser.parse(filename));

        // filename with force flag
        ExportCommand expectedForceCommand = new ExportCommand(filename, true, dataDir);
        assertEquals(expectedForceCommand, parser.parse("-f " + filename));

        // filename with hyphen (not a force flag)
        String filenameWithHyphen = "test-file";
        ExportCommand expectedHyphenCommand = new ExportCommand(filenameWithHyphen, false, dataDir);
        assertEquals(expectedHyphenCommand, parser.parse(filenameWithHyphen));
    }

    @Test
    public void parse_emptyFilename_throwsParseException() {
        // When force flag is present but filename is empty
        assertThrows(ParseException.class,
                ExportCommandParser.MESSAGE_INVALID_FILENAME, () -> parser.parse("test.file"));
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ExportCommand.MESSAGE_USAGE), () -> parser.parse("-f "));

        // When force flag is present but only spaces after it
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ExportCommand.MESSAGE_USAGE), () -> parser.parse("-f      "));
    }

    @Test
    public void parse_forceWithoutFilename_throwsParseException() {
        // Just "-f" without filename
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ExportCommand.MESSAGE_USAGE), () -> parser.parse("-f"));

        // "-f" with only spaces after it
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ExportCommand.MESSAGE_USAGE), () -> parser.parse("-f    "));

        // Multiple spaces before "-f"
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ExportCommand.MESSAGE_USAGE), () -> parser.parse("    -f"));

        // Multiple spaces before and after "-f"
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ExportCommand.MESSAGE_USAGE), () -> parser.parse("    -f    "));
    }
}
