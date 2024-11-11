package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class AddressContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "Tampines";
        String secondPredicateKeywords = "Tampines Street 1";

        AddressContainsKeywordsPredicate firstPredicate =
                new AddressContainsKeywordsPredicate(firstPredicateKeyword);
        AddressContainsKeywordsPredicate secondPredicate =
                new AddressContainsKeywordsPredicate(secondPredicateKeywords);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsKeywordsPredicate firstPredicateCopy =
                new AddressContainsKeywordsPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // One keyword
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate("Tampines");
        assertTrue(predicate.test(new PersonBuilder().withAddress("Tampines Street 1 #01-01").build()));

        // Multiple keywords
        predicate = new AddressContainsKeywordsPredicate("Tampines Street 1");
        assertTrue(predicate.test(new PersonBuilder().withAddress("Tampines Street 1 #01-01").build()));

        // Mixed-case keywords
        predicate = new AddressContainsKeywordsPredicate("tAmPiNeS StReEt 1");
        assertTrue(predicate.test(new PersonBuilder().withAddress("Tampines Street 1 #01-01").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withAddress("Tampines Street 1 #01-01").build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate("Jurong");
        assertFalse(predicate.test(new PersonBuilder().withAddress("Tampines Street 1 #01-01").build()));

        // Not all matching keywords
        predicate = new AddressContainsKeywordsPredicate("Tampines Street 2");
        assertFalse(predicate.test(new PersonBuilder().withAddress("Tampines Street 1 #01-01").build()));
    }

    @Test
    public void toStringMethod() {
        String keywords = "Tampines Street 1";
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(keywords);

        String expected = AddressContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
