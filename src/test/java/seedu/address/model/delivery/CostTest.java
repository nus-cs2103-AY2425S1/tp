package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CostTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Cost(null));
    }

    @Test
    public void constructor_invalidCost_throwsIllegalArgumentException() {
        String invalidCost = "#20";
        assertThrows(IllegalArgumentException.class, () -> new Cost(invalidCost));
    }

    @Test
    public void isValidCost() {
        // null cost
        assertThrows(NullPointerException.class, () -> Cost.isValidCost(null));

        // invalid cost
        assertFalse(Cost.isValidCost(""));
        assertFalse(Cost.isValidCost(" "));
        assertFalse(Cost.isValidCost("100"));
        assertFalse(Cost.isValidCost("$"));
        assertFalse(Cost.isValidCost("%100"));

        // valid cost
        assertTrue(Cost.isValidCost("$100"));
        assertTrue(Cost.isValidCost("$0"));
        assertTrue(Cost.isValidCost("$1093845679"));
    }
}
