package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WEDDING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_WEDDING;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameMatchesKeywordPredicate;

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
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_PERSON, null, null));

        DeleteCommand expectedDeleteCommand =
                new DeleteCommand(null, new NameMatchesKeywordPredicate(Arrays.asList("Alice", "Bob")), null);
        assertParseSuccess(parser, "Alice Bob", expectedDeleteCommand);

        assertParseSuccess(parser, " \n Alice Bob  \t", expectedDeleteCommand);

        assertParseSuccess(parser, " \n Alice       Bob  \t", expectedDeleteCommand);
    }


    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noSpaceBetweenIndexAndPrefix_throwsParseException() {
        // Test for wedding prefix
        String weddingInput = "1w/1";
        ParseException pe = assertThrows(ParseException.class, () -> parser.parse(weddingInput));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE),
                pe.getMessage());
    }
    @Test
    public void parse_validRemoveWeddingJobs_returnsDeleteCommand() {
        // Test for wedding prefix
        assertParseSuccess(parser, "1 w/1 w/2", new DeleteCommand(INDEX_FIRST_PERSON,
                null, Set.of(INDEX_FIRST_WEDDING, INDEX_SECOND_WEDDING)));

    }

}
