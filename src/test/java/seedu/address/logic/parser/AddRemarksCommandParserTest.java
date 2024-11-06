package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_REMARK;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddRemarksCommand;
import seedu.address.model.person.Remark;

public class AddRemarksCommandParserTest {

    private AddRemarksCommandParser parser = new AddRemarksCommandParser();

    @Test
    public void parse_validArgs_returnsAddNotesCommand() {
        String userInput = " " + PREFIX_ID + VALID_ID_AMY + " " + PREFIX_REMARK + VALID_REMARK_AMY;
        AddRemarksCommand expectedCommand = new AddRemarksCommand(VALID_ID_AMY, new Remark(VALID_REMARK_AMY));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidId_throwsParseException() {
        String userInput = " " + PREFIX_ID + "abc" + " " + PREFIX_REMARK + VALID_REMARK_AMY;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_ID);
    }

    @Test
    public void parse_emptyRemarks_throwsParseException() {
        String userInput = " " + PREFIX_ID + "abc" + " " + PREFIX_REMARK + " ";
        assertParseFailure(parser, userInput, MESSAGE_EMPTY_REMARK);
    }

    @Test
    public void parse_missingRemark_throwsParseException() {
        String userInput = " " + PREFIX_ID + VALID_ID_AMY;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemarksCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingId_throwsParseException() {
        String userInput = " " + PREFIX_REMARK + VALID_REMARK_AMY;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemarksCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        String userInput = " " + PREFIX_ID + VALID_ID_AMY + " "
                + PREFIX_ID + VALID_ID_AMY + " " + PREFIX_REMARK + VALID_REMARK;
        String expectedMessage = "Multiple values specified for the following single-valued field(s): z/";
        assertParseFailure(parser, userInput, expectedMessage);
    }
}


