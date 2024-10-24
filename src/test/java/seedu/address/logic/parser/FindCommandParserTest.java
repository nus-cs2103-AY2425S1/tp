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
import seedu.address.model.person.*;

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
                new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("12345", "67890")));
        assertParseSuccess(parser, "12345 67890", expectedFindCommand);
        assertParseSuccess(parser, " \n 12345 \n \t 67890  \t", expectedFindCommand);
    }

    @Test
    public void parse_validPostal_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PostalContainsKeywordsPredicate(Arrays.asList("123000", "123456")));
        assertParseSuccess(parser, "123000 123456", expectedFindCommand);
        assertParseSuccess(parser, " \n 123000 \n \t 123456  \t", expectedFindCommand);
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
    public void parse_emptyInput_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" "));
    }
}
