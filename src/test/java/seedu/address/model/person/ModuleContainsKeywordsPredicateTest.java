package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class ModuleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "CS1010";
        String secondPredicateKeyword = "CS2030";

        ModuleContainsKeywordsPredicate firstPredicate =
                new ModuleContainsKeywordsPredicate(firstPredicateKeyword);
        ModuleContainsKeywordsPredicate secondPredicate =
                new ModuleContainsKeywordsPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ModuleContainsKeywordsPredicate firstPredicateCopy =
                new ModuleContainsKeywordsPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_moduleContainsKeyword_returnsTrue() {
        ModuleContainsKeywordsPredicate predicate = new ModuleContainsKeywordsPredicate("CS1010");
        assertTrue(predicate.test(new PersonBuilder().addUngradedModule("CS1010").build()));

        predicate = new ModuleContainsKeywordsPredicate("cs1010");
        assertTrue(predicate.test(new PersonBuilder().addUngradedModule("CS1010").build()));

        predicate = new ModuleContainsKeywordsPredicate("1010");
        assertTrue(predicate.test(new PersonBuilder().addUngradedModule("CS1010").build()));
    }

    @Test
    public void test_moduleDoesNotContainKeyword_returnsFalse() {
        ModuleContainsKeywordsPredicate predicate = new ModuleContainsKeywordsPredicate("CS9999");
        assertFalse(predicate.test(new PersonBuilder().addUngradedModule("CS2030").build()));
    }

    @Test
    public void toStringMethod() {
        String keyword = "CS1010";
        ModuleContainsKeywordsPredicate predicate = new ModuleContainsKeywordsPredicate(keyword);

        String expected = ModuleContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keyword + "}";
        assertEquals(expected, predicate.toString());
    }
}
