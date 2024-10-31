package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "ExportCommandTest");
    @TempDir
    public Path testFolder;
    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_validArgs_returnsExportCommand() throws IOException {
        Path validPath = TEST_DATA_FOLDER.resolve("validExportFile");
        Files.deleteIfExists(validPath);
        File exportFile = validPath.toFile();

        Path afterParsePath = TEST_DATA_FOLDER.resolve("validExportFile.txt");
        Files.deleteIfExists(afterParsePath);
        File afterParseExportFile = afterParsePath.toFile();

        assertParseSuccess(parser, exportFile.toString(), new ExportCommand(afterParseExportFile));
    }

    @Test
    public void parse_emptyStringInvalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nullPathInvalidArgs_throwsParseException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }
}
