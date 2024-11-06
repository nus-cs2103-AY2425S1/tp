package seedu.address.logic.parser.volunteercommandparser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.volunteercommands.VolunteerViewCommand;

public class VolunteerViewCommandParserTest {

    private VolunteerViewCommandParser parser = new VolunteerViewCommandParser();

    @Test
    public void parse_validArgs_returnsVolunteerViewCommand() {
        // Test with first index
        assertParseSuccess(parser, "1", new VolunteerViewCommand(INDEX_FIRST));

        // Test with second index
        assertParseSuccess(parser, "2", new VolunteerViewCommand(INDEX_SECOND));

        // Test with whitespace before and after
        assertParseSuccess(parser, "  1  ", new VolunteerViewCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty string
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerViewCommand.MESSAGE_USAGE));

        // letters instead of numbers
        assertParseFailure(parser, "abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerViewCommand.MESSAGE_USAGE));

        // negative numbers
        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerViewCommand.MESSAGE_USAGE));

        // zero (index should start from 1)
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerViewCommand.MESSAGE_USAGE));

        // decimal numbers
        assertParseFailure(parser, "1.5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerViewCommand.MESSAGE_USAGE));

        // multiple indices
        assertParseFailure(parser, "1 2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerViewCommand.MESSAGE_USAGE));
    }
}
