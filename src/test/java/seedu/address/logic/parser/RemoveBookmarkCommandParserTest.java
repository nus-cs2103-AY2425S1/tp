package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemoveBookmarkCommand;

public class RemoveBookmarkCommandParserTest {
    private RemoveBookmarkCommandParser parser = new RemoveBookmarkCommandParser();

    @Test
    public void parse_validArgs_removeBookmarkCommand() {
        assertParseSuccess(parser, "1", new RemoveBookmarkCommand(INDEX_FIRST_COMPANY));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveBookmarkCommand.MESSAGE_USAGE));
    }
}
