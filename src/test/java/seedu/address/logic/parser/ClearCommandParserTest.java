package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;

public class ClearCommandParserTest {

    private ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parse_validArgs_returnsClearCommand() {
        // Valid flag for person book
        assertParseSuccess(parser, " -p", new ClearCommand(true));

        // Valid flag for event book
        assertParseSuccess(parser, " -e", new ClearCommand(false));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // No flag provided
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));

        // Invalid flag provided
        assertParseFailure(parser, " -x", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));

        // Extra arguments provided
        assertParseFailure(parser, " -p extra", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
    }
}
