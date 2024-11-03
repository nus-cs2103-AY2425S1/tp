package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains test cases for the ExportCommandParser.
 * Verifies that the parser correctly handles various scenarios,
 * such as valid inputs, missing fields, empty paths, duplicate prefixes, and incorrect file extensions.
 */
public class ExportCommandParserTest {
    private final ExportCommandParser parser = new ExportCommandParser();

    /**
     * Tests that a valid file path with the correct "path" prefix results
     * in a successful parse and generates the expected ExportCommand.
     */
    @Test
    public void parse_exportCommandSuccess() {
        String filePath = "test/data/testExport1.csv";
        String userInput = " " + PREFIX_PATH + filePath;
        ExportCommand expectedCommand = new ExportCommand(filePath);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Tests that missing the "path" prefix results in a parse failure,
     * with the appropriate error message indicating the missing prefix.
     */
    @Test
    public void parse_missingPrefix_failure() {
        String userInput = " test/data/testExport.csv";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    /**
     * Tests that an empty path with only the "path/" prefix results in a parse failure,
     * and an error message specifying that the file path cannot be empty.
     */
    @Test
    public void parse_notAllFileSpecified_error() throws ParseException {
        String userInput = " path/";
        String expectedMessage = "File path cannot be empty.";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    /**
     * Tests that duplicate "path" prefixes in the input result in a parse failure
     * with the appropriate error message indicating the improper format.
     */
    @Test
    public void parse_duplicatePath_error() throws ParseException {
        String userInput = " path/lol path/lol";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    /**
     * Tests that a non-empty preamble (text before the first prefix) results in a parse failure,
     * with an error message indicating an invalid command format.
     */
    @Test
    public void parse_preamble_error() throws ParseException {
        String userInput = " lol path/lol";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    /**
     * Tests that an invalid file extension (not ".csv") results in a parse failure,
     * with an error message prompting the user to provide a .csv file.
     */
    @Test
    public void parse_noCsvExtension() throws ParseException {
        String userInput = " path/lol.pdf";
        String expectedMessage = "Invalid file extension. Please provide a file path ending with .csv!";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    /**
     * Tests that a file path with an empty file name (e.g., "path/.csv") results in a parse failure,
     * with an error message indicating an invalid file name before the .csv extension.
     */
    @Test
    public void parse_invalidFileName() throws ParseException {
        String userInput = " path/.csv";
        String expectedMessage = "Invalid file name. Please provide a valid file name before the .csv extension!";
        assertParseFailure(parser, userInput, expectedMessage);
    }

}
