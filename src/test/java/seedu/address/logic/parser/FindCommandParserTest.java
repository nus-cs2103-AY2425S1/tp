package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() throws ParseException {
        // Parse valid arguments
        FindCommand findCommand = parser.parse("Alice Bob");

        // Create sample persons with matching names
        Person personMatchingAlice = new Person(
                new Name("Alice"),
                new Phone("98765432"),
                new Email("alice@example.com"),
                new Address("123 AMK Ave"),
                new HashSet<>(Arrays.asList(new Tag("friend")))
        );

        Person personMatchingBob = new Person(
                new Name("Bob"),
                new Phone("12345678"),
                new Email("bob@example.com"),
                new Address("456 AMK Road"),
                new HashSet<>(Arrays.asList(new Tag("musician")))
        );

        // Test that the actual parsed command returns true for both cases (name match)
        assertTrue(findCommand.getPredicate().test(personMatchingAlice)); // Should match Alice
        assertTrue(findCommand.getPredicate().test(personMatchingBob)); // Should match Bob
    }

    @Test
    public void parse_validPhoneArgs_returnsFindCommand() throws ParseException {
        // Mixed phone numbers
        FindCommand findCommand = parser.parse("12345 67890");

        // Create a sample person with matching phone numbers
        Person personMatchingFirstPhone = new Person(
                new Name("Alice"),
                new Phone("12345"),
                new Email("alice@example.com"),
                new Address("123 AMK Ave"),
                new HashSet<>(Arrays.asList(new Tag("friend")))
        );

        Person personMatchingSecondPhone = new Person(
                new Name("Bob"),
                new Phone("67890"),
                new Email("bob@example.com"),
                new Address("456 AMK Road"),
                new HashSet<>(Arrays.asList(new Tag("musician")))
        );

        assertTrue(findCommand.getPredicate().test(personMatchingFirstPhone));
        assertTrue(findCommand.getPredicate().test(personMatchingSecondPhone));
    }

    @Test
    public void parse_validNameAndPhoneArgs_returnsFindCommand() throws ParseException  {
        FindCommand findCommand = parser.parse("Alice 12345");

        Set<Tag> tagsForAlice = new HashSet<>(Arrays.asList(new Tag("friend")));
        Person personMatchingName = new Person(
                new Name("Alice"),
                new Phone("98765432"),
                new Email("alice@example.com"),
                new Address("123 AMK Ave"),
                tagsForAlice
        );

        Set<Tag> tagsForBob = new HashSet<>(Arrays.asList(new Tag("musician")));
        Person personMatchingPhone = new Person(
                new Name("Bob"),
                new Phone("12345"),
                new Email("bob@example.com"),
                new Address("456 AMK Road"),
                tagsForBob
        );

        assertTrue(findCommand.getPredicate().test(personMatchingName));
        assertTrue(findCommand.getPredicate().test(personMatchingPhone));
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
}
