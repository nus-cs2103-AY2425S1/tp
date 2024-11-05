package seedu.address.logic.parser.event.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.commands.ViewEventCommand;
import seedu.address.model.event.Event;


public class ViewEventCommandParserTest {
    private ViewEventCommandParser parser = new ViewEventCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsViewEventCommand() {
        ViewEventCommand expectedViewEventCommandNoSpaces = new ViewEventCommand(Index.fromOneBased(1));
        ViewEventCommand expectedViewEventCommandWithSpaces = new ViewEventCommand(Index.fromOneBased(2));

        // no trailing whitespaces
        assertParseSuccess(parser, "1", expectedViewEventCommandNoSpaces);
        assertParseSuccess(parser, "2", expectedViewEventCommandWithSpaces);

        // with trailing whitespaces
        assertParseSuccess(parser, "  1   ", expectedViewEventCommandNoSpaces);
        assertParseSuccess(parser, "      2     ", expectedViewEventCommandWithSpaces);
    }
}
