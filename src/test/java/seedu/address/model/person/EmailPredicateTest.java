package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class EmailPredicateTest {

    @Test
    public void test_emailContainsKeyword_returnsTrue() {
        EmailPredicate predicate = new EmailPredicate("example");

        // Email contains keyword
        assertTrue(predicate.test(new PersonBuilder().withEmail("user@example.com").build()));

        // Different casing
        assertTrue(predicate.test(new PersonBuilder().withEmail("USER@EXAMPLE.com").build()));

        // Keyword as part of email
        assertTrue(predicate.test(new PersonBuilder().withEmail("test@example.org").build()));
    }

    @Test
    public void test_emailDoesNotContainKeyword_returnsFalse() {
        EmailPredicate predicate = new EmailPredicate("example");

        // Email does not contain keyword
        assertFalse(predicate.test(new PersonBuilder().withEmail("user@test.com").build()));

        // Partial match but not contained as a whole
        assertFalse(predicate.test(new PersonBuilder().withEmail("exmpl@domain.com").build()));
    }

    @Test
    public void equals() {
        EmailPredicate firstPredicate = new EmailPredicate("example");
        EmailPredicate secondPredicate = new EmailPredicate("test");

        // Same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // Same value -> returns true
        EmailPredicate firstPredicateCopy = new EmailPredicate("example");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // Different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // Different types -> returns false
        assertFalse(firstPredicate.equals("example"));

        // Null -> returns false
        assertFalse(firstPredicate.equals(null));
    }
}

