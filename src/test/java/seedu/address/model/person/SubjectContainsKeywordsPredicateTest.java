package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class SubjectContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Physics");
        List<String> secondPredicateKeywordList = Arrays.asList("Physics", "English");

        SubjectContainsKeywordsPredicate firstPredicate =
                new SubjectContainsKeywordsPredicate(firstPredicateKeywordList);
        SubjectContainsKeywordsPredicate secondPredicate =
                new SubjectContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SubjectContainsKeywordsPredicate firstPredicateCopy =
                new SubjectContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_subjectContainsKeywords_returnsTrue() {
        // One keyword
        SubjectContainsKeywordsPredicate predicate =
                new SubjectContainsKeywordsPredicate(Collections.singletonList("Physics"));
        assertTrue(predicate.test(new PersonBuilder().withSubject("Physics").build()));

        // Multiple keywords
        predicate = new SubjectContainsKeywordsPredicate(Arrays.asList("Physics", "English"));
        assertTrue(predicate.test(new PersonBuilder().withSubject("Physics", "English").build()));

        // Only one matching keyword
        predicate = new SubjectContainsKeywordsPredicate(Arrays.asList("Physics", "English"));
        assertTrue(predicate.test(new PersonBuilder().withSubject("Physics", "Math").build()));

        // Mixed-case keywords
        predicate = new SubjectContainsKeywordsPredicate(Arrays.asList("PhYSicS", "enGLISh"));
        assertTrue(predicate.test(new PersonBuilder().withSubject("phYSiCs", "ENgLisH").build()));
    }

    @Test
    public void test_subjectDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        SubjectContainsKeywordsPredicate predicate = new SubjectContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withSubject("Physics").build()));

        // Non-matching keyword
        predicate = new SubjectContainsKeywordsPredicate(Arrays.asList("Physics"));
        assertFalse(predicate.test(new PersonBuilder().withSubject("English", "History").build()));

        // Keywords match name, phone, email, and address, but not subjects
        predicate = new SubjectContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com", "Main"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345678")
                .withEmail("alice@email.com").withAddress("123 Main Street").withSubject("Physics").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("friend", "colleague");
        SubjectContainsKeywordsPredicate predicate = new SubjectContainsKeywordsPredicate(keywords);

        String expected = SubjectContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
