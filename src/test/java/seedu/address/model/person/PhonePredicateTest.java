package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

/**
 * Test class for PhonePredicate.
 */
public class PhonePredicateTest {

    /**
     * Test the test method of PhonePredicate.
     */
    @Test
    public void test_phoneMatch_returnsTrue() {
        // Create a PhonePredicate with the same phone number
        PhonePredicate predicate = new PhonePredicate("12345");

        // Assertion
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345").build()));
    }

    @Test
    public void test_phoneNoMatch_returnsFalse() {
        // Create a PhonePredicate with a different phone number
        PhonePredicate predicate = new PhonePredicate("12345");

        // Assertion
        assertFalse(predicate.test(new PersonBuilder().withPhone("23456").build()));
    }

    /**
     * Test the equals method of PhonePredicate.
     */
    @Test
    public void equals_samePhone_returnsTrue() {
        // Setup
        PhonePredicate predicate1 = new PhonePredicate("12345");
        PhonePredicate predicate2 = new PhonePredicate("12345");

        // Assertion
        assertTrue(predicate1.equals(predicate2), "Predicates with the same phone number should be equal");
    }

    @Test
    public void equals_differentPhone_returnsFalse() {
        // Setup
        PhonePredicate predicate1 = new PhonePredicate("12345");
        PhonePredicate predicate2 = new PhonePredicate("54321");

        // Assertion
        assertFalse(predicate1.equals(predicate2), "Predicates with different phone numbers should not be equal");
    }
}
