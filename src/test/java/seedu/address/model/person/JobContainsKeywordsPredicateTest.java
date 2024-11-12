package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class JobContainsKeywordsPredicateTest {

    @Test
    public void test_jobContainsKeywords_returnsTrue() {
        // One keyword
        JobContainsKeywordsPredicate predicate = new JobContainsKeywordsPredicate(Collections
                .singletonList("Engineer"));
        assertTrue(predicate.test(new PersonBuilder().withJob("Software Engineer").build()));

        // Multiple keywords
        predicate = new JobContainsKeywordsPredicate(Arrays.asList("Software", "Engineer"));
        assertTrue(predicate.test(new PersonBuilder().withJob("Software Engineer").build()));

        // Only one matching keyword
        predicate = new JobContainsKeywordsPredicate(Arrays.asList("Engineer", "Doctor"));
        assertTrue(predicate.test(new PersonBuilder().withJob("Software Engineer").build()));

        // Mixed-case keywords
        predicate = new JobContainsKeywordsPredicate(Arrays.asList("eNgInEeR"));
        assertTrue(predicate.test(new PersonBuilder().withJob("Software Engineer").build()));
    }

    @Test
    public void test_jobDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        JobContainsKeywordsPredicate predicate = new JobContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withJob("Software Engineer").build()));

        // Non-matching keyword
        predicate = new JobContainsKeywordsPredicate(Arrays.asList("Doctor"));
        assertFalse(predicate.test(new PersonBuilder().withJob("Software Engineer").build()));

        // Keywords match name, phone, email, but does not match job
        predicate = new JobContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@example.com"));
        assertFalse(predicate.test(new PersonBuilder().withJob("Software Engineer").build()));
    }
}
