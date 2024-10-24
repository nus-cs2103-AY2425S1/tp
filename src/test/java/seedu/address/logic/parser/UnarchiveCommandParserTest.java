package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LIST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnarchiveCommand;

public class UnarchiveCommandParserTest {

    private UnarchiveCommandParser parser = new UnarchiveCommandParser();

    @Test
    public void parse_validArgs_returnsUnarchiveCommand() {
        assertParseSuccess(parser, "1", new UnarchiveCommand(INDEX_FIRST_LIST));
    }

    @Test
    public void parse_validMultipleArgs_returnsUnarchiveCommand() {
        assertParseSuccess(parser, "1 2", new UnarchiveCommand(INDEX_FIRST_LIST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnarchiveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidMultipleArgs_throwsParseException() {
        assertParseFailure(parser, "1 a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnarchiveCommand.MESSAGE_USAGE));
    }
}
