package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

// Solution below adapted from https://se-education.org/guides/tutorials/ab3AddRemark.html
public class RemarkCommandParserTest {

    private final RemarkCommandParser parser = new RemarkCommandParser();
    private final Remark randomRemark = new Remark("random remark!");
    private final Remark specialRemark = new Remark("needs 2 toilets/requires 2 room;//!@#$%^&*()");

    @Test
    public void parse_indexSpecified_success() {
        // valid remark with valid index
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK + randomRemark;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, randomRemark);
        assertParseSuccess(parser, userInput, expectedCommand);

        // valid remark with "/r" and slightly weirder characters
        String userInput2 = targetIndex.getOneBased() + " " + PREFIX_REMARK + specialRemark;
        RemarkCommand expectedCommand2 = new RemarkCommand(INDEX_FIRST_PERSON, specialRemark);
        assertParseSuccess(parser, userInput2, expectedCommand2);

        // empty remark with valid index
        userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK;
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String failureMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, failureMessage);

        // no index
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD + " " + randomRemark, failureMessage);
    }
}
