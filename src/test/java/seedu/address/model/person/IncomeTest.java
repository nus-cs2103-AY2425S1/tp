package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IncomeTest {
    @Test
    public void constructor_invalidIncome_throwsIllegalArgumentException() {
        String invalidIncome = "-1";
        assertThrows(IllegalArgumentException.class, () -> new Income(invalidIncome));
    }

    @Test
    public void constructor_validIncome_shouldSucceed() {
        String validIncome = "3000.0";
        assertDoesNotThrow(() -> new Income(validIncome));
    }
}
