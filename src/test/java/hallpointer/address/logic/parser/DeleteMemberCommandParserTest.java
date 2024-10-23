package hallpointer.address.logic.parser;

import static hallpointer.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hallpointer.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static hallpointer.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;

import org.junit.jupiter.api.Test;

import hallpointer.address.logic.commands.DeleteMemberCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteMemberCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteMemberCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteMemberCommandParserTest {

    private DeleteMemberCommandParser parser = new DeleteMemberCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteMemberCommand(INDEX_FIRST_MEMBER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMemberCommand.MESSAGE_USAGE));
    }
}
