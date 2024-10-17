package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class JobCodePredicateTest {

    @Test
    public void test_jobCodeMatches_returnsTrue() {
        JobCodePredicate predicate = new JobCodePredicate("ENG123");
        assertTrue(predicate.test(new PersonBuilder().withJobCode("ENG123").build()));
    }

    @Test
    public void test_jobCodeDoesNotMatch_returnsFalse() {
        JobCodePredicate predicate = new JobCodePredicate("ENG123");
        assertFalse(predicate.test(new PersonBuilder().withJobCode("ENG456").build()));
    }

    @Test
    public void test_jobCodeDifferentCase_returnsFalse() {
        JobCodePredicate predicate = new JobCodePredicate("eng123");
        assertFalse(predicate.test(new PersonBuilder().withJobCode("ENG123").build()));
    }
}

