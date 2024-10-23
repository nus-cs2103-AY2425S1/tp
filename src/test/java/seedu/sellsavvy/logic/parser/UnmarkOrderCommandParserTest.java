package seedu.sellsavvy.logic.parser;

import static seedu.sellsavvy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sellsavvy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.sellsavvy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST_ORDER;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.logic.commands.ordercommands.UnmarkOrderCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the UnmarkOrderCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeletePersonCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */

public class UnmarkOrderCommandParserTest {

    private UnmarkOrderCommandParser parser = new UnmarkOrderCommandParser();

    @Test
    public void parse_validArgs_returnsMarkOrderCommand() {
        assertParseSuccess(parser, "1", new UnmarkOrderCommand(INDEX_FIRST_ORDER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkOrderCommand.MESSAGE_USAGE));
    }
}
