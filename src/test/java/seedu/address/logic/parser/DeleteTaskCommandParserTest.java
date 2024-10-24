package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TASK_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.model.person.Name;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteTaskCommandParserTest {
    private DeleteTaskCommandParser parser = new DeleteTaskCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + TASK_INDEX_DESC,
                expectedMessage);

        // missing task index prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_TASK_INDEX,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_TASK_INDEX,
                expectedMessage);
    }

    @Test
    public void parse_tooManyArg_throwsParseException() {
        assertParseFailure(parser, " n/alice n/bob ti/1",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
        assertParseFailure(parser, " n/alice ti/1 ti/2",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TASK_INDEX));
    }

    @Test
    public void parse_validArgs_returnsDeleteTaskCommand() {
        // no leading and trailing whitespaces
        DeleteTaskCommand expectedDeleteTaskCommand =
                new DeleteTaskCommand(new Name("Bob Choo"), Index.fromOneBased(1));
        assertParseSuccess(parser, NAME_DESC_BOB + TASK_INDEX_DESC, expectedDeleteTaskCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ Bob Choo  \n ti/ 1 ", expectedDeleteTaskCommand);
    }

    @Test
    public void parse_invalidNameArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_NAME_DESC + TASK_INDEX_DESC, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidIndexArgs_throwsParseException() {
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_TASK_INDEX, MESSAGE_INVALID_INDEX);
    }

}
