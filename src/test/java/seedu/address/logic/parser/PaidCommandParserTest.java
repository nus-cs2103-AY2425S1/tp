package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PaidCommand;

public class PaidCommandParserTest {

    private PaidCommandParser parser = new PaidCommandParser();

    @Test
    public void parse_validArgs_returnsPaidCommand() {
        assertParseSuccess(parser, "1 " + PREFIX_POLICY + "1",
                new PaidCommand(Index.fromOneBased(1), Index.fromOneBased(1)));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a " + PREFIX_POLICY + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaidCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPolicyIndex_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaidCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArgs_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_POLICY + "1 extra",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaidCommand.MESSAGE_USAGE));
    }
}
