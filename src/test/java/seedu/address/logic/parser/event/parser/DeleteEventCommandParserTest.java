package seedu.address.logic.parser.event.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.commands.DeleteEventCommand;

public class DeleteEventCommandParserTest {
    private DeleteEventCommandParser deleteEventCommandParser = new DeleteEventCommandParser();
    @Test
    public void parse_validInputWithoutWhitespace_success() {
        DeleteEventCommand expectedDeleteEventCommand = new DeleteEventCommand(INDEX_FIRST_EVENT);

        assertParseSuccess(deleteEventCommandParser, "1", expectedDeleteEventCommand);
    }

    @Test
    public void parse_validInputWithWhitespace_success() {
        DeleteEventCommand expectedDeleteEventCommand = new DeleteEventCommand(Index.fromOneBased(5));

        assertParseSuccess(deleteEventCommandParser, "   5   ", expectedDeleteEventCommand);
    }

    // EP: negative integers
    @Test
    public void parse_invalidNegativeInput_throwsParseException() {
        assertParseFailure(deleteEventCommandParser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));
    }

    // EP: Non-integers
    @Test
    public void parse_invalidNonIntegerInput_throwsParseException() {
        assertParseFailure(deleteEventCommandParser, "apple", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));

        assertParseFailure(deleteEventCommandParser, "1.5", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));
    }

    // EP: boundary value 0
    @Test
    public void parse_invalidInputZero_throwsParseException() {
        assertParseFailure(deleteEventCommandParser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));
    }
}
