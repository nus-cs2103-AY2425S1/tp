package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains test cases for the ImportCommandParser.
 * Verifies that the parser correctly handles various scenarios,
 * such as valid inputs, missing fields, empty paths, duplicate prefixes, and incorrect file extensions.
 */
public class ImportCommandParserTest {
    private final ImportCommandParser parser = new ImportCommandParser();

    /**
     * Tests that a fully specified and correctly formatted path
     * results in a successful parse and creates the expected ImportCommand.
     */
    @Test
    public void parse_allFieldsSpecified_success() throws ParseException, IOException {
        Path tempFile = Files.createTempFile("validImportParse", ".csv");
        String userInput = " path/" + tempFile.toString();
        try {
            ImportCommand expectedCommand = new ImportCommand(tempFile.toString());
            assertEquals(parser.parse(userInput), expectedCommand);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /**
     * Tests that a missing path/ prefix results in a parse failure
     * with the appropriate error message.
     */
    @Test
    public void parse_notAllFieldSpecified_error() throws ParseException, IOException {
        Path tempFile = Files.createTempFile("validImportParse", ".csv");
        String userInput = tempFile.toString();
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);
        try {
            ImportCommand expectedCommand = new ImportCommand(tempFile.toString());
            assertParseFailure(parser, userInput, expectedMessage);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /**
     * Tests that an empty path (with only "path/" and no file path) results in a parse failure
     * with the error message specifying that the path cannot be empty.
     */
    @Test
    public void parse_emptyPath_error() throws ParseException {
        String userInput = " path/";
        String expectedMessage = "CSV file path cannot be empty!";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    /**
     * Tests that duplicate "path" prefixes result in a parse failure
     * with the appropriate error message indicating a duplicate prefix issue.
     */
    @Test
    public void parse_duplicatePrefix_error() throws ParseException {
        String userInput = " path/yyy path/yyy";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    /**
     * Tests that a duplicate "path" prefix where the second one is empty results in a parse failure
     * with an error message indicating the improper format.
     */
    @Test
    public void parse_duplicatePrefixEmpty_error() throws ParseException {
        String userInput = " path/yyy path/";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    /**
     * Tests that an incorrect file extension (not .csv) results in a parse failure
     * with an error message prompting the user to provide a .csv file.
     */
    @Test
    public void parse_wrongExtension_error() throws ParseException {
        String userInput = " path/lol.fff";
        String expectedMessage = "Invalid file extension. Please provide a .csv file!";
        assertParseFailure(parser, userInput, expectedMessage);
    }

}
