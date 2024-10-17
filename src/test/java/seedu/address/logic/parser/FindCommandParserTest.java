package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.CompositePredicate;
import seedu.address.model.person.EmailMatchesPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneMatchesPredicate;
import seedu.address.model.person.PolicyTypeMatchesPredicate;
import seedu.address.model.policy.PolicyType;

public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPrefixes_throwsParseException() {
        assertParseFailure(parser, " Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNameLength_throwsParseException() {
        // Name too short
        assertParseFailure(parser, " n/",
                "Error: Name must be between 1 and 100 characters.");

        // Name too long
        String longName = "a".repeat(101);
        assertParseFailure(parser, " n/" + longName,
                "Error: Name must be between 1 and 100 characters.");
    }

    @Test
    public void parse_validName_returnsFindCommand() throws ParseException {
        String input = " n/Alice Bob";
        List<Predicate<Person>> predicatesList = new ArrayList<>();
        predicatesList.add(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        Predicate<Person> combinedPredicate = new CompositePredicate(predicatesList);

        FindCommand expectedCommand = new FindCommand(combinedPredicate);
        FindCommand actualCommand = parser.parse(input);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_invalidPhone_throwsParseException() {
        // Less than 8 digits
        assertParseFailure(parser, " p/1234567",
                "Error: Phone number must be exactly 8 digits.");

        // More than 8 digits
        assertParseFailure(parser, " p/123456789",
                "Error: Phone number must be exactly 8 digits.");

        // Non-numeric
        assertParseFailure(parser, " p/phone123",
                "Error: Phone number must be exactly 8 digits.");
    }

    @Test
    public void parse_validPhone_returnsFindCommand() throws ParseException {
        String input = " p/12345678";
        List<Predicate<Person>> predicatesList = new ArrayList<>();
        predicatesList.add(new PhoneMatchesPredicate("12345678"));
        Predicate<Person> combinedPredicate = new CompositePredicate(predicatesList);

        FindCommand expectedCommand = new FindCommand(combinedPredicate);
        FindCommand actualCommand = parser.parse(input);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_invalidEmail_throwsParseException() {
        assertParseFailure(parser, " e/invalidemail",
                "Error: Invalid email format. Please provide a valid email address.");
    }

    @Test
    public void parse_validEmail_returnsFindCommand() throws ParseException {
        String input = " e/john.doe@example.com";
        List<Predicate<Person>> predicatesList = new ArrayList<>();
        predicatesList.add(new EmailMatchesPredicate("john.doe@example.com"));
        Predicate<Person> combinedPredicate = new CompositePredicate(predicatesList);

        FindCommand expectedCommand = new FindCommand(combinedPredicate);
        FindCommand actualCommand = parser.parse(input);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_invalidAddressLength_throwsParseException() {
        // Address too short
        assertParseFailure(parser, " a/",
                "Error: Address must be between 1 and 255 characters.");

        // Address too long
        String longAddress = "a".repeat(256);
        assertParseFailure(parser, " a/" + longAddress,
                "Error: Address must be between 1 and 255 characters.");
    }

    @Test
    public void parse_validAddress_returnsFindCommand() throws ParseException {
        String input = " a/123 Main Street";
        List<Predicate<Person>> predicatesList = new ArrayList<>();
        predicatesList.add(new AddressContainsKeywordsPredicate(Arrays.asList("123", "Main", "Street")));
        Predicate<Person> combinedPredicate = new CompositePredicate(predicatesList);

        FindCommand expectedCommand = new FindCommand(combinedPredicate);
        FindCommand actualCommand = parser.parse(input);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_invalidPolicyType_throwsParseException() {
        assertParseFailure(parser, " pt/InvalidPolicy",
                "Error: Invalid policy type. Please provide a valid policy type (e.g., Life, Health, Education).");
    }

    @Test
    public void parse_validPolicyType_returnsFindCommand() throws ParseException {
        String input = " pt/Life";
        List<Predicate<Person>> predicatesList = new ArrayList<>();
        predicatesList.add(new PolicyTypeMatchesPredicate(PolicyType.LIFE));
        Predicate<Person> combinedPredicate = new CompositePredicate(predicatesList);

        FindCommand expectedCommand = new FindCommand(combinedPredicate);
        FindCommand actualCommand = parser.parse(input);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_multipleValidParameters_returnsFindCommand() throws ParseException {
        String input = " n/Alice p/12345678 e/alice@example.com a/123 Main St pt/Health";
        List<Predicate<Person>> predicatesList = new ArrayList<>();
        predicatesList.add(new NameContainsKeywordsPredicate(Collections.singletonList("Alice")));
        predicatesList.add(new PhoneMatchesPredicate("12345678"));
        predicatesList.add(new EmailMatchesPredicate("alice@example.com"));
        predicatesList.add(new AddressContainsKeywordsPredicate(Arrays.asList("123", "Main", "St")));
        predicatesList.add(new PolicyTypeMatchesPredicate(PolicyType.HEALTH));
        Predicate<Person> combinedPredicate = new CompositePredicate(predicatesList);

        FindCommand expectedCommand = new FindCommand(combinedPredicate);
        FindCommand actualCommand = parser.parse(input);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_invalidAndValidParameters_throwsParseException() {
        // Invalid name length and valid phone
        assertParseFailure(parser, " n/ p/12345678",
                "Error: Name must be between 1 and 100 characters.");
    }

    @Test
    public void parse_unknownPrefix_throwsParseException() {
        assertParseFailure(parser, " x/unknown",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_preamblePresent_throwsParseException() {
        assertParseFailure(parser, " unexpected n/Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}
