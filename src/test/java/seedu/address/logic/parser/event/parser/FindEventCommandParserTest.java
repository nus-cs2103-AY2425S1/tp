package seedu.address.logic.parser.event.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.commands.FindEventCommand;


public class FindEventCommandParserTest {
    private FindEventCommandParser parser = new FindEventCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindEventCommand() {
        FindEventCommand expectedFindEventCommandNoSpaces = new FindEventCommand(Index.fromOneBased(1));
        FindEventCommand expectedFindEventCommandWithSpaces = new FindEventCommand(Index.fromOneBased(2));

        // no trailing whitespaces
        assertParseSuccess(parser, "1", expectedFindEventCommandNoSpaces);
        assertParseSuccess(parser, "2", expectedFindEventCommandWithSpaces);

        // with trailing whitespaces
        assertParseSuccess(parser, "  1   ", expectedFindEventCommandNoSpaces);
        assertParseSuccess(parser, "      2     ", expectedFindEventCommandWithSpaces);
    }
}
