package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class FullNameMatchesPredicateTest {

    @Test
    public void test_fullNameMatches_returnsTrue() {
        FullNameMatchesPredicate predicate = new FullNameMatchesPredicate("John Doe");
        assertTrue(predicate.test(new PersonBuilder().withName("John Doe").build()));
    }

    @Test
    public void test_fullNamePartialMatch_returnsFalse() {
        FullNameMatchesPredicate predicate = new FullNameMatchesPredicate("John");
        assertFalse(predicate.test(new PersonBuilder().withName("John Doe").build()));
    }

    @Test
    public void test_fullNameDifferent_returnsFalse() {
        FullNameMatchesPredicate predicate = new FullNameMatchesPredicate("John Adams");
        assertFalse(predicate.test(new PersonBuilder().withName("John Doe").build()));
    }

    @Test
    public void test_fullNameDifferentCase_returnsTrue() {
        FullNameMatchesPredicate predicate = new FullNameMatchesPredicate("JOHN DOE");
        assertTrue(predicate.test(new PersonBuilder().withName("John Doe").build()));
    }
}
