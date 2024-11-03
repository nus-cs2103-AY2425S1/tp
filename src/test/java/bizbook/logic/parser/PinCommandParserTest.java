package bizbook.logic.parser;

import static bizbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static bizbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static bizbook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static bizbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static bizbook.testutil.TypicalIndexes.INDEX_OUTOFBOUND_PERSON;

import org.junit.jupiter.api.Test;

import bizbook.logic.commands.PinCommand;

public class PinCommandParserTest {

    private PinCommandParser parser = new PinCommandParser();

    @Test
    public void parse_validArgs_returnsPinCommand() {
        assertParseSuccess(parser, "1", new PinCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_outOfBound_returnsPinCommand() {
        // handling of out of bound person is done by pin command,
        // as 1000 is still considered a positive integer.
        assertParseSuccess(parser, "1000", new PinCommand(INDEX_OUTOFBOUND_PERSON));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonInteger_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonPositiveInteger_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE));
    }

}
