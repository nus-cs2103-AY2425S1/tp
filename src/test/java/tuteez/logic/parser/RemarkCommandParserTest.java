package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_EMPTY_REMARK;
import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.Messages.MESSAGE_INVALID_PERSON_INDEX_FORMAT;
import static tuteez.logic.Messages.MESSAGE_INVALID_REMARK_INDEX_FORMAT;
import static tuteez.logic.Messages.MESSAGE_MISSING_PERSON_INDEX;
import static tuteez.logic.Messages.MESSAGE_MISSING_REMARK_INDEX;
import static tuteez.logic.Messages.MESSAGE_REMARK_MISSING_COMMAND_TYPE;
import static tuteez.logic.Messages.MESSAGE_REMARK_MULTIPLE_OPERATIONS;
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
    public void parse_whitespaceOnlyAfterCommandWord_throwsParseException() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleCommandTypes_throwsParseException() {
        assertParseFailure(parser,
                String.format("1 %s Some remark %s 2", AddRemarkCommand.ADD_REMARK_PARAM,
                        DeleteRemarkCommand.DELETE_REMARK_PARAM),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_REMARK_MULTIPLE_OPERATIONS));
    }

    @Test
    public void parse_invalidCommandType_throwsParseException() {
        assertParseFailure(parser, "1 -x Some remark",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_REMARK_MISSING_COMMAND_TYPE));

        assertParseFailure(parser, "1 a/ Some remark",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_REMARK_MISSING_COMMAND_TYPE));
    }

    @Test
    public void parse_missingCommandType_throwsParseException() {
        assertParseFailure(parser, "1 Some remark",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_REMARK_MISSING_COMMAND_TYPE));

        assertParseFailure(parser, "1 2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_REMARK_MISSING_COMMAND_TYPE));
    }

    @Test
    public void parse_invalidPersonIndex_throwsParseException() {
        // Invalid index for add remark
        assertParseFailure(parser, "0 " + AddRemarkCommand.ADD_REMARK_PARAM + " Some remark",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_PERSON_INDEX_FORMAT));

        // Invalid index for delete remark
        assertParseFailure(parser, "-1 " + DeleteRemarkCommand.DELETE_REMARK_PARAM + " 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_PERSON_INDEX_FORMAT));
    }

    @Test
    public void parse_noPersonIndexSpecified_throwsParseException() {
        assertParseFailure(parser, String.format(" %s Some remark", AddRemarkCommand.ADD_REMARK_PARAM),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_PERSON_INDEX));
        assertParseFailure(parser, String.format(" %s 1", DeleteRemarkCommand.DELETE_REMARK_PARAM),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_PERSON_INDEX));
    }

    @Test
    public void parse_emptyRemarkContent_throwsParseException() {
        assertParseFailure(parser, "1 " + AddRemarkCommand.ADD_REMARK_PARAM + "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_EMPTY_REMARK));

        assertParseFailure(parser, "1 " + AddRemarkCommand.ADD_REMARK_PARAM + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_EMPTY_REMARK));
    }

    @Test
    public void parse_missingRemarkIndex_throwsParseException() {
        assertParseFailure(parser, String.format("1 %s", DeleteRemarkCommand.DELETE_REMARK_PARAM),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_REMARK_INDEX));

        assertParseFailure(parser, String.format("1 %s ", DeleteRemarkCommand.DELETE_REMARK_PARAM),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_REMARK_INDEX));
    }

    @Test
    public void parse_invalidRemarkIndex_throwsParseException() {
        assertParseFailure(parser, String.format("1 %s 0", DeleteRemarkCommand.DELETE_REMARK_PARAM),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_REMARK_INDEX_FORMAT));

        assertParseFailure(parser, String.format("1 %s -1", DeleteRemarkCommand.DELETE_REMARK_PARAM),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_REMARK_INDEX_FORMAT));
    }
}
