package seedu.address.logic.parser.volunteercommandparser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.volunteercommands.FindVolunteerCommand;
import seedu.address.model.volunteer.VolunteerNameContainsKeywordPredicate;

public class FindVolunteerCommandParserTest {

    private FindVolunteerCommandParser parser = new FindVolunteerCommandParser();

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // single word
        String searchString = "John";
        VolunteerNameContainsKeywordPredicate predicate = new VolunteerNameContainsKeywordPredicate(searchString);
        assertParseSuccess(parser, searchString, new FindVolunteerCommand(predicate));

        // multiple words
        String multiWordSearch = "John Doe Smith";
        predicate = new VolunteerNameContainsKeywordPredicate(multiWordSearch);
        assertParseSuccess(parser, multiWordSearch, new FindVolunteerCommand(predicate));

        // leading and trailing whitespace
        String searchWithWhitespace = "   John   ";
        predicate = new VolunteerNameContainsKeywordPredicate("John");
        assertParseSuccess(parser, searchWithWhitespace, new FindVolunteerCommand(predicate));

        // mixed case
        String mixedCaseSearch = "jOhN dOe";
        predicate = new VolunteerNameContainsKeywordPredicate("jOhN dOe");
        assertParseSuccess(parser, mixedCaseSearch, new FindVolunteerCommand(predicate));

        // numbers in search term
        String searchWithNumbers = "John123";
        predicate = new VolunteerNameContainsKeywordPredicate("John123");
        assertParseSuccess(parser, searchWithNumbers, new FindVolunteerCommand(predicate));

        // special characters (invalid case)
        assertParseFailure(parser, "John@Doe", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindVolunteerCommand.MESSAGE_USAGE));

        // chinese characters (invalid case)
        assertParseFailure(parser, "张三", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindVolunteerCommand.MESSAGE_USAGE));

        // mix of different character types (invalid case)
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
        String singleCharSearch = "a";
        VolunteerNameContainsKeywordPredicate predicate = new VolunteerNameContainsKeywordPredicate("a");
        assertParseSuccess(parser, singleCharSearch, new FindVolunteerCommand(predicate));

        // very long search term (100 characters)
        String longSearchTerm = "a".repeat(1000);
        assertParseFailure(parser, longSearchTerm, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindVolunteerCommand.MESSAGE_USAGE));

        // multiple spaces between words
        String searchWithSpaces = "John    Doe";
        predicate = new VolunteerNameContainsKeywordPredicate("John    Doe");
        assertParseSuccess(parser, searchWithSpaces, new FindVolunteerCommand(predicate));

        // tab characters
        assertParseFailure(parser, "John\tDoe", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindVolunteerCommand.MESSAGE_USAGE));

        // newline characters
        assertParseFailure(parser, "John\nDoe", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindVolunteerCommand.MESSAGE_USAGE));
    }
}
