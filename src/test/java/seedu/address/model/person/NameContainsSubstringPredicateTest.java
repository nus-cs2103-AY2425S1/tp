package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.predicates.NameContainsSubstringPredicate;
import seedu.address.testutil.PersonBuilder;

public class NameContainsSubstringPredicateTest {

    @Test
    public void equals() {
        String firstPredicateSubstring = "first";
        String secondPredicateSubstring = "first second";

        NameContainsSubstringPredicate firstPredicate = new NameContainsSubstringPredicate(firstPredicateSubstring);
        NameContainsSubstringPredicate secondPredicate = new NameContainsSubstringPredicate(secondPredicateSubstring);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsSubstringPredicate firstPredicateCopy = new NameContainsSubstringPredicate(firstPredicateSubstring);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsSubstring_returnsTrue() {
        // Partial name match
        NameContainsSubstringPredicate predicate = new NameContainsSubstringPredicate("Alice");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Full name match
        predicate = new NameContainsSubstringPredicate("Alice Bob");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case substring
        predicate = new NameContainsSubstringPredicate("aLIce bOB");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_emptySubstring_throwsException() {
        NameContainsSubstringPredicate predicate = new NameContainsSubstringPredicate("");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void test_nameDoesNotContainSubstring_returnsFalse() {
        // Non-matching substring
        NameContainsSubstringPredicate predicate = new NameContainsSubstringPredicate("Carol");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Substring has a single matching word but substring does not match name
        predicate = new NameContainsSubstringPredicate("Bob Carol");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Substring matches phone but does not match name
        predicate = new NameContainsSubstringPredicate("91234567");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        // Substring matches email but does not match name
        predicate = new NameContainsSubstringPredicate("alice@email.com");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        // Substring matches address but does not match name
        predicate = new NameContainsSubstringPredicate("Main Street");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        String substring = "testing substring";
        NameContainsSubstringPredicate predicate = new NameContainsSubstringPredicate(substring);

        String expected = NameContainsSubstringPredicate.class.getCanonicalName() + "{substring=" + substring + "}";
        assertEquals(expected, predicate.toString());
    }
}
