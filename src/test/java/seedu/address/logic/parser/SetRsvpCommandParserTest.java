package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SetRsvpCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the SetRsvpCommandParser code. For example, inputs "1 1" and "1 abc" take the
 * same path through the SetRsvpCommandParser, and therefore we test only one of them.
 */
public class SetRsvpCommandParserTest {

    private SetRsvpCommandParser parser = new SetRsvpCommandParser();

    @Test
    public void parse_validArgs_returnsSetRsvpCommand() {
        // Test for "Coming"
        assertParseSuccess(parser, "1 1", new SetRsvpCommand(INDEX_FIRST_PERSON, 1));

        // Test for "Not Coming"
        assertParseSuccess(parser, "1 2", new SetRsvpCommand(INDEX_FIRST_PERSON, 2));

        // Test for "Pending"
        assertParseSuccess(parser, "1 3", new SetRsvpCommand(INDEX_FIRST_PERSON, 3));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid index
        assertParseFailure(parser, "a 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRsvpCommand.MESSAGE_USAGE));

        // Invalid action number
        assertParseFailure(parser, "1 4", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRsvpCommand.MESSAGE_USAGE));

        // Missing arguments
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRsvpCommand.MESSAGE_USAGE));
    }
}
