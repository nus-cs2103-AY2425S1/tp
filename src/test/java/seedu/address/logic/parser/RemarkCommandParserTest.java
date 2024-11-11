package seedu.address.logic.parser;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalNrics.NRIC_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Remark;

public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();
    private final String nonEmptyRemark = "Some remark";
    @Test
    public void parse_nricSpecified_success() {
        // have remark
        Nric targetNric = NRIC_FIRST_PERSON;
        String userInput = targetNric + " " + PREFIX_REMARK + nonEmptyRemark;
        RemarkCommand expectedCommand = new RemarkCommand(NRIC_FIRST_PERSON, new Remark(nonEmptyRemark));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no remark
        userInput = targetNric + " " + PREFIX_REMARK;
        expectedCommand = new RemarkCommand(NRIC_FIRST_PERSON, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessageNoParameters = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);
        String expectedMessageInvalidNric = String.format(MESSAGE_INVALID_NRIC);
        // no parameters
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessageNoParameters);
        // no nric
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD + " " + nonEmptyRemark, expectedMessageInvalidNric);
        //invalid nric
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD + "123" + nonEmptyRemark, expectedMessageInvalidNric);
    }
}
