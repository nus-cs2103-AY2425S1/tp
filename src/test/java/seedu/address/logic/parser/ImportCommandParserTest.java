package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ImportCommand;

public class ImportCommandParserTest {

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_validArgs_returnsImportCommand() {
        // Valid file path
        String filePath = "/path/to/valid.csv";
        ImportCommand expectedCommand = new ImportCommand(filePath);

        assertParseSuccess(parser, filePath, expectedCommand);
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        // Empty file path argument
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ImportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidFilePath_throwsParseException() {
        // Invalid file path format that should trigger the path validation
        String invalidFilePath = "/path//tofile.csv"; // Double slashes (Invalid path format)
        assertParseFailure(parser, invalidFilePath, String.format(ImportCommandParser.MESSAGE_INVALID_FILE_PATH,
                invalidFilePath));
    }

    @Test
    public void parse_invalidCharactersInFilePath_throwsParseException() {
        // File path with invalid characters (e.g., "*" is not allowed in file paths)
        String invalidFilePath = "/path/to/invalid*.csv";
        assertParseFailure(parser, invalidFilePath, String.format(ImportCommandParser.MESSAGE_INVALID_FILE_PATH,
                invalidFilePath));
    }

    @Test
    public void parse_noFileArg_throwsParseException() {
        // No file argument provided
        String emptyArg = "";
        assertParseFailure(parser, emptyArg, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ImportCommand.MESSAGE_USAGE));
    }
}
