package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.GuestBuilder;
import seedu.address.testutil.VendorBuilder;

public class EmailContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("john@gmail.com");
        List<String> secondPredicateKeywordList = Arrays.asList("john@gmail.com", "betsy@gmail.com");

        EmailContainsKeywordsPredicate firstPredicate = new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        EmailContainsKeywordsPredicate secondPredicate = new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordsPredicate firstPredicateCopy =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different email -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("john@gmail.com"));
        assertTrue(predicate.test(new GuestBuilder().withEmail("john@gmail.com").build()));
        assertTrue(predicate.test(new VendorBuilder().withEmail("john@gmail.com").build()));

        // Only one matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("john@gmail.com", "betsy@gmail.com"));
        assertTrue(predicate.test(new GuestBuilder().withEmail("john@gmail.com").build()));
        assertTrue(predicate.test(new VendorBuilder().withEmail("john@gmail.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new GuestBuilder().withEmail("john@gmail.com").build()));
        assertFalse(predicate.test(new VendorBuilder().withEmail("john@gmail.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("john@gmail.com"));
        assertFalse(predicate.test(new GuestBuilder().withEmail("betsy@gmail.com").build()));
        assertFalse(predicate.test(new VendorBuilder().withEmail("betsy@gmail.com").build()));

        // Keywords match name, phone and address, but does not match email
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "Main", "Street"));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
        assertFalse(predicate.test(new VendorBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("john@gmail.com", "betsy@gmail.com");
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(keywords);

        String expected = EmailContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
