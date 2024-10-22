package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewCommand;

public class ViewCommandParserTest {
    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_validNric_success() {
        // Valid NRICs
        assertParseSuccess(parser, "S1234567A", new ViewCommand("S1234567A"));
        assertParseSuccess(parser, "s1234567a", new ViewCommand("S1234567A"));
        assertParseSuccess(parser, "T7654321Z", new ViewCommand("T7654321Z"));
        assertParseSuccess(parser, "t7654321z", new ViewCommand("T7654321Z"));
    }

    @Test
    public void parse_invalidNricFormat_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);

        // Missing first or last alphabet
        assertParseFailure(parser, "1234567A", expectedMessage);
        assertParseFailure(parser, "S1234567", expectedMessage);

        // Not exactly 9 characters
        assertParseFailure(parser, "S12345678A", expectedMessage);
        assertParseFailure(parser, "S123456", expectedMessage);

        // Special characters or extra letters
        assertParseFailure(parser, "S1234@67A", expectedMessage);
        assertParseFailure(parser, "S12345Z67A", expectedMessage);

        // All numbers
        assertParseFailure(parser, "123456789", expectedMessage);

        // Letters only
        assertParseFailure(parser, "ABCDEFGHI", expectedMessage);
    }
}
