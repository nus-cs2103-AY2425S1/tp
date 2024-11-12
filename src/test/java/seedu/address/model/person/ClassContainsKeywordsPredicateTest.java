package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class ClassContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("LE202");
        List<String> secondPredicateKeywordList = Arrays.asList("LE202", "ML103");

        ClassContainsKeywordsPredicate firstPredicate = new ClassContainsKeywordsPredicate(firstPredicateKeywordList);
        ClassContainsKeywordsPredicate secondPredicate = new ClassContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ClassContainsKeywordsPredicate firstPredicateCopy =
                new ClassContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_classContainsKeywords_returnsTrue() {
        // One keyword
        ClassContainsKeywordsPredicate predicate = new ClassContainsKeywordsPredicate(Collections.singletonList("7A"));
        assertTrue(predicate.test(new PersonBuilder().withClasses("7A").build()));

        // Multiple keywords
        predicate = new ClassContainsKeywordsPredicate(Arrays.asList("7A", "8C"));
        assertTrue(predicate.test(new PersonBuilder().withClasses("7A", "8C").build()));

        // Only one matching keyword
        predicate = new ClassContainsKeywordsPredicate(Arrays.asList("7A", "8C"));
        assertTrue(predicate.test(new PersonBuilder().withClasses("7A", "3B").build()));

        // Mixed-case keywords
        predicate = new ClassContainsKeywordsPredicate(Arrays.asList("lE202", "ml101"));
        assertTrue(predicate.test(new PersonBuilder().withClasses("Le202", "ML101").build()));
    }

    @Test
    public void test_classDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ClassContainsKeywordsPredicate predicate = new ClassContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withClasses("7A").build()));

        // Non-matching keyword
        predicate = new ClassContainsKeywordsPredicate(Arrays.asList("7A"));
        assertFalse(predicate.test(new PersonBuilder().withClasses("4C", "6P").build()));

        // Keywords match name, phone, email, and address, but not classes
        predicate = new ClassContainsKeywordsPredicate(Arrays.asList("Alice", "12345678", "alice@email.com", "Main"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345678")
                .withEmail("alice@email.com").withAddress("123 Main Street").withClasses("8B").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("friend", "colleague");
        ClassContainsKeywordsPredicate predicate = new ClassContainsKeywordsPredicate(keywords);

        String expected = ClassContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
