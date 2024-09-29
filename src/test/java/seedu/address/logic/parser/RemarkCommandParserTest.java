package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
//import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RemarkCommand;
//import seedu.address.model.person.Remark;

public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemarkCommand.MESSAGE_USAGE));
    }

    //    @Test
    //    public void parse_validArgs_returnsRemarkCommand() {
    //        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
    //                        + ADDRESS_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_FRIEND,
    //                new RemarkCommand(Index.fromOneBased(1), Remark.of(REMARK_DESC_BOB)));
    //    }
}
