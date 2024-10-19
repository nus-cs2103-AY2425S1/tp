package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AdvFilterCommand;

public class AdvFilterCommandParserTest {

    private AdvFilterCommandParser parser = new AdvFilterCommandParser();

    //Test for empty input
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));
    }

    //Test for varying number of space, 1 input and 2 inputs
    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        AdvFilterCommand expectedAdvFilterCommand = new AdvFilterCommand("friend", "=", "yes");
        assertParseSuccess(parser, "t/friend = yes", expectedAdvFilterCommand);

        // multiple whitespaces between filters
        assertParseSuccess(parser, " \n \t t/friend = \n yes \t", expectedAdvFilterCommand);

        // only tag name provided
        assertParseFailure(parser, "t/friend",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));

        // only tag name and operator provided
        assertParseFailure(parser, "t/friend =",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));

        //only tag name and tag value provided
        assertParseFailure(parser, "t/friend yes",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));

        //no spacing between tag name, operator or tag value
        assertParseFailure(parser, "t/friend=yes",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "t/friend =yes",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "t/friend= yes",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));
    }

    //input with flag but no name or tag
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "n/   t/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));
    }

}

