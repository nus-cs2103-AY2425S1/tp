package seedu.address.model.types.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonTagContainsKeywordsPredicate firstPredicate =
                new PersonTagContainsKeywordsPredicate(firstPredicateKeywordList);
        PersonTagContainsKeywordsPredicate secondPredicate =
                new PersonTagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonTagContainsKeywordsPredicate firstPredicateCopy =
                new PersonTagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        PersonTagContainsKeywordsPredicate predicate =
                new PersonTagContainsKeywordsPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "owesMoney").build()));

        // Multiple keywords
        predicate = new PersonTagContainsKeywordsPredicate(Arrays.asList("friends", "owesMoney"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "owesMoney").build()));

        // Only one matching keyword
        predicate = new PersonTagContainsKeywordsPredicate(Arrays.asList("owesMoney", "family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "family").build()));

        // Mixed-case keywords
        predicate = new PersonTagContainsKeywordsPredicate(Arrays.asList("fRIends", "oWEsmONey"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "owesMoney").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonTagContainsKeywordsPredicate predicate =
                new PersonTagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Non-matching keyword
        predicate = new PersonTagContainsKeywordsPredicate(Arrays.asList("family"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends", "owesMoney").build()));

        // Keywords match name, phone, email and address, but does not match tag
        predicate = new PersonTagContainsKeywordsPredicate(
                Arrays.asList("Alice", "12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("friends").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PersonTagContainsKeywordsPredicate predicate = new PersonTagContainsKeywordsPredicate(keywords);

        String expected = PersonTagContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
