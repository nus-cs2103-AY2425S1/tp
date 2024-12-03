package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.FindCommandParser.MESSAGE_CONSTRAINTS;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameOrPhoneContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameOrPhoneContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "  Alice   Bob  ", expectedFindCommand);

        // name with integers
        expectedFindCommand = new FindCommand(
                new NameOrPhoneContainsKeywordsPredicate(Arrays.asList("Alice123")));
        assertParseSuccess(parser, "Alice123", expectedFindCommand);

        // only integers
        expectedFindCommand = new FindCommand(
                new NameOrPhoneContainsKeywordsPredicate(Arrays.asList("12345")));
        assertParseSuccess(parser, "12345", expectedFindCommand);

        // very long keyword
        String longKeyword = "a".repeat(255);
        expectedFindCommand = new FindCommand(
                new NameOrPhoneContainsKeywordsPredicate(Arrays.asList(longKeyword)));
        assertParseSuccess(parser, longKeyword, expectedFindCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "   *+/ ", MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "@!#%", MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "#Alice#", MESSAGE_CONSTRAINTS);
    }

}
