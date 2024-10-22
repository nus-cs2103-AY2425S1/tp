package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ExportCommandParserTest {
    private final ExportCommandParser parser = new ExportCommandParser();


    @Test
    public void parse_allFieldsSpecified_success() throws ParseException {
        String userInput = "export path/test/data/testExport.csv";
        ExportCommand expectedCommand = new ExportCommand("test/data/testExport.csv");
        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_notAllFieldSpecified_error() throws ParseException {
        String userInput = "export C:\\Users\\User\\Documents\\tp\\src\\test\\data\\testExport.csv";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_notAllFileSpecified_error() throws ParseException {
        String userInput = "export path/";
        String expectedMessage = "File path cannot be empty.";
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
