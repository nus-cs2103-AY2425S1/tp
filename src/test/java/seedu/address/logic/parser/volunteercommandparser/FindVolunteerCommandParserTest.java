package seedu.address.logic.parser.volunteercommandparser;


import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.volunteercommands.FindVolunteerCommand;

public class FindVolunteerCommandParserTest {

    private FindVolunteerCommandParser parser = new FindVolunteerCommandParser();

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // single word
        assertParseSuccess(parser, "John", new FindVolunteerCommand("John"));

        // multiple words
        assertParseSuccess(parser, "John Doe Smith", new FindVolunteerCommand("John Doe Smith"));

        // leading and trailing whitespace
        assertParseSuccess(parser, "   John   ", new FindVolunteerCommand("John"));

        // mixed case
        assertParseSuccess(parser, "jOhN dOe", new FindVolunteerCommand("jOhN dOe"));

        // numbers in search term
        assertParseSuccess(parser, "John123", new FindVolunteerCommand("John123"));

        // special characters
        assertParseFailure(parser, "John@Doe", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindVolunteerCommand.MESSAGE_USAGE));

        // chinese characters
        assertParseFailure(parser, "张三", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindVolunteerCommand.MESSAGE_USAGE));

        // mix of different character types
        assertParseFailure(parser, "John张三@123", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindVolunteerCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty string
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindVolunteerCommand.MESSAGE_USAGE));

        // only spaces
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindVolunteerCommand.MESSAGE_USAGE));

    }

    @Test
    public void parse_edgeCases_returnsFindCommand() {
        // single character
        assertParseSuccess(parser, "a", new FindVolunteerCommand("a"));

        // very long search term (100 characters)
        String longSearchTerm = "a".repeat(1000);
        assertParseFailure(parser, longSearchTerm, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindVolunteerCommand.MESSAGE_USAGE));

        // multiple spaces between words
        assertParseSuccess(parser, "John    Doe", new FindVolunteerCommand("John    Doe"));

        // tab characters
        assertParseFailure(parser, "John\tDoe", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindVolunteerCommand.MESSAGE_USAGE));

        // newline characters
        assertParseFailure(parser, "John\nDoe", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindVolunteerCommand.MESSAGE_USAGE));
    }
}
