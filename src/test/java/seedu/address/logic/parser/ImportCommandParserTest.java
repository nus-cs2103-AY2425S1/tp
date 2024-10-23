package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ImportCommandParserTest {
    private final ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_allFieldsSpecified_success() throws ParseException {
        String userInput = " path/C:\\Users\\User\\Documents\\tp\\src\\test\\data\\testImport.csv";
        ImportCommand expectedCommand = new ImportCommand(
            "C:\\Users\\User\\Documents\\tp\\src\\test\\data\\testImport.csv");
        assertEquals(parser.parse(userInput), expectedCommand);
    }

    @Test
    public void parse_notAllFieldSpecified_error() throws ParseException {
        String userInput = " C:\\Users\\User\\Documents\\tp\\src\\test\\data\\testImport.csv";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyPath_error() throws ParseException {
        String userInput = " path/";
        String expectedMessage = "CSV file path cannot be empty.";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_duplicatePrefix_error() throws ParseException {
        String userInput = " path/yyy path/yyy";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_duplicatePrefixEmpty_error() throws ParseException {
        String userInput = " path/yyy path/";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }


}
