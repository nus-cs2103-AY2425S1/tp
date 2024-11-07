package seedu.internbuddy.logic.parser;

import static seedu.internbuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.internbuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.internbuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.internbuddy.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.logic.commands.ReopenCommand;

public class ReopenCommandParserTest {

    private ReopenCommandParser parser = new ReopenCommandParser();

    @Test
    public void parse_validArgs_returnsReopenCommand() {
        assertParseSuccess(parser, "1", new ReopenCommand(INDEX_FIRST_COMPANY));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReopenCommand.MESSAGE_USAGE));
    }
}
