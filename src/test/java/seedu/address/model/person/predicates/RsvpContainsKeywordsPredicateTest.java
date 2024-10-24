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

public class RsvpContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("accepted");
        List<String> secondPredicateKeywordList = Arrays.asList("accepted", "pending");

        RsvpContainsKeywordsPredicate firstPredicate = new RsvpContainsKeywordsPredicate(firstPredicateKeywordList);
        RsvpContainsKeywordsPredicate secondPredicate = new RsvpContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RsvpContainsKeywordsPredicate firstPredicateCopy =
                new RsvpContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different rsvp -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_rsvpContainsKeywords_returnsTrue() {
        // One keyword
        RsvpContainsKeywordsPredicate predicate =
                new RsvpContainsKeywordsPredicate(Collections.singletonList("accepted"));
        assertTrue(predicate.test(new GuestBuilder().withRsvp("accepted").build()));

        // Only one matching keyword
        predicate = new RsvpContainsKeywordsPredicate(Arrays.asList("accepted", "declined"));
        assertTrue(predicate.test(new GuestBuilder().withRsvp("accepted").build()));
    }

    @Test
    public void test_rsvpDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RsvpContainsKeywordsPredicate predicate = new RsvpContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new GuestBuilder().withRsvp("accepted").build()));

        // Non-matching keyword
        predicate = new RsvpContainsKeywordsPredicate(Arrays.asList("accepted"));
        assertFalse(predicate.test(new GuestBuilder().withRsvp("pending").build()));

        // Keywords match name, phone, email and address, but does not match rsvp
        predicate = new RsvpContainsKeywordsPredicate(
                Arrays.asList("Alice", "12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withRsvp("accepted").build()));

        // Non-guest
        predicate = new RsvpContainsKeywordsPredicate(Arrays.asList("accepted"));
        assertFalse(predicate.test(new VendorBuilder().build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("accepted");
        RsvpContainsKeywordsPredicate predicate = new RsvpContainsKeywordsPredicate(keywords);

        String expected = RsvpContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
