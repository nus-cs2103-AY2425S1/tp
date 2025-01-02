package seedu.address.model.client.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;

public class AddressContainsSubstringPredicateTest {

    @Test
    public void equals() {
        String firstPredicateSubstring = "first";
        String secondPredicateSubstring = "first second";

        AddressContainsSubstringPredicate firstPredicate =
                new AddressContainsSubstringPredicate(firstPredicateSubstring);
        AddressContainsSubstringPredicate secondPredicate =
                new AddressContainsSubstringPredicate(secondPredicateSubstring);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsSubstringPredicate firstPredicateCopy =
                new AddressContainsSubstringPredicate(firstPredicateSubstring);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different client -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsSubstring_returnsTrue() {
        // Partial address match
        AddressContainsSubstringPredicate predicate = new AddressContainsSubstringPredicate("Block 123");
        assertTrue(predicate.test(new ClientBuilder().withAddress("Block 123 Avenue 2").build()));

        // Full address match
        predicate = new AddressContainsSubstringPredicate("Block 123 Avenue 2");
        assertTrue(predicate.test(new ClientBuilder().withAddress("Block 123 Avenue 2").build()));

        // Mixed-case substring
        predicate = new AddressContainsSubstringPredicate("blOck 123 avEnuE 2");
        assertTrue(predicate.test(new ClientBuilder().withAddress("Block 123 Avenue 2").build()));
    }

    @Test
    public void test_emptySubstring_throwsException() {
        AddressContainsSubstringPredicate predicate = new AddressContainsSubstringPredicate("");
        Assertions.assertThrows(IllegalArgumentException.class, () -> predicate.test(
                new ClientBuilder().withAddress("Alice").build()));
    }

    @Test
    public void test_addressDoesNotContainSubstring_returnsFalse() {
        // Non-matching substring
        AddressContainsSubstringPredicate predicate = new AddressContainsSubstringPredicate("White House");
        assertFalse(predicate.test(new ClientBuilder().withAddress("Block 123").build()));

        // Substring has a single matching word but substring does not match address
        predicate = new AddressContainsSubstringPredicate("Block 123");
        assertFalse(predicate.test(new ClientBuilder().withAddress("Block 456").build()));

        // Substring matches phone but does not match address
        predicate = new AddressContainsSubstringPredicate("91234567");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));

        // Substring matches email but does not match address
        predicate = new AddressContainsSubstringPredicate("alice@email.com");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));

        // Substring matches name but does not match address
        predicate = new AddressContainsSubstringPredicate("Alice");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));

        // Substring matches remark but does not match remark
        predicate = new AddressContainsSubstringPredicate("Genius");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));

        // Substring matches remark but does not match job
        predicate = new AddressContainsSubstringPredicate("Doctor");
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("Genius")
                .withJob("Doctor").build()));

    }

    @Test
    public void toStringMethod() {
        String substring = "testing substring";
        AddressContainsSubstringPredicate predicate = new AddressContainsSubstringPredicate(substring);

        String expected = AddressContainsSubstringPredicate.class.getCanonicalName()
                + "{substring=" + substring.toUpperCase() + "}";
        assertEquals(expected, predicate.toString());
    }
}
