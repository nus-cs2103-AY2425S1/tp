package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NAME_INVESTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PaidCommand;

public class PaidCommandParserTest {

    private PaidCommandParser parser = new PaidCommandParser();

    @Test
    public void parse_validArgs_returnsPaidCommand() {
        assertParseSuccess(parser, "1 " + PREFIX_POLICY_NAME + VALID_POLICY_NAME_INVESTMENT,
                new PaidCommand(Index.fromOneBased(1), VALID_POLICY_NAME_INVESTMENT));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a " + VALID_POLICY_NAME_INVESTMENT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaidCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPolicyName_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaidCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArgs_throwsParseException() {
        assertParseFailure(parser, "1 " + VALID_POLICY_NAME_INVESTMENT + " extra",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaidCommand.MESSAGE_USAGE));
    }
}
