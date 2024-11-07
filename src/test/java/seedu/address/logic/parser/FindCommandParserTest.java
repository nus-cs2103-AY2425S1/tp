package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
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
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));

        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }
    @Test
    public void parse_validPhoneArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("912345", "967890")));
        assertParseSuccess(parser, "912345 967890", expectedFindCommand);
        assertParseSuccess(parser, " \n 912345 \n \t 967890  \t", expectedFindCommand);
    }
    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }
    @Test
    public void equals_sameObject_returnsTrue() {
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("12345"));
        assertTrue(predicate.equals(predicate));
    }
    @Test
    public void equals_samePostalObject_returnsTrue() {
        PostalContainsKeywordsPredicate predicate = new PostalContainsKeywordsPredicate(Arrays.asList("S123456"));
        assertTrue(predicate.equals(predicate));
    }
    @Test
    public void parse_emptyInput_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" "));
    }
    @Test
    public void parse_invalidKeyword_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("find Alice -1234"));
    }

}
