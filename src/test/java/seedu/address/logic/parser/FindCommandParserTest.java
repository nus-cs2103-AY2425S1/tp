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
import seedu.address.logic.commands.findcommand.FindTaskCommand;
import seedu.address.logic.commands.findcommand.FindWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.keywordspredicate.AddressContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.EmailContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.NameContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.TagContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.TaskContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.WeddingContainsKeywordsPredicate;

public class FindCommandParserTest {

    public static final String NAME_CANNOT_BE_EMPTY = "Name cannot be empty!";
    public static final String PHONE_NUMBER_CANNOT_BE_EMPTY = "Phone number cannot be empty!";
    public static final String EMAIL_CANNOT_BE_EMPTY = "Email address cannot be empty!";
    public static final String ADDRESS_CANNOT_BE_EMPTY = "Address cannot be empty!";
    public static final String TAG_CANNOT_BE_EMPTY = "Tag cannot be empty!";
    public static final String WEDDING_CANNOT_BE_EMPTY = "Wedding cannot be empty!";
    public static final String TASK_CANNOT_BE_EMPTY = "Task cannot be empty!";
    public static final String PARSE_FAILURE_MULTIPLE_PREFIXES = "You can only specify one prefix at a time.";

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
                new FindNameCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        assertParseSuccess(parser, "find n/Alice", expectedFindCommand);

