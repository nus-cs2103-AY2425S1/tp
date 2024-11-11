package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.BookmarkCommand;

public class BookmarkCommandParserTest {

    private BookmarkCommandParser parser = new BookmarkCommandParser();

    @Test
    public void parse_validArgs_bookmarkCommand() {
        assertParseSuccess(parser, "1", new BookmarkCommand(INDEX_FIRST_COMPANY));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BookmarkCommand.MESSAGE_USAGE));
    }
}
