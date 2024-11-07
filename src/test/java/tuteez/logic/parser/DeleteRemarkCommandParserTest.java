package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.Messages.MESSAGE_INVALID_PERSON_INDEX_FORMAT;
import static tuteez.logic.Messages.MESSAGE_INVALID_REMARK_INDEX_FORMAT;
import static tuteez.logic.Messages.MESSAGE_MISSING_REMARK_INDEX;
import static tuteez.logic.Messages.MESSAGE_MISSING_REMARK_INDEX_PREFIX;
import static tuteez.logic.parser.CliSyntax.PREFIX_REMARK_INDEX;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_REMARK;

import org.junit.jupiter.api.Test;

import tuteez.logic.commands.DeleteRemarkCommand;

public class DeleteRemarkCommandParserTest {

    private DeleteRemarkCommandParser parser = new DeleteRemarkCommandParser();

    @Test
    public void parse_deleteRemark_success() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK_INDEX + "1";
        DeleteRemarkCommand expectedCommand = new DeleteRemarkCommand(INDEX_FIRST_PERSON, INDEX_FIRST_REMARK);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_whitespaceOnlyAfterCommandWord_throwsParseException() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRemarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCommandType_throwsParseException() {
        assertParseFailure(parser, "1 -x Some remark",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_REMARK_INDEX_PREFIX));

        assertParseFailure(parser, "1 a/ Some remark",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_REMARK_INDEX_PREFIX));
    }

    @Test
    public void parse_invalidPersonIndex_throwsParseException() {
        assertParseFailure(parser, "0 " + PREFIX_REMARK_INDEX + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, String.format(MESSAGE_INVALID_PERSON_INDEX_FORMAT, "0")));
    }

    @Test
    public void parse_missingCommandType_throwsParseException() {
        assertParseFailure(parser, "1 Some remark",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_REMARK_INDEX_PREFIX));

        assertParseFailure(parser, "1 2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_REMARK_INDEX_PREFIX));
    }

    @Test
    public void parse_missingRemarkIndex_throwsParseException() {
        assertParseFailure(parser, String.format("1 %s", PREFIX_REMARK_INDEX),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_REMARK_INDEX));

        assertParseFailure(parser, String.format("1 %s ", PREFIX_REMARK_INDEX),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_REMARK_INDEX));
    }

    @Test
    public void parse_invalidRemarkIndex_throwsParseException() {
        assertParseFailure(parser, String.format("1 %s 0", PREFIX_REMARK_INDEX),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_REMARK_INDEX_FORMAT));

        assertParseFailure(parser, String.format("1 %s -1", PREFIX_REMARK_INDEX),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_REMARK_INDEX_FORMAT));
    }
}
