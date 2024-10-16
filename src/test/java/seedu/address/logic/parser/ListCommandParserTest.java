package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListEventCommand;
import seedu.address.logic.commands.ListVendorCommand;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_noArgs_returnsListCommand() {
        assertParseSuccess(parser, "", new ListCommand());
    }

    @Test
    public void parse_validArgs_returnsListCommand() {
        assertParseSuccess(parser, " v/ e/", new ListCommand());
    }

    @Test
    public void parse_validArgs_returnsListEventCommand() {
        assertParseSuccess(parser, " e/", new ListEventCommand());
    }

    @Test
    public void parse_validArgs_returnsListVendorCommand() {
        assertParseSuccess(parser, " v/", new ListVendorCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // trailing inputs
        assertParseFailure(parser, " ev",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));

        // leading inputs
        assertParseFailure(parser, " ev v/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));

        // duplicated prefixes
        assertParseFailure(parser, " v/ v/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}
