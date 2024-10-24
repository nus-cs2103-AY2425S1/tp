package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        String invalidBudget = "-1";
        assertThrows(IllegalArgumentException.class, () -> new Budget(invalidBudget));
    }

    @Test
    public void constructor_validInputs_success() {
        assertEquals("0.00", new Budget(".").toString()); // Fixes "."
        assertEquals("0.00", new Budget("0").toString()); // Fixes "x"
        assertEquals("5.00", new Budget("5").toString()); // Fixes "x"
        assertEquals("5.00", new Budget("5.").toString()); // Fixes "x."
        assertEquals("5.50", new Budget("5.5").toString()); // Fixes "x.x"
        assertEquals("0.50", new Budget(".5").toString()); // Fixes ".xx"
        assertEquals("0.00", new Budget(".00").toString());
        assertEquals("100.99", new Budget("100.99").toString());
    }

    @Test
    public void isValidBudget() {
        // null budget number
        assertThrows(NullPointerException.class, () -> Budget.isValidBudget(null));

        // invalid budget numbers
        assertFalse(Budget.isValidBudget("-1.0")); // negative values
        assertFalse(Budget.isValidBudget("0.12345")); // more than 2dp
        assertFalse(Budget.isValidBudget("0.1.1")); // more than 1 .
        assertFalse(Budget.isValidBudget("a")); // invalid string
        assertFalse(Budget.isValidBudget("")); // empty string
        assertFalse(Budget.isValidBudget(" ")); // whitespace string
        assertFalse(Budget.isValidBudget(" . ")); // whitespace string
        assertFalse(Budget.isValidBudget("238213798213281 2132132321")); // space between string

        // valid budget numbers
        assertTrue(Budget.isValidBudget("."));
        assertTrue(Budget.isValidBudget("0.0"));
        assertTrue(Budget.isValidBudget(".03"));
        assertTrue(Budget.isValidBudget("1242938420331"));
        assertTrue(Budget.isValidBudget("1."));
    }

    @Test
    public void equals() {
        Budget budget = new Budget("999.0");

        // same values -> returns true
        assertTrue(budget.equals(new Budget("999.0")));

        // same object -> returns true
        assertTrue(budget.equals(budget));

        // null -> returns false
        assertFalse(budget.equals(null));

        // different types -> returns false
        assertFalse(budget.equals(999));

        // different values -> returns false
        assertFalse(budget.equals(new Budget("995.0")));
    }
}
