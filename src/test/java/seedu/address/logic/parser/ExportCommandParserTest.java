package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ExportCommandParserTest {
    private final ExportCommandParser parser = new ExportCommandParser();


    @Test
    public void parse_allFieldsSpecified_success() {
        String filePath = "test/data/testExport1.csv";
        String userInput = " " + PREFIX_PATH + filePath;
        ExportCommand expectedCommand = new ExportCommand(filePath);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingPrefix_failure() {
        String userInput = " test/data/testExport.csv";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_notAllFileSpecified_error() throws ParseException {
        String userInput = " path/";
        String expectedMessage = "File path cannot be empty.";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_duplicatePath_error() throws ParseException {
        String userInput = " path/lol path/lol";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_preamble_error() throws ParseException {
        String userInput = " lol path/lol";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }



}
