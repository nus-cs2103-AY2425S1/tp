package hallpointer.address.logic.parser;

import static hallpointer.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hallpointer.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static hallpointer.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_SECOND_MEMBER;

import org.junit.jupiter.api.Test;

import hallpointer.address.logic.commands.DeleteMemberCommand;

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

    @Test
    public void parse_validArgsWithLeadingTrailingSpaces_returnsDeleteCommand() {
        assertParseSuccess(parser, " 1 ", new DeleteMemberCommand(INDEX_FIRST_MEMBER));
    }

    @Test
    public void parse_validArgsWithMultipleSpaces_returnsDeleteCommand() {
        assertParseSuccess(parser, "  2  ", new DeleteMemberCommand(INDEX_SECOND_MEMBER));
    }

    @Test
    public void parse_invalidArgsNegativeIndex_throwsParseException() {
        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMemberCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsZeroIndex_throwsParseException() {
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMemberCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsNonNumeric_throwsParseException() {
        assertParseFailure(parser, "one",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMemberCommand.MESSAGE_USAGE));
    }
}