        // search multiple names using multiple prefixes
        assertParseSuccess(parser, "find n/ \n Alice \n \t  \t", expectedFindCommand);
    }

    @Test
    public void parse_validMultipleFindNameArgs_returnsFindNameCommand() {
        // no leading and trailing whitespaces
        FindNameCommand expectedFindCommand =
                new FindNameCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "find n/Alice n/Bob", expectedFindCommand);

        // search multiple names using multiple prefixes
        assertParseSuccess(parser, "find n/ \n Alice \n \t n/ \tBob  \t", expectedFindCommand);
    }

    @Test
    public void parse_missingNameAfterPrefix_throwsParseException() {
        String input = "find n/"; // Nothing after name prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals(NAME_CANNOT_BE_EMPTY, thrown.getMessage());

    }

    @Test
    public void parse_missingNameWithTrailingWhiteSpace_throwsParseException() {
        String input = "find n/ \n \t"; // Nothing after name prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals(NAME_CANNOT_BE_EMPTY, thrown.getMessage());
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
        String input = "find a/"; // Nothing after address prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals(ADDRESS_CANNOT_BE_EMPTY, thrown.getMessage());
    }

    @Test
    public void missingAddressWithTrailingWhiteSpace_throwsParseException() {
        String input = "find a/ \t \n"; // Nothing after address prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals(ADDRESS_CANNOT_BE_EMPTY, thrown.getMessage());
    }

    @Test
    public void missingAddressWithTrailingWhiteSpace2_throwsParseException() {
        String input = "find a/ Woodlands Avenue 3 a/\t \n"; // Input with empty name prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals(ADDRESS_CANNOT_BE_EMPTY, thrown.getMessage());
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
    public void parse_validMultipleFindPhoneArgs_returnsFindPhoneCommand() {
        // no leading and trailing whitespaces
        FindPhoneCommand expectedFindCommand =
                new FindPhoneCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("91234567", "285")));
        assertParseSuccess(parser, "find p/91234567 p/285", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find p/ \n91234567 \t p/\t 285", expectedFindCommand);
    }

    @Test
    public void parse_missingPhoneAfterPrefix_throwsParseException() {
        String input = "find p/"; // Nothing after phone prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals(PHONE_NUMBER_CANNOT_BE_EMPTY, thrown.getMessage());

    }

    @Test
    public void parse_missingPhoneWithTrailingWhiteSpace_throwsParseException() {
        String input = "find p/ \n \t"; // Nothing after phone prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals(PHONE_NUMBER_CANNOT_BE_EMPTY, thrown.getMessage());
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
    public void parse_validMultipleFindEmailArgs_returnsFindEmailCommand() {
        // no leading and trailing whitespaces
        FindEmailCommand expectedFindCommand =
                new FindEmailCommand(new EmailContainsKeywordsPredicate(Arrays.asList("carlos@gmail.com",
                        "lisa@example.com")));
        assertParseSuccess(parser, "find e/carlos@gmail.com e/lisa@example.com", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find e/ \t carlos@gmail.com e/ \t lisa@example.com", expectedFindCommand);
    }


    @Test
    public void parse_missingEmailAfterPrefix_throwsParseException() {
        String input = "find e/"; // Nothing after email prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals(EMAIL_CANNOT_BE_EMPTY, thrown.getMessage());

    }

    @Test
    public void parse_missingEmailWithTrailingWhiteSpace_throwsParseException() {
        String input = "find e/ \n \t"; // Nothing after email prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals(EMAIL_CANNOT_BE_EMPTY, thrown.getMessage());
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
    public void parse_validMultipleFindTagArgs_returnsFindTagCommand() {
        // no leading and trailing whitespaces
        FindTagCommand expectedFindCommand =
                new FindTagCommand(new TagContainsKeywordsPredicate(Arrays.asList("florist", "DJ")));
        assertParseSuccess(parser, "find t/florist t/DJ", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find t/ \t florist t/ \t DJ", expectedFindCommand);
    }

    @Test
    public void parse_missingTagAfterPrefix_throwsParseException() {
        String input = "find t/"; // Nothing after tag prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals(TAG_CANNOT_BE_EMPTY, thrown.getMessage());
    }

    @Test
    public void parse_missingTagWithTrailingWhiteSpace_throwsParseException() {
        String input = "find t/ \n \t"; // Nothing after tag prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals(TAG_CANNOT_BE_EMPTY, thrown.getMessage());
    }

    @Test
    public void parse_validFindWeddingArgs_returnsFindWeddingCommand() {
        // no leading and trailing whitespaces
        FindWeddingCommand expectedFindCommand =
                new FindWeddingCommand(new WeddingContainsKeywordsPredicate(Arrays.asList("Dave's wedding",
                        "Wedding 2")));
        assertParseSuccess(parser, "find w/Dave's wedding w/Wedding 2", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find w/ \t Dave's wedding w/ \t Wedding 2", expectedFindCommand);
    }

    @Test
    public void parse_missingWeddingAfterPrefix_throwsParseException() {
        String input = "find w/"; // Nothing after wedding prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals(WEDDING_CANNOT_BE_EMPTY, thrown.getMessage());
    }

    @Test
    public void parse_missingWeddingWithTrailingWhiteSpace_throwsParseException() {
        String input = "find w/ \n \t"; // Nothing after wedding prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        // Check for correct error message
        assertEquals(WEDDING_CANNOT_BE_EMPTY, thrown.getMessage());
    }

    @Test
    public void parse_validFindTaskArgs_returnsFindTaskCommand() {
        // no leading and trailing whitespaces
        FindTaskCommand expectedFindCommand =
                new FindTaskCommand(new TaskContainsKeywordsPredicate(Arrays.asList("Order food")));
        assertParseSuccess(parser, "find tk/Order food", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find tk/ \t Order food", expectedFindCommand);
    }

    @Test
    public void parse_validMultipleFindTasksArgs_returnsFindTaskCommand() {
        // no leading and trailing whitespaces
        FindTaskCommand expectedFindCommand =
                new FindTaskCommand(new TaskContainsKeywordsPredicate(Arrays.asList("Order food",
                        "Print photographs")));
        assertParseSuccess(parser, "find tk/Order food tk/Print photographs", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find tk/ \t Order food \n tk/ Print photographs", expectedFindCommand);
    }

    @Test
    public void parse_missingTaskAfterPrefix_throwsParseException() {
        String input = "find tk/"; // Nothing after task prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        assertEquals(TASK_CANNOT_BE_EMPTY, thrown.getMessage());
    }

    @Test
    public void parse_missingTaskWithTrailingWhiteSpace_throwsParseException() {
        String input = "find tk/ \n \t"; // Nothing after task prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        assertEquals(TASK_CANNOT_BE_EMPTY, thrown.getMessage());
    }

    @Test
    public void parse_multiplePrefixes_throwsParseException() {
        String input = "find tk/H a/";
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        assertEquals(PARSE_FAILURE_MULTIPLE_PREFIXES, thrown.getMessage());
    }

    @Test
    public void parse_multiplePrefixes2_throwsParseException() {
        String input = "find p/ a/"; // nothing after first prefix
        ParseException thrown = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });

        assertEquals(PARSE_FAILURE_MULTIPLE_PREFIXES, thrown.getMessage());
    }

}
