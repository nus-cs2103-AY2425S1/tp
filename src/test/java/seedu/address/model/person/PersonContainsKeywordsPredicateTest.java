package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {
    @Test
    public void test_personContainsKeywords_returnsTrue() throws ParseException {
        // Name
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_NAME.getPrefix(), "John"));
        assertTrue(predicate.test(new PersonBuilder().withName("John Doe").build()));

        // Contact
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_PHONE.getPrefix(), "12345678"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        // Email
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_EMAIL.getPrefix(), "alice12@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice12@example.com").build()));

        // Address
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_ADDRESS.getPrefix(), "Test"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Test Rd").build()));

        // Tags
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_TAG.getPrefix(), "test"));
        assertTrue(predicate.test(new PersonBuilder().withTags("test").build()));

        // Multiple keywords in one category
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_NAME.getPrefix(), "John", "Adam"));
        assertTrue(predicate.test(new PersonBuilder().withName("John").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Adam").build()));

        // Multiple categories
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_NAME.getPrefix(), "John", "Adam",
                PREFIX_PHONE.getPrefix(), "12345678", PREFIX_EMAIL.getPrefix(), "test@example.com", "alice@nus.com"));
        assertTrue(predicate.test(new PersonBuilder().withName("John").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Adam").build()));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
        assertTrue(predicate.test(new PersonBuilder().withEmail("test@example.com").build()));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@nus.com").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Steve").withPhone("12345678").build()));

        // Empty search query
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(""));
        assertTrue(predicate.test(new PersonBuilder().withName("John Doe").build()));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice12@example.com").build()));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Test Rd").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("test").build()));
        assertTrue(predicate.test(new PersonBuilder().build()));
    }

    @Test
    public void test_personContainsKeywords_returnsFalse() throws ParseException {
        // Name
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Arrays
                .asList(PREFIX_NAME.getPrefix(), "John"));
        assertFalse(predicate.test(new PersonBuilder().withName("Jane Doe").build()));

        // Contact
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_PHONE.getPrefix(), "87654321"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        // Email
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_EMAIL.getPrefix(), "alice12@example.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("bobby@example.com").build()));

        // Address
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_ADDRESS.getPrefix(), "Test"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("123 NUS Rd").build()));

        // Tags
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PREFIX_TAG.getPrefix(), "test"));
        assertFalse(predicate.test(new PersonBuilder().withTags("noMatchingTags").build()));
    }
}
