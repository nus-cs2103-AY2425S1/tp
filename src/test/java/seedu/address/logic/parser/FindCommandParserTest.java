package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.TagContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validTagArgs_returnsFindCommand() {
        // Test valid tag-based search
        FindCommand expectedFindCommand =
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList("Diabetic", "G6PD")));
        assertParseSuccess(parser, "tag/Diabetic tag/G6PD", expectedFindCommand);

        // multiple whitespaces between tag keywords
        assertParseSuccess(parser, " \n tag/Diabetic \n \t tag/G6PD  \t", expectedFindCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test invalid argument (e.g., empty string)
        assertParseFailure(parser, "tag/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonAlphanumericArgs_throwsParseException() {
        // Test non-alphanumeric characters in names
        assertParseFailure(parser, "Alice@", "Names or tags cannot contain non-alphanumeric characters");
        assertParseFailure(parser, "Bob#", "Names or tags cannot contain non-alphanumeric characters");

        // Test non-alphanumeric characters in tags
        assertParseFailure(parser, "tag/Diabetic$", "Names or tags cannot contain non-alphanumeric characters");
        assertParseFailure(parser, "tag/G6PD!", "Names or tags cannot contain non-alphanumeric characters");

        // Test a mix of valid and invalid characters
        assertParseFailure(parser, "Alice Bob#", "Names or tags cannot contain non-alphanumeric characters");
        assertParseFailure(parser, "tag/Diabetic G6PD!", "Names or tags cannot contain non-alphanumeric characters");
    }

}
