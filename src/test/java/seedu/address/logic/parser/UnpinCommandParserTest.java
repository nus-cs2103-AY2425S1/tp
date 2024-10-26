package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_OUTOFBOUND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnpinCommand;

public class UnpinCommandParserTest {
    private UnpinCommandParser parser = new UnpinCommandParser();

    @Test
    public void parse_validArgs_returnsUnpinCommand() {
        assertParseSuccess(parser, "1", new UnpinCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_outOfBound_returnsUnpinCommand() {
        // handling of out of bound person is done by Unpin command,
        // as 1000 is still considered a positive integer.
        assertParseSuccess(parser, "1000", new UnpinCommand(INDEX_OUTOFBOUND_PERSON));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpinCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonInteger_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpinCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonPositiveInteger_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpinCommand.MESSAGE_USAGE));
    }
}
