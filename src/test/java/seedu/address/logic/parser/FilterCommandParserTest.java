package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    //Test for empty input
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    //Test for varying number of space, 1 input and 2 inputs
    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand = new FilterCommand("Alice", "friend");
        assertParseSuccess(parser, "n/Alice t/friend", expectedFilterCommand);

        // multiple whitespaces between filters
        assertParseSuccess(parser, " \n n/Alice \n \t t/friend  \t", expectedFilterCommand);

        // only name provided
        expectedFilterCommand = new FilterCommand("Alice", null);
        assertParseSuccess(parser, "n/Alice", expectedFilterCommand);

        // only tag provided
        expectedFilterCommand = new FilterCommand(null, "friend");
        assertParseSuccess(parser, "t/friend", expectedFilterCommand);
    }

    //input with flag but no name or tag
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "n/   t/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

}

