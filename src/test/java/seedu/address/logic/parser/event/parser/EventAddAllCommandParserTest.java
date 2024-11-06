package seedu.address.logic.parser.event.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.commands.EventAddAllCommand;

public class EventAddAllCommandParserTest {
    private EventAddAllCommandParser eventAddAllCommandParser = new EventAddAllCommandParser();
    @Test
    public void parse_invalidInputs_throwsParseException() {
        assertParseFailure(eventAddAllCommandParser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddAllCommand.MESSAGE_USAGE));
        assertParseFailure(eventAddAllCommandParser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddAllCommand.MESSAGE_USAGE));
        assertParseFailure(eventAddAllCommandParser, "cat", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddAllCommand.MESSAGE_USAGE));
        assertParseFailure(eventAddAllCommandParser, "1.5", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddAllCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validInputWithoutWhiteSpace_success() {
        EventAddAllCommand expectedCommand = new EventAddAllCommand(INDEX_FIRST_EVENT);
        assertParseSuccess(eventAddAllCommandParser, "1", expectedCommand);
    }

    @Test
    public void parse_validInputWithWhiteSpace_success() {
        EventAddAllCommand expectedCommand = new EventAddAllCommand(INDEX_FIRST_EVENT);
        assertParseSuccess(eventAddAllCommandParser, "  1    ", expectedCommand);
    }
}
