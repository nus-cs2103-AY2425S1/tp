package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.commands.RemovePastryCommand.MESSAGE_USAGE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemovePastryCommand;
/**
 * Contains tests for {@code RemovePastryCommandParser}.
 */
public class RemovePastryCommandParserTest {

    private final RemovePastryCommandParser parser = new RemovePastryCommandParser();

    private static final String VALID_PASTRY_NAME = "Croissant";
    private static final String EMPTY_INPUT = "";
    private static final String WHITESPACE_ONLY = "   ";

    @Test
    public void parse_validArgs_returnsRemovePastryCommand() {
        // Prepare expected RemovePastryCommand
        RemovePastryCommand expectedCommand = new RemovePastryCommand(VALID_PASTRY_NAME);

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
}

