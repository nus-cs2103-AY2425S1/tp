package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONCERT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteConcertCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations outside of
 * the DeleteConcertCommand code. For example, inputs "1" and "1 abc" take the same path through the
 * DeleteConcertCommand, and therefore we test only one of them. The path variation for those two
 * cases occur inside the ParserUtil, and therefore should be covered by the ParserUtilTest.
 */
public class DeleteConcertCommandParserTest {

    private DeleteConcertCommandParser parser = new DeleteConcertCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteConcertCommand() {
        assertParseSuccess(parser, "1", new DeleteConcertCommand(INDEX_FIRST_CONCERT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX);
    }
}
