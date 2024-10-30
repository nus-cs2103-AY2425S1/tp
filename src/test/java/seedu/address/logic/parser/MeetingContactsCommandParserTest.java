package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_GENERIC_INDEX_OUT_OF_BOUNDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MeetingContactsCommand;

public class MeetingContactsCommandParserTest {

    private final MeetingContactsCommandParser parser = new MeetingContactsCommandParser();

    @Test
    public void parse_invalidCommandFormat() {
        // empty string input
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MeetingContactsCommand.MESSAGE_USAGE));
        // invalid whitespace
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MeetingContactsCommand.MESSAGE_USAGE));
        // invalid non numeric characters
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MeetingContactsCommand.MESSAGE_USAGE));
        // more than one indices
        assertParseFailure(parser, "1 2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MeetingContactsCommand.MESSAGE_USAGE));
        // out of bounds index
        assertParseFailure(parser, "-1", String.format(MESSAGE_GENERIC_INDEX_OUT_OF_BOUNDS,
            MeetingContactsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validCommandFormat() {
        assertParseSuccess(parser, "1", new MeetingContactsCommand(Index.fromOneBased(1)));
    }
}
