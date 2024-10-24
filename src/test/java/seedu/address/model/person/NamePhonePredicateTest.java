package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NamePhonePredicateTest {

    @Test
    public void test_nameAndPhoneMatches_returnsTrue() {
        NamePhonePredicate predicate = new NamePhonePredicate("John Doe", "12345678");
        assertTrue(predicate.test(new PersonBuilder().withName("John Doe").withPhone("12345678").build()));
    }

    @Test
    public void test_nameMatchesPhoneDoesNotMatch_returnsFalse() {
        NamePhonePredicate predicate = new NamePhonePredicate("John Doe", "12345678");
        assertFalse(predicate.test(new PersonBuilder().withName("John Doe").withPhone("87654321").build()));
    }

    @Test
    public void test_nameDoesNotMatchPhoneMatches_returnsFalse() {
        NamePhonePredicate predicate = new NamePhonePredicate("John Adams", "12345678");
        assertFalse(predicate.test(new PersonBuilder().withName("John Doe").withPhone("12345678").build()));
    }

    @Test
    public void test_nameAndPhoneDifferentCase_returnsTrue() {
        NamePhonePredicate predicate = new NamePhonePredicate("JOHN DOE", "12345678");
        assertTrue(predicate.test(new PersonBuilder().withName("John Doe").withPhone("12345678").build()));
    }
}

