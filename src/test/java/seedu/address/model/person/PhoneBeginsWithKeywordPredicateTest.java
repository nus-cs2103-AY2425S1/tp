package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneBeginsWithKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "9678";
        String secondPredicateKeyword = "9876";

        PhoneBeginsWithKeywordPredicate firstPredicate = new PhoneBeginsWithKeywordPredicate(firstPredicateKeyword);
        PhoneBeginsWithKeywordPredicate secondPredicate = new PhoneBeginsWithKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneBeginsWithKeywordPredicate firstPredicateCopy = new PhoneBeginsWithKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneBeginsWithKeyword_returnsTrue() {
        // One number
        PhoneBeginsWithKeywordPredicate predicate = new PhoneBeginsWithKeywordPredicate("9");
        assertTrue(predicate.test(new PersonBuilder().withPhone("98765432").build()));

        // Multiple numbers
        predicate = new PhoneBeginsWithKeywordPredicate("9876");
        assertTrue(predicate.test(new PersonBuilder().withPhone("98765432").build()));

        // Only one matching keyword
        predicate = new PhoneBeginsWithKeywordPredicate("98765432");
        assertTrue(predicate.test(new PersonBuilder().withPhone("98765432").build()));
    }

    @Test
    public void test_phoneDoesNotBeginWithKeyword_returnsFalse() {
        // Zero keyword
        PhoneBeginsWithKeywordPredicate predicate = new PhoneBeginsWithKeywordPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withPhone("98765432").build()));

        // Non-matching keyword
        predicate = new PhoneBeginsWithKeywordPredicate("92345678");
        assertFalse(predicate.test(new PersonBuilder().withPhone("98765432").build()));

        // Keyword match client type, but does not match phone number
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("98765432")
                .withEmail("alice@email.com").withAddress("Main Street").withClientTypes("92345678").build()));
    }

    @Test
    public void toStringMethod() {
        String keyword = "98765432";
        PhoneBeginsWithKeywordPredicate predicate = new PhoneBeginsWithKeywordPredicate(keyword);

        String expected = PhoneBeginsWithKeywordPredicate.class.getCanonicalName() + "{keyword=" + keyword + "}";
        assertEquals(expected, predicate.toString());
    }
}
