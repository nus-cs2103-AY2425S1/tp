package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameOrJobContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateNameKeywordList = Collections.singletonList("first");
        List<String> firstPredicateJobKeywordList = Collections.singletonList("job");
        List<String> secondPredicateNameKeywordList = Arrays.asList("first", "second");
        List<String> secondPredicateJobKeywordList = Arrays.asList("job", "secondJob");

        NameOrJobContainsKeywordsPredicate firstPredicate = new
                NameOrJobContainsKeywordsPredicate(firstPredicateNameKeywordList,
                firstPredicateJobKeywordList);
        NameOrJobContainsKeywordsPredicate secondPredicate = new
                NameOrJobContainsKeywordsPredicate(secondPredicateNameKeywordList,
                secondPredicateJobKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameOrJobContainsKeywordsPredicate firstPredicateCopy = new
                NameOrJobContainsKeywordsPredicate(firstPredicateNameKeywordList,
                firstPredicateJobKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameOrJobContainsKeywords_returnsTrue() {
        // One name keyword
        NameOrJobContainsKeywordsPredicate predicate = new
                NameOrJobContainsKeywordsPredicate(Collections.singletonList("Alice"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // One job keyword
        predicate = new NameOrJobContainsKeywordsPredicate(Collections.emptyList(),
                Collections.singletonList("Engineer"));
        assertTrue(predicate.test(new PersonBuilder().withJob("Engineer").build()));

        // Multiple name keywords
        predicate = new NameOrJobContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple job keywords
        predicate = new NameOrJobContainsKeywordsPredicate(Collections.emptyList(), Arrays.asList("Engineer",
                "Manager"));
        assertTrue(predicate.test(new PersonBuilder().withJob("Engineer Manager").build()));

        // Mixed-case name keywords
        predicate = new NameOrJobContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case job keywords
        predicate = new NameOrJobContainsKeywordsPredicate(Collections.emptyList(), Arrays.asList("eNGineer",
                "mANager"));
        assertTrue(predicate.test(new PersonBuilder().withJob("Engineer Manager").build()));
    }

    @Test
    public void test_nameOrJobDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameOrJobContainsKeywordsPredicate predicate = new NameOrJobContainsKeywordsPredicate(Collections.emptyList(),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching name keyword
        predicate = new NameOrJobContainsKeywordsPredicate(Arrays.asList("Carol"), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Non-matching job keyword
        predicate = new NameOrJobContainsKeywordsPredicate(Collections.emptyList(), Arrays.asList("Doctor"));
        assertFalse(predicate.test(new PersonBuilder().withJob("Engineer").build()));

        // Keywords match phone, email and address, but does not match name or job
        predicate = new NameOrJobContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"),
                Arrays.asList("Doctor"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withJob("Engineer").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> nameKeywords = List.of("keyword1", "keyword2");
        List<String> jobKeywords = List.of("job1", "job2");
        NameOrJobContainsKeywordsPredicate predicate =
                new NameOrJobContainsKeywordsPredicate(nameKeywords, jobKeywords);

        String expected = "NameOrJobContainsKeywordsPredicate{nameKeywords=" + nameKeywords + ", jobKeywords="
                + jobKeywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
