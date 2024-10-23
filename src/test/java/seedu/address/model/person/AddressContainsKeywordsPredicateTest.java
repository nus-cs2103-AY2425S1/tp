package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class AddressContainsKeywordsPredicateTest {

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // One keyword
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Street"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Baker Street").build()));

        // Multiple keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Baker", "Street"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Baker Street").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Arrays.asList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("123 Baker Street").build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("nomatch"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("123 Baker Street").build()));
    }
}
