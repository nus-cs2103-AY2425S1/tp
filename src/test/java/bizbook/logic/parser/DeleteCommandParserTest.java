package bizbook.logic.parser;

import static bizbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static bizbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static bizbook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static bizbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static bizbook.testutil.TypicalIndexes.INDEX_OUTOFBOUND_PERSON;

import org.junit.jupiter.api.Test;

import bizbook.logic.commands.DeleteCommand;

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
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_outOfBound_returnsDeleteCommand() {
        // handling of out of bound person is done by delete command,
        // as 1000 is still considered a positive integer.
        assertParseSuccess(parser, "1000", new DeleteCommand(INDEX_OUTOFBOUND_PERSON));
    }

    @Test
    public void parse_nonInteger_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonPositiveInteger_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
