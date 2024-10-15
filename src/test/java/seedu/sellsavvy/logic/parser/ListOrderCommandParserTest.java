package seedu.sellsavvy.logic.parser;
import static seedu.sellsavvy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sellsavvy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.sellsavvy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.logic.commands.ordercommands.ListOrderCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the ListOrderCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the ListOrderCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ListOrderCommandParserTest {
    private ListOrderCommandParser parser = new ListOrderCommandParser();

    @Test
    public void parse_validArgs_returnsListOrderCommand() {
        assertParseSuccess(parser, "1", new ListOrderCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListOrderCommand.MESSAGE_USAGE));
    }
}
