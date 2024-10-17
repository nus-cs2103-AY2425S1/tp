package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.util.IncomeComparisonOperator;
import seedu.address.testutil.PersonBuilder;

public class IncomeComparisonPredicateTest {

    @Test
    public void equals() {
        IncomeComparisonOperator operatorEqual = new IncomeComparisonOperator("=");
        IncomeComparisonOperator operatorGreater = new IncomeComparisonOperator(">");

        IncomeComparisonPredicate firstPredicate = new IncomeComparisonPredicate(5000, operatorEqual);
        IncomeComparisonPredicate secondPredicate = new IncomeComparisonPredicate(10000, operatorGreater);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        IncomeComparisonPredicate firstPredicateCopy = new IncomeComparisonPredicate(5000, operatorEqual);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_incomeEqualComparison_returnsTrue() {
        IncomeComparisonPredicate predicate = new IncomeComparisonPredicate(5000, new IncomeComparisonOperator("="));
        assertTrue(predicate.test(new PersonBuilder().withIncome(5000).build()));
    }

    @Test
    public void test_incomeEqualComparison_returnsFalse() {
        IncomeComparisonPredicate predicate = new IncomeComparisonPredicate(5000, new IncomeComparisonOperator("="));
        assertFalse(predicate.test(new PersonBuilder().withIncome(6000).build()));
        assertFalse(predicate.test(new PersonBuilder().withIncome(4000).build()));
    }

    @Test
    public void test_incomeGreaterThanComparison_returnsTrue() {
        IncomeComparisonPredicate predicate = new IncomeComparisonPredicate(5000, new IncomeComparisonOperator(">"));
        assertTrue(predicate.test(new PersonBuilder().withIncome(6000).build()));
    }

    @Test
    public void test_incomeGreaterThanComparison_returnsFalse() {
        IncomeComparisonPredicate predicate = new IncomeComparisonPredicate(5000, new IncomeComparisonOperator(">"));
        assertFalse(predicate.test(new PersonBuilder().withIncome(4000).build()));
        assertFalse(predicate.test(new PersonBuilder().withIncome(5000).build()));
    }

    @Test
    public void test_incomeLessThanComparison_returnsTrue() {
        IncomeComparisonPredicate predicate = new IncomeComparisonPredicate(5000, new IncomeComparisonOperator("<"));
        assertTrue(predicate.test(new PersonBuilder().withIncome(4000).build()));
    }

    @Test
    public void test_incomeLessThanComparison_returnsFalse() {
        // Income is not less than the threshold
        IncomeComparisonPredicate predicate = new IncomeComparisonPredicate(5000, new IncomeComparisonOperator("<"));
        assertFalse(predicate.test(new PersonBuilder().withIncome(6000).build()));
        assertFalse(predicate.test(new PersonBuilder().withIncome(5000).build()));
    }

    @Test
    public void toStringMethod() {
        IncomeComparisonOperator operator = new IncomeComparisonOperator("=");
        IncomeComparisonPredicate predicate = new IncomeComparisonPredicate(5000, operator);

        String expected = IncomeComparisonPredicate.class.getCanonicalName()
                + "{incomeThreshold=5000, incomeComparisonOperator==}";
        assertEquals(expected, predicate.toString());
    }
}
