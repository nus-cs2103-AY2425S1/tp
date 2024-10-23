package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.io.File;
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
    public void parse_validArgs_returnsExportCommand() {
        Path validPath = TEST_DATA_FOLDER.resolve("validExportFile.txt");
        File exportFile = validPath.toFile();
        assertParseSuccess(parser, exportFile.toString(), new ExportCommand(exportFile));
    }

    @Test
    public void parse_emptyStringInvalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nullPathInvalidArgs_throwsParseException() {
        assertParseFailure(parser, null, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }
}
