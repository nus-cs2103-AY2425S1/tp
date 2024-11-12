package seedu.internbuddy.logic.parser;

import static seedu.internbuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.internbuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.internbuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.internbuddy.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.logic.commands.DeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        // no whitespace
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_COMPANY));

        // whitespace before
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "1", new DeleteCommand(INDEX_FIRST_COMPANY));

        // whitespace after
        assertParseSuccess(parser, "1" + PREAMBLE_WHITESPACE, new DeleteCommand(INDEX_FIRST_COMPANY));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // non positive integer
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // non integer
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // empty string
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
