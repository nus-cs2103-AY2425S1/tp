package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;


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
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }
    @Test
    public void parse_validPhoneArgs_returnsFindCommand() {
        // no leading and trailing whitespaces for phone search
        FindCommand expectedFindCommand =
                new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("12345", "67890")));
        assertParseSuccess(parser, "12345 67890", expectedFindCommand);

        // multiple whitespaces between phone number keywords
        assertParseSuccess(parser, " \n 12345 \n \t 67890  \t", expectedFindCommand);
    }
    @Test
    public void parse_mixedNameAndPhoneArgs_returnsFindCommand() {
        // Mixed name and phone number search
        FindCommand expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice"))
                        .or(new PhoneContainsKeywordsPredicate(Arrays.asList("12345")))
        );
        assertParseSuccess(parser, "Alice 12345", expectedFindCommand);

        // multiple whitespaces between mixed keywords
        assertParseSuccess(parser, " \n Alice \n \t 12345  \t", expectedFindCommand);
    }

}
