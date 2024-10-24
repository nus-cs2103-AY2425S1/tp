package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameEmailPredicateTest {

    @Test
    public void test_nameAndEmailMatches_returnsTrue() {
        NameEmailPredicate predicate = new NameEmailPredicate("John Doe", "john@example.com");
        assertTrue(predicate.test(new PersonBuilder().withName("John Doe").withEmail("john@example.com").build()));
    }

    @Test
    public void test_nameMatchesEmailDoesNotMatch_returnsFalse() {
        NameEmailPredicate predicate = new NameEmailPredicate("John Doe", "john@example.com");
        assertFalse(predicate.test(new PersonBuilder().withName("John Doe").withEmail("jane@example.com").build()));
    }

    @Test
    public void test_nameDoesNotMatchEmailMatches_returnsFalse() {
        NameEmailPredicate predicate = new NameEmailPredicate("John Adams", "john@example.com");
        assertFalse(predicate.test(new PersonBuilder().withName("John Doe").withEmail("john@example.com").build()));
    }

    @Test
    public void test_nameAndEmailDifferentCase_returnsTrue() {
        NameEmailPredicate predicate = new NameEmailPredicate("JOHN DOE", "JOHN@EXAMPLE.COM");
        assertTrue(predicate.test(new PersonBuilder().withName("John Doe").withEmail("john@example.com").build()));
    }
}

