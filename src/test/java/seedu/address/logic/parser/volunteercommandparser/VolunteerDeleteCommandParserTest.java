package seedu.address.logic.parser.volunteercommandparser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.volunteercommands.VolunteerDeleteCommand;

public class VolunteerDeleteCommandParserTest {

    private VolunteerDeleteCommandParser parser = new VolunteerDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        // Test with first index
        assertParseSuccess(parser, "1", new VolunteerDeleteCommand(INDEX_FIRST));

        // Test with second index
        assertParseSuccess(parser, "2", new VolunteerDeleteCommand(INDEX_SECOND));

        // Test with whitespace before and after
        assertParseSuccess(parser, "   1   ", new VolunteerDeleteCommand(INDEX_FIRST));

        // Test with larger index number
        assertParseSuccess(parser, "999", new VolunteerDeleteCommand(Index.fromOneBased(999)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty string
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerDeleteCommand.MESSAGE_USAGE));

        // spaces only
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerDeleteCommand.MESSAGE_USAGE));

        // letters instead of numbers
        assertParseFailure(parser, "abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerDeleteCommand.MESSAGE_USAGE));

        // negative numbers
        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerDeleteCommand.MESSAGE_USAGE));

        // zero (index should start from 1)
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerDeleteCommand.MESSAGE_USAGE));

        // decimal numbers
        assertParseFailure(parser, "1.5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerDeleteCommand.MESSAGE_USAGE));

        // multiple indices
        assertParseFailure(parser, "1 2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerDeleteCommand.MESSAGE_USAGE));

        // index with extra characters
        assertParseFailure(parser, "1a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerDeleteCommand.MESSAGE_USAGE));
    }
}
