package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_REMARK;

import org.junit.jupiter.api.Test;

import tuteez.logic.commands.AddRemarkCommand;
import tuteez.logic.commands.DeleteRemarkCommand;
import tuteez.logic.commands.RemarkCommand;
import tuteez.model.remark.Remark;

public class RemarkCommandParserTest {

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_addRemark_success() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + AddRemarkCommand.ADD_REMARK_PARAM + " Some remark";
        AddRemarkCommand expectedCommand = new AddRemarkCommand(INDEX_FIRST_PERSON, new Remark("Some remark"));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_deleteRemark_success() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + DeleteRemarkCommand.DELETE_REMARK_PARAM + " 1";
        DeleteRemarkCommand expectedCommand = new DeleteRemarkCommand(INDEX_FIRST_PERSON, INDEX_FIRST_REMARK);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidRemarkType_throwsParseException() {
        assertParseFailure(parser, "1 -x Some remark",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Invalid index for add remark
        assertParseFailure(parser, "-1 " + AddRemarkCommand.ADD_REMARK_PARAM + " Some remark",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));

        // Invalid index for delete remark
        assertParseFailure(parser, "-1 " + DeleteRemarkCommand.DELETE_REMARK_PARAM + " 1 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPersonIndexSpecified_throwsParseException() {
        assertParseFailure(parser, AddRemarkCommand.ADD_REMARK_PARAM + " Some remark",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        assertParseFailure(parser, DeleteRemarkCommand.DELETE_REMARK_PARAM + " 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }
}
