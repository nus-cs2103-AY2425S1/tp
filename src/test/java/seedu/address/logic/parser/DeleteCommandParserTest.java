package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
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
        assertParseSuccess(parser, "1", new DeleteCommand(new Index[]{INDEX_FIRST_PERSON}));
    }

    @Test
    public void parse_validMultipleArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1 2",
                new DeleteCommand(new Index[]{INDEX_FIRST_PERSON, INDEX_SECOND_PERSON}));

        assertParseSuccess(parser, "2 1",
                new DeleteCommand(new Index[]{INDEX_SECOND_PERSON, INDEX_FIRST_PERSON}));
    }

    @Test
    public void parse_validMultipleArgsMultipleSpaces_returnsDeleteCommand() {
        assertParseSuccess(parser, "   1    3 2   ",
                new DeleteCommand(new Index[]{INDEX_FIRST_PERSON, INDEX_THIRD_PERSON, INDEX_SECOND_PERSON}));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String invalidIndex = "a";
        assertParseFailure(parser, invalidIndex,
                Messages.getErrorMessageWithUsage(
                        String.format(MESSAGE_INVALID_INDEX, invalidIndex), DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidMultipleArgs_throwsParseException() {
        String indices = "1 a b";
        assertParseFailure(parser, indices,
                Messages.getErrorMessageWithUsage(
                        String.format(MESSAGE_INVALID_INDEX, "a"), DeleteCommand.MESSAGE_USAGE));
    }
}
