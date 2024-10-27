package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsDeletePredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsDeletePredicate firstPredicate = new
                NameContainsKeywordsDeletePredicate(firstPredicateKeywordList);
        NameContainsKeywordsDeletePredicate secondPredicate = new
                NameContainsKeywordsDeletePredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsDeletePredicate firstPredicateCopy = new
                NameContainsKeywordsDeletePredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsDeletePredicate predicate = new
                NameContainsKeywordsDeletePredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsDeletePredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsDeletePredicate(Arrays.asList("Ali", "Car"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsDeletePredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords in a different order
        predicate = new NameContainsKeywordsDeletePredicate(Arrays.asList("Car", "Ali"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsDeletePredicate predicate = new
                NameContainsKeywordsDeletePredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsDeletePredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsKeywordsDeletePredicate(
                Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_isExact_returnsTrue() {
        // One keyword
        NameContainsKeywordsDeletePredicate predicate = new
                NameContainsKeywordsDeletePredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.isExact(new PersonBuilder().withName("Alice").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsDeletePredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.isExact(new PersonBuilder().withName("Alice Bob").build()));


        // Mixed-case keywords
        predicate = new NameContainsKeywordsDeletePredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.isExact(new PersonBuilder().withName("Alice Bob").build()));

    }

    @Test
    public void test_isExact_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsDeletePredicate predicate = new
                NameContainsKeywordsDeletePredicate(Collections.emptyList());
        assertFalse(predicate.isExact(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsDeletePredicate(Arrays.asList("Carol"));
        assertFalse(predicate.isExact(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords in a different order
        predicate = new NameContainsKeywordsDeletePredicate(Arrays.asList("Carol", "Alice"));
        assertFalse(predicate.isExact(new PersonBuilder().withName("Alice Carol").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsDeletePredicate(Arrays.asList("Alice", "Car"));
        assertFalse(predicate.isExact(new PersonBuilder().withName("Alice Carol").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        NameContainsKeywordsDeletePredicate predicate = new NameContainsKeywordsDeletePredicate(keywords);

        String expected = NameContainsKeywordsDeletePredicate.class.getCanonicalName()
                + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }

}
