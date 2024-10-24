package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UndoCommand;

public class UndoCommandParserTest {

    @Test
    public void parse_validArgs_returnsUndoCommand() {
        // no leading and trailing whitespaces
        UndoCommandParser parser = new UndoCommandParser();
        assertParseSuccess(parser, "", new UndoCommand());

        // leading and trailing whitespaces
        assertParseSuccess(parser, "  ", new UndoCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // non-empty args
        UndoCommandParser parser = new UndoCommandParser();
        assertParseFailure(parser, "1", UndoCommand.MESSAGE_USAGE);
    }
}
