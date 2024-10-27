package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

public class RemarkCommandParserTest {

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_validArgs_success() {
        // Valid case with index and remark
        Remark expectedRemark = new Remark(VALID_REMARK_AMY);
        Index index = Index.fromOneBased(1);
        assertParseSuccess(parser, "1 " + REMARK_DESC_AMY, new RemarkCommand(index, expectedRemark));
    }

    @Test
    public void parse_invalidIndex_failure() {
        // Invalid index
        assertParseFailure(parser, "0 " + REMARK_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-1 " + REMARK_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "abc " + REMARK_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidRemark_failure() {
        // Invalid remark
        assertParseFailure(parser, "1 " + INVALID_REMARK_DESC,
                Remark.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsMissing_failure() {
        // Missing all fields
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }

}
