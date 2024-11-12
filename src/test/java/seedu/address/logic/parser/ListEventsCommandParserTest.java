package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListEventsCommand;

public class ListEventsCommandParserTest {

    private ListEventsCommandParser parser = new ListEventsCommandParser();

    @Test
    public void parse_emptyInput_success() {
        assertParseSuccess(parser, "", new ListEventsCommand());
    }

    @Test
    public void parse_whitespaceInput_success() {
        assertParseSuccess(parser, "   ", new ListEventsCommand());
    }

    @Test
    public void parse_invalidInput_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListEventsCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " extra input", expectedMessage);
    }
}
