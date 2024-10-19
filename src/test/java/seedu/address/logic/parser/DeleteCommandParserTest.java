package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DELETE_EMPTY_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_OVERFLOW_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;

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
    public void parse_invalidArgs_throwsParseException() {
        String errorMessage = String.format("%s \n%s",
                MESSAGE_INVALID_INDEX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "a", errorMessage);
    }

    @Test
    public void parse_prefixPositive_throwsParseException() {
        String errorMessage = String.format("%s \n%s",
                MESSAGE_INVALID_INDEX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "+", errorMessage);
    }

    @Test
    public void parse_prefixNegative_throwsParseException() {
        String errorMessage = String.format("%s \n%s",
                MESSAGE_INVALID_INDEX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-", errorMessage);
    }

    @Test
    public void parse_overflowArgs_throwsParseException() {
        String errorMessage = String.format("%s \n%s",
                MESSAGE_OVERFLOW_INDEX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "2147483648", errorMessage);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        String errorMessage = String.format("%s \n%s",
                MESSAGE_DELETE_EMPTY_INDEX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "", errorMessage);
    }
}
