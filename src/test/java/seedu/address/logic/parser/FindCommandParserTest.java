package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.findcommand.FindAddressCommand;
import seedu.address.logic.commands.findcommand.FindCommand;
import seedu.address.logic.commands.findcommand.FindEmailCommand;
import seedu.address.logic.commands.findcommand.FindNameCommand;
import seedu.address.logic.commands.findcommand.FindPhoneCommand;
import seedu.address.logic.commands.findcommand.FindTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.keywordspredicate.AddressContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.EmailContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.NameContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.TagContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, "find amy",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validFindNameArgs_returnsFindNameCommand() {
        // no leading and trailing whitespaces
        FindNameCommand expectedFindCommand =
                new FindNameCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "find n/Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find n/ \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_missingNameAfterPrefix_throwsParseException() {
        String input = "find n/"; // Input with empty name prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals("Name cannot be empty!", thrown.getMessage());

    }

    @Test
    public void parse_missingNameWithTrailingWhiteSpace_throwsParseException() {
        String input = "find n/ \n \t"; // Input with empty name prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals("Name cannot be empty!", thrown.getMessage());
    }

    @Test
    public void parse_validFindAddressArgs_returnsFindAddressCommand() {
        // no leading and trailing whitespaces
        FindAddressCommand expectedFindCommand =
                new FindAddressCommand(new AddressContainsKeywordsPredicate(Arrays.asList("5 Clementi Ave 4 "
                        + "#03-945")));
        assertParseSuccess(parser, "find a/5 Clementi Ave 4 #03-945", expectedFindCommand);

        // multiple whitespaces before and after keywords
        assertParseSuccess(parser, "find a/ \n5 Clementi Ave 4 #03-945  \t", expectedFindCommand);
    }

    @Test
    public void parse_validMultipleFindAddressArgs_returnsFindAddressCommand() {
        // no leading and trailing whitespaces
        FindAddressCommand expectedFindCommand =
                new FindAddressCommand(new AddressContainsKeywordsPredicate(Arrays.asList("5 Clementi Ave 4 "
                        + "#03-945", "Lorong 1")));
        assertParseSuccess(parser, "find a/5 Clementi Ave 4 #03-945 a/Lorong 1", expectedFindCommand);

        // multiple whitespaces before and after keywords
        assertParseSuccess(parser, "find a/ \n5 Clementi Ave 4 #03-945  \t a/ \t Lorong 1", expectedFindCommand);
    }

    @Test
    public void parse_missingAddressAfterPrefix_throwsParseException() {
        String input = "find a/"; // Input with empty name prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals("Address cannot be empty!", thrown.getMessage());
    }

    @Test
    public void missingAddressWithTrailingWhiteSpace_throwsParseException() {
        String input = "find a/ \t \n"; // Input with empty name prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals("Address cannot be empty!", thrown.getMessage());
    }

    @Test
    public void missingAddressWithTrailingWhiteSpace2_throwsParseException() {
        String input = "find a/ Woodlands Avenue 3 a/\t \n"; // Input with empty name prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals("Address cannot be empty!", thrown.getMessage());
    }

    @Test
    public void parse_validFindPhoneArgs_returnsFindPhoneCommand() {
        // no leading and trailing whitespaces
        FindPhoneCommand expectedFindCommand =
                new FindPhoneCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("91234567")));
        assertParseSuccess(parser, "find p/91234567", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find p/ \n91234567 \t", expectedFindCommand);
    }

    @Test
    public void parse_missingPhoneAfterPrefix_throwsParseException() {
        String input = "find p/"; // Input with empty name prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals("Phone number cannot be empty!", thrown.getMessage());

    }

    @Test
    public void parse_missingPhoneWithTrailingWhiteSpace_throwsParseException() {
        String input = "find p/ \n \t"; // Input with empty name prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals("Phone number cannot be empty!", thrown.getMessage());
    }

    @Test
    public void parse_validFindEmailArgs_returnsFindEmailCommand() {
        // no leading and trailing whitespaces
        FindEmailCommand expectedFindCommand =
                new FindEmailCommand(new EmailContainsKeywordsPredicate(Arrays.asList("carlos@gmail.com")));
        assertParseSuccess(parser, "find e/carlos@gmail.com", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find e/ \t carlos@gmail.com", expectedFindCommand);
    }

    @Test
    public void parse_missingEmailAfterPrefix_throwsParseException() {
        String input = "find e/"; // Input with empty name prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals("Email address cannot be empty!", thrown.getMessage());

    }

    @Test
    public void parse_missingEmailWithTrailingWhiteSpace_throwsParseException() {
        String input = "find e/ \n \t"; // Input with empty name prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals("Email address cannot be empty!", thrown.getMessage());
    }

    @Test
    public void parse_validFindTagArgs_returnsFindTagCommand() {
        // no leading and trailing whitespaces
        FindTagCommand expectedFindCommand =
                new FindTagCommand(new TagContainsKeywordsPredicate(Arrays.asList("florist")));
        assertParseSuccess(parser, "find t/florist", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find t/ \t florist", expectedFindCommand);
    }

    @Test
    public void parse_missingTagAfterPrefix_throwsParseException() {
        String input = "find t/"; // Nothing after tag prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals("Tag cannot be empty!", thrown.getMessage());

    }

}
