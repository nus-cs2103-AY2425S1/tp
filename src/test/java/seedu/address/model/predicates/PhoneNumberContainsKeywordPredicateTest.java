package seedu.address.model.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.predicates.PhoneNumberContainsKeywordPredicate;
import seedu.address.testutil.PersonBuilder;
public class PhoneNumberContainsKeywordPredicateTest {

    @Test
    public void test_diffNumber() {
        List<String> keywords = Collections.singletonList("12345678");
        PhoneNumberContainsKeywordPredicate predicate = new PhoneNumberContainsKeywordPredicate(keywords);
        assertFalse(predicate.test(new PersonBuilder().withPhone("87654321").build()));
    }

    @Test
    public void test_samePartial_returnFalse() {
        List<String> keywords = Collections.singletonList("12345679");
        PhoneNumberContainsKeywordPredicate predicate = new PhoneNumberContainsKeywordPredicate(keywords);
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_partialNumber_returnTrue() {
        List<String> keywords = Collections.singletonList("1234567");
        PhoneNumberContainsKeywordPredicate predicate = new PhoneNumberContainsKeywordPredicate(keywords);
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_multiplePartialNumber_returnFalse() {
        List<String> keywords = Arrays.asList("1234567", "8765432");
        PhoneNumberContainsKeywordPredicate predicate = new PhoneNumberContainsKeywordPredicate(keywords);
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_multipleCompelte_returnTrue() {
        List<String> keywords = Arrays.asList("12345678", "87654321");
        PhoneNumberContainsKeywordPredicate predicate = new PhoneNumberContainsKeywordPredicate(keywords);
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
        assertTrue(predicate.test(new PersonBuilder().withPhone("87654321").build()));
    }
}
