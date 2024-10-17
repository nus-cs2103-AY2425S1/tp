package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() throws ParseException {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(Arrays.asList("/name", "Alice", "Bob")));
        assertParseSuccess(parser, "/name Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n /name Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsForPhone_returnsFindCommand() throws ParseException {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(Arrays.asList("/contact", "12345", "67890")));
        assertParseSuccess(parser, "/contact 12345 67890", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n /contact 12345 \n \t 67890  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsForAddress_returnsFindCommand() throws ParseException {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(Arrays.asList("/address", "Main", "Street")));
        assertParseSuccess(parser, "/address Main Street", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n /address Main \n \t Street  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsForMixedPrefixes_returnsFindCommand() throws ParseException {
        // Test with mixed prefixes
        FindCommand expectedFindCommand =
                new FindCommand(
                        new PersonContainsKeywordsPredicate(Arrays.asList("/name", "Alice", "/phone", "12345"))
                );
        assertParseSuccess(parser, "/name Alice /phone 12345", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n /name Alice \t /phone 12345  \t", expectedFindCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // No prefixes
        assertParseFailure(parser, "Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // Invalid prefix
        assertParseFailure(parser, "/unknown Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // Only a single slash without a proper prefix
        assertParseFailure(parser, "/ Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}
