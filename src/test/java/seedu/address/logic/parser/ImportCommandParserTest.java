package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_FILE_PATH;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ImportCommand;

public class ImportCommandParserTest {
    private ImportCommandParser parser = new ImportCommandParser();
    private final String nonEmptyFilePath = "./SomeFilePath";
    @Test
    public void parse_filePathSpecified_success() {
        // have file path
        Path targetFilePath = VALID_FILE_PATH;

        String userInput = " " + PREFIX_FILE_PATH + targetFilePath;
        ImportCommand expectedCommand = new ImportCommand(targetFilePath);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    void parse_invalidFilePath_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_FILE_PATH);

        // empty file path
        assertParseFailure(parser, " " + PREFIX_FILE_PATH,
                expectedMessage);

        // invalid path string
        assertParseFailure(parser, " " + PREFIX_FILE_PATH + "\\>",
                expectedMessage);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, " ", expectedMessage);
    }
}
