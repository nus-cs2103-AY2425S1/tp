package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_VALID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.company.Remark;

/**
 * Contains unit tests for RemarkCommandParser.
 */
public class RemarkCommandParserTest {

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    @Disabled
    public void parse_indexSpecified_success() {
        // have remark
        Index targetIndex = Index.fromOneBased(1);
        String userInput = targetIndex.getOneBased() + REMARK_DESC_VALID;
        RemarkCommand expectedCommand = new RemarkCommand(targetIndex, new Remark(VALID_REMARK));
        assertParseSuccess(parser, userInput, expectedCommand);

        // empty remark (deleting remark)
        userInput = targetIndex.getOneBased() + " " + "r/";
        expectedCommand = new RemarkCommand(targetIndex, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noIndexSpecified_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        // no index
        String userInput = REMARK_DESC_VALID;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        // negative index
        assertParseFailure(parser, "-5" + REMARK_DESC_VALID, expectedMessage);

        // zero index
        assertParseFailure(parser, "0" + REMARK_DESC_VALID, expectedMessage);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", expectedMessage);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", expectedMessage);
    }

    @Test
    @Disabled
    public void parse_invalidRemark_failure() {
        String expectedMessage = Remark.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, "1" + INVALID_REMARK_DESC, expectedMessage);
    }
}
