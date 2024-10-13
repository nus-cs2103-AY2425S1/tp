package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;




public class ClassIdContainsKeywordsPredicateTest {

    @Test
    public void testEqualsMethod() {
        List<String> firstPredicateKeywordList = Collections.singletonList("1");
        List<String> secondPredicateKeywordList = Arrays.asList("1", "2");

        ClassIdContainsKeywordsPredicate firstPredicate =
                new ClassIdContainsKeywordsPredicate(firstPredicateKeywordList);
        ClassIdContainsKeywordsPredicate secondPredicate =
                new ClassIdContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ClassIdContainsKeywordsPredicate firstPredicateCopy =
                new ClassIdContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testClassIdContainsKeywordsReturnsTrue() {
        // One keyword
        ClassIdContainsKeywordsPredicate predicate =
                new ClassIdContainsKeywordsPredicate(Collections.singletonList("1"));
        assertTrue(predicate.test(new PersonBuilder().withClassId("1").build()));

        // Multiple keywords
        predicate = new ClassIdContainsKeywordsPredicate(Arrays.asList("1", "2"));
        assertTrue(predicate.test(new PersonBuilder().withClassId("1").build()));

        // Only one matching keyword
        predicate = new ClassIdContainsKeywordsPredicate(Arrays.asList("1", "2"));
        assertTrue(predicate.test(new PersonBuilder().withClassId("1").build()));

    }

    @Test
    public void testNameDoesNotContainKeywordsReturnsFalse() {
        ClassIdContainsKeywordsPredicate predicate =
                new ClassIdContainsKeywordsPredicate(Collections.singletonList("1"));
        assertFalse(predicate.test(new PersonBuilder().withClassId("2").build()));

        // Non-matching keyword
        predicate = new ClassIdContainsKeywordsPredicate(Collections.singletonList("1"));
        assertFalse(predicate.test(new PersonBuilder().withClassId("3").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new ClassIdContainsKeywordsPredicate(Collections.singletonList("1"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withClassId("2").build()));
    }

    @Test
    public void testToStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ClassIdContainsKeywordsPredicate predicate = new ClassIdContainsKeywordsPredicate(keywords);

        String expected = ClassIdContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

