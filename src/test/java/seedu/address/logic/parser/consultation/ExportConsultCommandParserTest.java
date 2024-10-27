package seedu.address.logic.parser.consultation;

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

import seedu.address.logic.commands.consultation.ExportConsultCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ExportConsultCommandParserTest {
    @TempDir
    public Path temporaryFolder;

    private ExportConsultCommandParser parser;
    private Path dataDir;

    @BeforeEach
    public void setUp() throws IOException {
        parser = new ExportConsultCommandParser();
        dataDir = temporaryFolder.resolve("data");
        Files.createDirectories(dataDir);
    }

    @AfterEach
    public void tearDown() throws IOException {
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
                        ExportConsultCommand.MESSAGE_USAGE), () -> parser.parse("     "));
    }

    @Test
    public void parse_invalidFilename_throwsParseException() {
        assertThrows(ParseException.class,
                ExportConsultCommandParser.MESSAGE_INVALID_FILENAME, () -> parser.parse("test.file"));

        assertThrows(ParseException.class,
                ExportConsultCommandParser.MESSAGE_INVALID_FILENAME, () -> parser.parse("test/file"));

        assertThrows(ParseException.class,
                ExportConsultCommandParser.MESSAGE_INVALID_FILENAME, () -> parser.parse("test\\file"));
    }

    @Test
    public void parse_validArgs_returnsExportCommand() throws ParseException {
        String filename = "testfile";
        ExportConsultCommand expectedCommand = new ExportConsultCommand(filename, false, dataDir);
        assertEquals(expectedCommand, parser.parse(filename));

        ExportConsultCommand expectedForceCommand = new ExportConsultCommand(filename, true, dataDir);
        assertEquals(expectedForceCommand, parser.parse("-f " + filename));

        String filenameWithHyphen = "test-file";
        ExportConsultCommand expectedHyphenCommand = new ExportConsultCommand(filenameWithHyphen, false, dataDir);
        assertEquals(expectedHyphenCommand, parser.parse(filenameWithHyphen));
    }

    @Test
    public void parse_forceWithoutFilename_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ExportConsultCommand.MESSAGE_USAGE), () -> parser.parse("-f"));

        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ExportConsultCommand.MESSAGE_USAGE), () -> parser.parse("-f    "));
    }
}
