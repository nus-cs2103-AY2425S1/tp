package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AdvFilterCommand;
import seedu.address.logic.commands.AdvFilterCommand.Operator;

public class AdvFilterCommandParserTest {

    private final AdvFilterCommandParser parser = new AdvFilterCommandParser();

    //Test for empty input
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AdvFilterCommand.MESSAGE_USAGE));
    }

    //Test for varying number of space, 1 input and 2 inputs
    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespacesc
        AdvFilterCommand expectedAdvFilterCommand = new AdvFilterCommand("friend", Operator.EQUAL, "yes");
        assertParseSuccess(parser, "t\\friend = yes", expectedAdvFilterCommand);

        // multiple whitespaces between filters
        assertParseSuccess(parser, " \n \t t\\friend = \n yes \t", expectedAdvFilterCommand);
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        // only tag name provided
        assertParseFailure(parser, "t\\friend",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));

        // only tag name and operator provided
        assertParseFailure(parser, "t\\friend =",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));

        // only tag name and tag value provided
        assertParseFailure(parser, "t\\friend yes",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidSpacing_throwsParseException() {
        // no spacing between tag name, operator or tag value
        assertParseFailure(parser, "t\\friend=yes",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "t\\friend =yes",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "t\\friend= yes",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));
    }

    //input with flag but no name or tag
    @Test
    public void parse_invalidArgs_throwsParseException() {
        // input with flag but no name or tag
        assertParseFailure(parser, "n\\   t\\",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_differentOperators_returnsFilterCommand() {
        // Test different valid operators
        assertParseSuccess(parser, "t\\age > 18", new AdvFilterCommand("age", Operator.GREATER_THAN, "18"));
        assertParseSuccess(parser, "t\\salary < 5000", new AdvFilterCommand("salary", Operator.LESS_THAN, "5000"));
        assertParseSuccess(parser, "t\\name != John", new AdvFilterCommand("name", Operator.NOT_EQUAL, "John"));
    }

    @Test
    public void parse_multipleTagsOrValues_throwsParseException() {
        // Test multiple tags or values
        assertParseFailure(parser, "t\\age \\name = John",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "t\\age = 18 20",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nullOrEmptyFields_throwsParseException() {
        // Test null tag
        assertParseFailure(parser, "t\\ = value",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));

        // Test empty tag
        assertParseFailure(parser, "t\\ = value",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));

        // Test null operator
        assertParseFailure(parser, "t\\tag  value",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));

        // Test empty operator
        assertParseFailure(parser, "t\\tag  value",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));

        // Test null value
        assertParseFailure(parser, "t\\tag = ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));

        // Test empty value
        assertParseFailure(parser, "t\\tag = ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));

        // Test all fields null or empty
        assertParseFailure(parser, "t\\   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_extraSpacesBetweenOperatorAndValue_returnsFilterCommand() {
        AdvFilterCommand expectedCommand = new AdvFilterCommand("age", Operator.EQUAL, "25");
        assertParseSuccess(parser, "t\\age =   25", expectedCommand);
    }
}


