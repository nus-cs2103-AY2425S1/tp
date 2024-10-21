package seedu.address.model.buyer;

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
        String invalidBudget = "";
        assertThrows(IllegalArgumentException.class, () -> new Budget(invalidBudget));
    }

    @Test
    public void isValidBudget() {
        // null address
        assertThrows(NullPointerException.class, () -> Budget.isValidBudget(null));

        // invalid budget
        assertFalse(Budget.isValidBudget("-100,000")); // negative number
        assertFalse(Budget.isValidBudget("abc")); // not a number
        assertFalse(Budget.isValidBudget("ab100")); // contain number and alphabets
        assertFalse(Budget.isValidBudget("100.00")); // not an integer
        assertFalse(Budget.isValidBudget("1000,000")); // haphazard placement of commas


        // valid budget
        assertTrue(Budget.isValidBudget("1000000")); // no commas
        assertTrue(Budget.isValidBudget("1,000,000")); // commas
    }

    @Test
    public void equals() {
        Budget budget = new Budget("10,000");

        // same values -> returns true
        assertTrue(budget.equals(new Budget("10,000")));

        // same object -> returns true
        assertTrue(budget.equals(budget));

        // null -> returns false
        assertFalse(budget.equals(null));

        // different types -> returns false
        assertFalse(budget.equals(5.0f));

        // different values -> returns false
        assertFalse(budget.equals(new Budget("1000")));
    }
}
