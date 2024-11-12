package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.GuestBuilder;
import seedu.address.testutil.VendorBuilder;

public class BudgetContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("10.00");
        List<String> secondPredicateKeywordList = Arrays.asList("10.00", "20.24");

        BudgetContainsKeywordsPredicate firstPredicate =
                new BudgetContainsKeywordsPredicate(firstPredicateKeywordList);
        BudgetContainsKeywordsPredicate secondPredicate =
                new BudgetContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BudgetContainsKeywordsPredicate firstPredicateCopy =
                new BudgetContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different budget -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_budgetContainsKeywords_returnsTrue() {
        // One keyword
        BudgetContainsKeywordsPredicate predicate =
                new BudgetContainsKeywordsPredicate(Collections.singletonList("10.00"));
        assertTrue(predicate.test(new VendorBuilder().withBudget("10.00").build()));

        // Only one matching keyword
        predicate = new BudgetContainsKeywordsPredicate(Arrays.asList("10.00", "20.24"));
        assertTrue(predicate.test(new VendorBuilder().withBudget("10.00").build()));
    }

    @Test
    public void test_budgetDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        BudgetContainsKeywordsPredicate predicate = new BudgetContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new VendorBuilder().withBudget("10.00").build()));

        // Non-matching keyword
        predicate = new BudgetContainsKeywordsPredicate(Arrays.asList("10.00"));
        assertFalse(predicate.test(new VendorBuilder().withBudget("20.24").build()));

        // Keywords match name, phone, email and address, but does not match budget
        predicate = new BudgetContainsKeywordsPredicate(
                Arrays.asList("Alice", "12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new VendorBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withBudget("10.00").build()));

        // Non-vendor
        predicate = new BudgetContainsKeywordsPredicate(Arrays.asList("10.00"));
        assertFalse(predicate.test(new GuestBuilder().build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("10.00", "20.24");
        BudgetContainsKeywordsPredicate predicate = new BudgetContainsKeywordsPredicate(keywords);

        String expected = BudgetContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
