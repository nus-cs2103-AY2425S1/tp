package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

public class IncomeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Income(null));
    }

    @Test
    public void constructor_invalidIncomeValue_throwsIllegalArgumentException() {
        String invalidIncomeValue = "not a number";
        assertThrows(IllegalArgumentException.class, () -> new Income(new BigInteger(invalidIncomeValue)));
    }

    @Test
    public void isValidIncome() {
        // null income value name
        assertThrows(NullPointerException.class, () -> Income.isValidIncome(null));

        // empty string
        assertFalse(Income.isValidIncome(""));

        // valid values -> return true
        assertTrue(Income.isValidIncome("0")); // Smallest valid input
        assertTrue(Income.isValidIncome("2147483648")); // One larger than INTEGER_MAX_VALUE
        assertTrue(Income.isValidIncome("9223372036854775808")); // One larger than LONG_MAX_VALUE

        // invalid tier -> returns false
        assertFalse(Income.isValidIncome(" ")); // Invalid string input
        assertFalse(Income.isValidIncome("-1")); // Smallest invalid input
        assertFalse(Income.isValidIncome("-2147483649")); // One smaller than INTEGER_MIN_VALUE
        assertFalse(Income.isValidIncome("-9223372036854775809")); // One smaller than LONG_MIN_VALUE
    }

    @Test
    public void equals() {
        Income income = new Income(BigInteger.ONE);

        // same values -> returns true
        assertTrue(income.equals(new Income(BigInteger.valueOf(1)))); // Using BigInteger factory method
        assertTrue(income.equals(new Income(new BigInteger("1")))); // Using BigInteger public constructor

        // same object -> returns true
        assertTrue(income.equals(income));

        // null -> returns false
        assertFalse(income.equals(null));

        // different types -> returns false
        assertFalse(income.equals(5.0f));

        // different values -> returns false
        assertFalse(income.equals(new Income(BigInteger.TWO)));
    }
}
