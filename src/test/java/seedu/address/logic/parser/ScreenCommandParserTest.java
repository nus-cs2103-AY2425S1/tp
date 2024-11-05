package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.Messages.MESSAGE_MISSING_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ScreenJobCommand;

public class ScreenCommandParserTest {

    private ScreenCommandParser parser = new ScreenCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScreenJobCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(parser, "job ",
                String.format(MESSAGE_MISSING_INDEX));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "job abc", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_validArgs_returnsScreenJobCommand() {
        // no leading and trailing whitespaces
        ScreenJobCommand expectedCommand = new ScreenJobCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, "job 1", expectedCommand);

        // multiple whitespaces between arguments
        assertParseSuccess(parser, " \n job \n \t 1  \t", expectedCommand);
    }
}
