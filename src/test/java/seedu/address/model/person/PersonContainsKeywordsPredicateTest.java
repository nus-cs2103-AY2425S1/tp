package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate("Alice", null, null, null, null, null, null);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate("Bob", null, null, null, null, null, null);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy =
                new PersonContainsKeywordsPredicate("Alice", null, null, null, null, null, null);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personContainsKeywords_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate("Alice", null, null, null, null, null, null);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        predicate = new PersonContainsKeywordsPredicate(null, null,
                null, null, "cs", null, null);
        assertTrue(predicate.test(new PersonBuilder().withMajor("cs").build()));

        predicate = new PersonContainsKeywordsPredicate(null, null,
                null, "brUdder", null, null, null);
        assertTrue(predicate.test(new PersonBuilder().withRole("brUdder").build()));
    }

    @Test
    public void test_personDoesNotContainKeywords_returnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate("Carol", null, null, null, null, null, null);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        predicate = new PersonContainsKeywordsPredicate(null, null, null, null, "bza", null, null);
        assertFalse(predicate.test(new PersonBuilder().withMajor("cs").build()));

        predicate = new PersonContainsKeywordsPredicate(null, null, null, "mUdder", null, null, null);
        assertFalse(predicate.test(new PersonBuilder().withRole("brUdder").build()));
    }

    @Test
    public void test_personMatchesSomeFields_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate("Alice", null, null, null, "bza", null, null);
        assertTrue(predicate.test(
                new PersonBuilder().withName("Alice").withMajor("bza").withAddress("Queenstown").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> tags = List.of("friends", "owesMoney");
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate("Alice", "98765432",
                "alice@example.com", "brUdder", "cs", "123 Street", tags);

        String expected = PersonContainsKeywordsPredicate.class.getCanonicalName()
                + "{name=Alice, phone=98765432, email=alice@example.com, role=brUdder,"
                + " major=cs, address=123 Street, tags=" + tags + "}";
        assertEquals(expected, predicate.toString());
    }
}
