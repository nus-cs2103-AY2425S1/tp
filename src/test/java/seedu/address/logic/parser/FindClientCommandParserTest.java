package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindClientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.AddressContainsKeywordsPredicate;
import seedu.address.model.client.Client;
import seedu.address.model.client.CompositePredicate;
import seedu.address.model.client.EmailMatchesPredicate;
import seedu.address.model.client.NameContainsKeywordsPredicate;
import seedu.address.model.client.PhoneMatchesPredicate;
import seedu.address.model.client.PolicyTypeMatchesPredicate;
import seedu.address.model.policy.PolicyType;

public class FindClientCommandParserTest {

    private final FindClientCommandParser parser = new FindClientCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindClientCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPrefixes_throwsParseException() {
        assertParseFailure(parser, " Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindClientCommand.MESSAGE_USAGE));
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
    public void parse_validName_returnsFindClientCommand() throws ParseException {
        String input = " n/Alice Bob";
        List<Predicate<Client>> predicatesList = new ArrayList<>();
        predicatesList.add(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        Predicate<Client> combinedPredicate = new CompositePredicate(predicatesList);

        FindClientCommand expectedCommand = new FindClientCommand(combinedPredicate);
        FindClientCommand actualCommand = parser.parse(input);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_invalidPhone_throwsParseException() {
        try {
            parser.parse(" p/12");
            fail("Expected ParseException was not thrown.");
        } catch (ParseException e) {
            System.out.println("Actual exception message: '" + e.getMessage() + "'");
            assertEquals("Error: Phone number must be between 3 and 15 digits.", e.getMessage());
        }
    }

    @Test
    public void parse_validPhone_returnsFindClientCommand() throws ParseException {
        // Test minimum valid length (3 digits)
        String inputMin = " p/123";
        List<Predicate<Client>> predicatesListMin = new ArrayList<>();
        predicatesListMin.add(new PhoneMatchesPredicate("123"));
        Predicate<Client> combinedPredicateMin = new CompositePredicate(predicatesListMin);

        FindClientCommand expectedCommandMin = new FindClientCommand(combinedPredicateMin);
        FindClientCommand actualCommandMin = parser.parse(inputMin);

        assertEquals(expectedCommandMin, actualCommandMin);

        // Test maximum valid length (15 digits)
        String inputMax = " p/123456789012345";
        List<Predicate<Client>> predicatesListMax = new ArrayList<>();
        predicatesListMax.add(new PhoneMatchesPredicate("123456789012345"));
        Predicate<Client> combinedPredicateMax = new CompositePredicate(predicatesListMax);

        FindClientCommand expectedCommandMax = new FindClientCommand(combinedPredicateMax);
        FindClientCommand actualCommandMax = parser.parse(inputMax);

        assertEquals(expectedCommandMax, actualCommandMax);
    }

    @Test
    public void parse_invalidEmail_throwsParseException() {
        assertParseFailure(parser, " e/invalidemail",
                "Error: Invalid email format. Please provide a valid email address.");
    }

    @Test
    public void parse_validEmail_returnsFindClientCommand() throws ParseException {
        String input = " e/john.doe@example.com";
        List<Predicate<Client>> predicatesList = new ArrayList<>();
        predicatesList.add(new EmailMatchesPredicate("john.doe@example.com"));
        Predicate<Client> combinedPredicate = new CompositePredicate(predicatesList);

        FindClientCommand expectedCommand = new FindClientCommand(combinedPredicate);
        FindClientCommand actualCommand = parser.parse(input);

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
    public void parse_validAddress_returnsFindClientCommand() throws ParseException {
        String input = " a/123 Main Street";
        List<Predicate<Client>> predicatesList = new ArrayList<>();
        predicatesList.add(new AddressContainsKeywordsPredicate(Arrays.asList("123", "Main", "Street")));
        Predicate<Client> combinedPredicate = new CompositePredicate(predicatesList);

        FindClientCommand expectedCommand = new FindClientCommand(combinedPredicate);
        FindClientCommand actualCommand = parser.parse(input);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_invalidPolicyType_throwsParseException() {
        assertParseFailure(parser, " pt/InvalidPolicy",
                "Error: Invalid policy type. Please provide a valid policy type (e.g., Life, Health, Education).");
    }

    @Test
    public void parse_validPolicyType_returnsFindClientCommand() throws ParseException {
        String input = " pt/Life";
        List<Predicate<Client>> predicatesList = new ArrayList<>();
        predicatesList.add(new PolicyTypeMatchesPredicate(PolicyType.LIFE));
        Predicate<Client> combinedPredicate = new CompositePredicate(predicatesList);

        FindClientCommand expectedCommand = new FindClientCommand(combinedPredicate);
        FindClientCommand actualCommand = parser.parse(input);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_multipleValidParameters_returnsFindClientCommand() throws ParseException {
        String input = " n/Alice p/12345678 e/alice@example.com a/123 Main St pt/Health";
        List<Predicate<Client>> predicatesList = new ArrayList<>();
        predicatesList.add(new NameContainsKeywordsPredicate(Collections.singletonList("Alice")));
        predicatesList.add(new PhoneMatchesPredicate("12345678"));
        predicatesList.add(new EmailMatchesPredicate("alice@example.com"));
        predicatesList.add(new AddressContainsKeywordsPredicate(Arrays.asList("123", "Main", "St")));
        predicatesList.add(new PolicyTypeMatchesPredicate(PolicyType.HEALTH));
        Predicate<Client> combinedPredicate = new CompositePredicate(predicatesList);

        FindClientCommand expectedCommand = new FindClientCommand(combinedPredicate);
        FindClientCommand actualCommand = parser.parse(input);

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
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindClientCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_preamblePresent_throwsParseException() {
        assertParseFailure(parser, " unexpected n/Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindClientCommand.MESSAGE_USAGE));
    }
}
