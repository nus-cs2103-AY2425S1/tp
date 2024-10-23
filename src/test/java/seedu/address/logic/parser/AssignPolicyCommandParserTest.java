package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_POLICY_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NAME_LIFE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AssignPolicyCommand;


public class AssignPolicyCommandParserTest {

    private AssignPolicyCommandParser parser = new AssignPolicyCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_POLICY_NAME_LIFE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignPolicyCommand.MESSAGE_USAGE));

        //no policy details specified
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_POLICY_FORMAT + "\n"
                + AssignPolicyCommand.MESSAGE_USAGE));

    }
}
