package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

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
        ViewEventCommand expectedViewEventCommandNoSpaces = new ViewEventCommand(new Event("test"));
        ViewEventCommand expectedViewEventCommandWithSpaces = new ViewEventCommand(new Event("test test test"));

        // no trailing whitespaces
        assertParseSuccess(parser, "test", expectedViewEventCommandNoSpaces);
        assertParseSuccess(parser, "test test test", expectedViewEventCommandWithSpaces);

        // with trailing whitespaces
        assertParseSuccess(parser, "  test   ", expectedViewEventCommandNoSpaces);
        assertParseSuccess(parser, "      test test test     ", expectedViewEventCommandWithSpaces);
    }
}
