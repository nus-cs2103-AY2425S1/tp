package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BudgetTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Budget(null));
    }

    @Test
    public void constructor_invalidBudget_throwsIllegalArgumentException() {
        double invalidBudget = -1;
        assertThrows(IllegalArgumentException.class, () -> new Budget(invalidBudget));
    }

    @Test
    public void isValidBudget() {
        // null budget number
        assertThrows(NullPointerException.class, () -> Budget.isValidBudget(null));

        // invalid budget numbers
        assertFalse(Budget.isValidBudget(-1.0)); // negative values
        assertFalse(Budget.isValidBudget(0.12345)); // more than 2dp
         assertFalse(Budget.isValidBudget(123456789101234.56)); // more than 15 characters at parser util instead

        // valid budget numbers
        assertTrue(Budget.isValidBudget(0.0)); // exactly 3 numbers
        assertTrue(Budget.isValidBudget(9312153.03));
        assertTrue(Budget.isValidBudget(1242938420331.23)); // 15 characters
    }

    @Test
    public void equals() {
        Budget budget = new Budget(999.0);

        // same values -> returns true
        assertTrue(budget.equals(new Budget(999.0)));

        // same object -> returns true
        assertTrue(budget.equals(budget));

        // null -> returns false
        assertFalse(budget.equals(null));

        // different types -> returns false
        assertFalse(budget.equals("hello"));

        // different values -> returns false
        assertFalse(budget.equals(new Budget(995.0)));
    }
}
