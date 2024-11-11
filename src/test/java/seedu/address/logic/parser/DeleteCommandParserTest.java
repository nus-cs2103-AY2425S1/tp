package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
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
    public void parse_validSingleArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, " " + PREFIX_ID + " 1", new DeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidSingleArgs_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_ID + " a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validMultipleArgs_returnsDeleteCommand() {
        ArrayList<Index> targetIndexes = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        assertParseSuccess(parser, " " + PREFIX_ID + "1, 2", new DeleteCommand(targetIndexes));
    }

    @Test
    public void parse_validDuplicateArgs_returnsDeleteCommand() {
        ArrayList<Index> targetIndexes = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON));
        assertParseSuccess(parser, " " + PREFIX_ID + "1, 1", new DeleteCommand(targetIndexes));
    }

    @Test
    public void parse_invalidMultipleArgs_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_ID + "1, a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
