package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.contact.Name;

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
    public void parse_validInteger_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_CONTACT));
    }

    @Test
    public void parse_validNameArgs_returnsDeleteCommand() {
        Name name = new Name(VALID_NAME_AMY);
        DeleteCommand expectedCommand = new DeleteCommand(name);
        String userInput = " " + PREFIX_NAME + VALID_NAME_AMY;
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validNameArgsNoPrefix_returnsDeleteCommand() {
        // does not look if input matches address book
        String input = "a";
        Name name = new Name(input);
        DeleteCommand expectedCommand = new DeleteCommand(name);
        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_invalidNameArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_NAME_DESC,
                ParserUtil.MESSAGE_INVALID_NAME_FIELD);
    }

    @Test
    public void parse_attemptToDeleteBothWays_throwsParseException() {
        assertParseFailure(parser,
                "1 " + PREFIX_NAME + VALID_NAME_AMY,
                DeleteCommand.MESSAGE_DELETE_MULTIPLE_WAYS_FORBIDDEN);
    }

    @Test
    public void parse_attemptToDeleteWithBlankName_throwsParseException() {
        assertParseFailure(parser, "    ", DeleteCommand.MESSAGE_MISSING_INDEX_OR_FULL_NAME);
    }

    @Test
    public void parse_attemptToDeleteByInvalidInt_throwsParseException() {
        // num too big
        assertParseFailure(parser, Integer.MAX_VALUE + "1", ParserUtil.MESSAGE_INVALID_INDEX);

        // negative num
        assertParseFailure(parser, "-1", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_attemptToDeleteByInvalidName_throwsParseException() {
        assertParseFailure(parser, "James&", Messages.MESSAGE_INVALID_NAME_FIELD_INPUT);
    }

}
