package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindNricCommand;
import seedu.address.model.person.NricContainsKeywordsPredicate;

public class FindNricCommandParserTest {
    private FindNricCommandParser parser = new FindNricCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNricCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindNricCommand expectedFindNricCommand =
                new FindNricCommand(new NricContainsKeywordsPredicate(Arrays.asList(VALID_NRIC_AMY, VALID_NRIC_BOB)));
        assertParseSuccess(parser, VALID_NRIC_AMY + " " + VALID_NRIC_BOB, expectedFindNricCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + VALID_NRIC_AMY
                + " \n \t" + VALID_NRIC_BOB + "  \t", expectedFindNricCommand);
    }

}
