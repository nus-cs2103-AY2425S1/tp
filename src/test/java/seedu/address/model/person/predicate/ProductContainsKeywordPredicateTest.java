package seedu.address.model.person.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.predicates.ProductContainsKeywordPredicate;
import seedu.address.testutil.PersonBuilder;

public class ProductContainsKeywordPredicateTest {
    @Test
    public void equals() {

        ProductContainsKeywordPredicate firstPredicate = new ProductContainsKeywordPredicate("Iphone");
        ProductContainsKeywordPredicate secondPredicate = new ProductContainsKeywordPredicate("Water Bottle");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ProductContainsKeywordPredicate firstPredicateCopy = new ProductContainsKeywordPredicate("Iphone");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_companyContainsKeywords_returnsTrue() {
        // One keyword
        ProductContainsKeywordPredicate predicate = new ProductContainsKeywordPredicate("Iphone");
        assertTrue(predicate.test(new PersonBuilder().withProducts("Iphone Sprite Almonds").build()));

        // Partial matching keyword
        predicate = new ProductContainsKeywordPredicate("Phone");
        assertTrue(predicate.test(new PersonBuilder().withProducts("Iphone Sprite Almonds").build()));

        // Mixed-case keywords
        predicate = new ProductContainsKeywordPredicate("spRiTe");
        assertTrue(predicate.test(new PersonBuilder().withProducts("Iphone Sprite Almonds").build()));
    }

    @Test
    public void test_companyDoesNotContainKeywords_returnsFalse() {
        // Bad keyword
        ProductContainsKeywordPredicate predicate = new ProductContainsKeywordPredicate("$$$$$$$$$$$$");
        assertFalse(predicate.test(new PersonBuilder().withProducts("Iphone Sprite Almonds").build()));

        // Non-matching keyword
        predicate = new ProductContainsKeywordPredicate("Cashew");
        assertFalse(predicate.test(new PersonBuilder().withProducts("Iphone Sprite Almonds").build()));

    }

    @Test
    public void toStringMethod() {
        ProductContainsKeywordPredicate predicate = new ProductContainsKeywordPredicate("testing 1 2 3");

        String expected = ProductContainsKeywordPredicate.class.getCanonicalName() + "{keyword=testing 1 2 3}";
        assertEquals(expected, predicate.toString());
    }
}
