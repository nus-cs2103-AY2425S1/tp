package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class EmailContainsKeywordsPredicateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EmailContainsKeywordsPredicate(null));
    }

    @Test
    public void equals() {
        Set<String> firstPredicateKeywordList = Set.of("first");
        Set<String> secondPredicateKeywordList = Set.of("first", "second");

        EmailContainsKeywordsPredicate firstPredicate = new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        EmailContainsKeywordsPredicate secondPredicate = new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordsPredicate firstPredicateCopy = new EmailContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(
                Set.of("alice"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@wonderland.com").build()));

        // Multiple keywords
        predicate = new EmailContainsKeywordsPredicate(Set.of("alice", "wonderland"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@wonderland.com").build()));

        // Only one matching keyword
        predicate = new EmailContainsKeywordsPredicate(Set.of("wonderland", "oz"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@wonderland.com").build()));

        // Mixed-case keywords
        predicate = new EmailContainsKeywordsPredicate(Set.of("aLIce", "WoNDeRlaNd"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@wonderland.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Collections.emptySet());
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@wonderland.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Set.of("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@wonderland.com").build()));

        // Keywords match name, but does not match email
        predicate = new EmailContainsKeywordsPredicate(Set.of("12345", "Pauline", "gmail"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Pauline").withEmail("alice@email.com").build()));
    }

    @Test
    public void toStringMethod() {
        Set<String> keywords = Set.of("keyword1", "keyword2");
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(keywords);

        String expected = EmailContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
