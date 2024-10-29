package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
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
        // no leading and trailing whitespaces on names
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);

        // multiple whitespaces between name keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t", expectedFindCommand);

        // multiple whitespaces between phone keywords
        FindCommand expectedFindCommandForPhone =
                new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("9345", "1234")));
        assertParseSuccess(parser, " p/9345 1234", expectedFindCommandForPhone);

        // whitespaces allowed in address and multiple "_" between address keywords
        FindCommand expectedFindCommandForAddress =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList("Wall Street", "Michigan")));
        assertParseSuccess(parser, " a/Wall Street_Michigan", expectedFindCommandForAddress);

        // name contains 'n/' prefix
        FindCommand expectedFindCommandNameContainsPrefix =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("n/Alice", "Bobn/")));
        assertParseSuccess(parser, " n/n/Alice Bobn/", expectedFindCommandNameContainsPrefix);

        // address contains 'a/' prefix
        FindCommand expectedFindCommandAddressContainsPrefix =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList("Walla/ Street")));
        assertParseSuccess(parser, " a/Walla/ Street", expectedFindCommandAddressContainsPrefix);
    }

}
