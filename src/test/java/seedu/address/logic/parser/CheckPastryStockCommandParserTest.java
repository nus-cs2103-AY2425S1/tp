package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.commands.CheckPastryStockCommand.MESSAGE_USAGE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CheckPastryStockCommand;
/**
 * Contains tests for {@code CheckPastryStockCommandParser}.
 */
public class CheckPastryStockCommandParserTest {

    private final CheckPastryStockCommandParser parser = new CheckPastryStockCommandParser();

    private static final String VALID_PASTRY_NAME = "Chocolate Donut";
    private static final String EMPTY_INPUT = "";
    private static final String WHITESPACE_ONLY = "   ";
    private static final String SPECIAL_CHARACTERS_INPUT = "@!Pastry";

    @Test
    public void parse_validArgs_returnsCheckPastryStockCommand() {
        // Prepare the expected command
        CheckPastryStockCommand expectedCommand = new CheckPastryStockCommand(VALID_PASTRY_NAME);

        // Verify that valid input produces the expected command
        assertParseSuccess(parser, VALID_PASTRY_NAME, expectedCommand);
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        // Verify that empty input throws a ParseException
        assertParseFailure(parser, EMPTY_INPUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        // Verify that input with only whitespace throws a ParseException
        assertParseFailure(parser, WHITESPACE_ONLY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_specialCharactersInput_validCommand() {
        // Prepare the expected command
        CheckPastryStockCommand expectedCommand = new CheckPastryStockCommand(SPECIAL_CHARACTERS_INPUT);

        // Verify that input with special characters is accepted
        assertParseSuccess(parser, SPECIAL_CHARACTERS_INPUT, expectedCommand);
    }

    @Test
    public void parse_extraSpaces_validCommand() {
        // Prepare the expected command
        CheckPastryStockCommand expectedCommand = new CheckPastryStockCommand(VALID_PASTRY_NAME);

        // Verify that valid input with extra spaces is trimmed and accepted
        assertParseSuccess(parser, "   " + VALID_PASTRY_NAME + "   ", expectedCommand);
    }
}
