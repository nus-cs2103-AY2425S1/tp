package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IncomeTest {
    @Test
    public void constructor_invalidIncome_throwsIllegalArgumentException() {
        double invalidIncome = -1;
        assertThrows(IllegalArgumentException.class, () -> new Income(invalidIncome));
    }

    @Test
    public void constructor_validIncome_shouldSucceed() {
        double validIncome = 3000.0;
        assertDoesNotThrow(() -> new Income(validIncome));
    }

    @Test
    public void isValidIncome() {
        assertTrue(Income.isValidIncome(3000.0));
        assertTrue(Income.isValidIncome(0));
        assertFalse(Income.isValidIncome(-0.1));
        assertFalse(Income.isValidIncome(-1));
    }
}
