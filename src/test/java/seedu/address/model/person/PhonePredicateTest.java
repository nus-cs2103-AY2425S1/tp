package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PhonePredicateTest {

    @Test
    public void test_phoneContainsNumber_returnsTrue() {
        PhonePredicate predicate = new PhonePredicate("123");

        // Full phone number contains the given number
        assertTrue(predicate.test(new PersonBuilder().withPhone("123456789").build()));

        // Number appears in the middle of the phone number
        assertTrue(predicate.test(new PersonBuilder().withPhone("451236789").build()));

        // Different formatting, but contains the number
        assertTrue(predicate.test(new PersonBuilder().withPhone("00123").build()));
    }

    @Test
    public void test_phoneDoesNotContainNumber_returnsFalse() {
        PhonePredicate predicate = new PhonePredicate("123");

        // Phone number does not contain the given number
        assertFalse(predicate.test(new PersonBuilder().withPhone("456789000").build()));

        // Completely different phone number
        assertFalse(predicate.test(new PersonBuilder().withPhone("987654321").build()));
    }

    @Test
    public void equals() {
        PhonePredicate firstPredicate = new PhonePredicate("123");
        PhonePredicate secondPredicate = new PhonePredicate("456");

        // Same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // Same value -> returns true
        PhonePredicate firstPredicateCopy = new PhonePredicate("123");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // Different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // Different types -> returns false
        assertFalse(firstPredicate.equals("123"));

        // Null -> returns false
        assertFalse(firstPredicate.equals(null));
    }
}

