package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsTagsPredicateTest {

    @Test
    public void constructor_nullKeywords_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new PersonContainsTagsPredicate(null));
    }

    @Test
    public void constructor_emptyKeywords_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new PersonContainsTagsPredicate(Collections.emptyList()));
    }

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("friends");
        List<String> secondPredicateKeywordList = Arrays.asList("friends", "family");

        PersonContainsTagsPredicate firstPredicate = new PersonContainsTagsPredicate(firstPredicateKeywordList);
        PersonContainsTagsPredicate secondPredicate = new PersonContainsTagsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsTagsPredicate firstPredicateCopy = new PersonContainsTagsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personHasTag_returnsTrue() {
        // One keyword
        PersonContainsTagsPredicate predicate =
                new PersonContainsTagsPredicate(Collections.singletonList("Friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friends").build()));

        // Multiple keywords, one matching
        predicate = new PersonContainsTagsPredicate(Arrays.asList("Friends", "Family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friends").build()));

        // Multiple keywords, multiple matches
        predicate = new PersonContainsTagsPredicate(Arrays.asList("Friends", "Family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friends", "Family").build()));

        // Mixed-case keywords
        predicate = new PersonContainsTagsPredicate(Arrays.asList("fRiEnDs", "FaMiLy"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friends", "Family").build()));
    }

    @Test
    public void test_personDoesNotHaveTag_returnsFalse() {
        // Non-matching keyword
        PersonContainsTagsPredicate predicate =
                new PersonContainsTagsPredicate(Collections.singletonList("Friends"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Family").build()));

        // Keywords match name, phone, email and address, but does not match any tags
        predicate = new PersonContainsTagsPredicate(Arrays.asList("Alice"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice")
                .withPhone("12345").withEmail("alice@email.com")
                .withAddress("Main Street").withTags("Family").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("Friends", "Family");
        PersonContainsTagsPredicate predicate = new PersonContainsTagsPredicate(keywords);

        String expected = PersonContainsTagsPredicate.class.getCanonicalName() + "{tags=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}