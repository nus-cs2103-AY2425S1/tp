package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.Messages.MESSAGE_INVALID_PERSON_INDEX_FORMAT;
import static tuteez.logic.Messages.MESSAGE_MISSING_PERSON_INDEX;
import static tuteez.logic.Messages.MESSAGE_MISSING_REMARK_PREFIX;
import static tuteez.logic.parser.CliSyntax.PREFIX_REMARK;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import tuteez.logic.commands.AddRemarkCommand;
import tuteez.model.remark.Remark;

public class AddRemarkCommandParserTest {

    private AddRemarkCommandParser parser = new AddRemarkCommandParser();

    @Test
    public void parse_addRemark_success() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + "Some remark";
        AddRemarkCommand expectedCommand = new AddRemarkCommand(INDEX_FIRST_PERSON, new Remark("Some remark"));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_whitespaceOnlyAfterCommandWord_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddRemarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCommandType_throwsParseException() {
        assertParseFailure(parser, "1 -x Some remark",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_REMARK_PREFIX));

        assertParseFailure(parser, "1 a/ Some remark",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_REMARK_PREFIX));
    }

    @Test
    public void parse_missingCommandType_throwsParseException() {
        assertParseFailure(parser, "1 Some remark",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_REMARK_PREFIX));

        assertParseFailure(parser, "1 2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_REMARK_PREFIX));
    }

    @Test
    public void parse_invalidPersonIndex_throwsParseException() {
        // Invalid index for add remark
        assertParseFailure(parser, "0 " + PREFIX_REMARK + " Some remark",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, String.format(MESSAGE_INVALID_PERSON_INDEX_FORMAT, "0")));
    }

    @Test
    public void parse_noPersonIndexSpecified_throwsParseException() {
        assertParseFailure(parser, String.format(" %s Some remark", PREFIX_REMARK),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_PERSON_INDEX));
    }

    @Test
    public void parse_emptyRemarkContent_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_REMARK + "",
                Remark.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "1 " + PREFIX_REMARK + " ",
                Remark.MESSAGE_CONSTRAINTS);
    }
}
