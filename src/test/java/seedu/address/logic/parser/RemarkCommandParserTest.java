package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.*;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Remark;


public class RemarkCommandParserTest {

    private static final Object PREFIX_REMARK = "r/";
    private final RemarkCommandParser parser = new RemarkCommandParser();
    private final Remark RANDOM_REMARK = new Remark("random remark!");
    private final Remark SPECIAL_REMARK = new Remark("needs 2 toilets/requires 2 room;//!@#$%^&*()");

    @Test
    public void parse_indexSpecified_success() {
        // valid remark with valid index
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK + RANDOM_REMARK;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, RANDOM_REMARK);
        assertParseSuccess(parser, userInput, expectedCommand);

        // valid remark with "/r" and slightly weirder characters
        String userInput2 = targetIndex.getOneBased() + " " + PREFIX_REMARK + SPECIAL_REMARK;
        RemarkCommand expectedCommand2 = new RemarkCommand(INDEX_FIRST_PERSON, SPECIAL_REMARK);
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
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD + " " + RANDOM_REMARK, failureMessage);
    }


}
