package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ExistingConditionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ExistingCondition(null));
    }

    @Test
    public void constructor_invalidExistingCondition_throwsIllegalArgumentException() {
        String invalidExistingCondition = "";
        assertThrows(IllegalArgumentException.class, () -> new ExistingCondition(invalidExistingCondition));
    }

    @Test
    public void isValidExistingCondition() {
        // null existing condition
        assertThrows(NullPointerException.class, () -> ExistingCondition.isValidExistingCondition(null));

        // invalid existing condition
        assertFalse(ExistingCondition.isValidExistingCondition("")); // empty string
        assertFalse(ExistingCondition.isValidExistingCondition(" ")); // spaces only

        // valid existing condition
        assertTrue(ExistingCondition.isValidExistingCondition("Undergoing treatment for cancer"));
        assertTrue(ExistingCondition.isValidExistingCondition("Diabetes"));
    }

    @Test
    public void equals() {
        ExistingCondition existingCondition = new ExistingCondition("Diabetes");

        // same values -> returns true
        assertTrue(existingCondition.equals(new ExistingCondition("Diabetes")));

        // same object -> returns true
        assertTrue(existingCondition.equals(existingCondition));

        // null -> returns false
        assertFalse(existingCondition.equals(null));

        // different types -> returns false
        assertFalse(existingCondition.equals(5.0f));

        // different values -> returns false
        assertFalse(existingCondition.equals(new ExistingCondition("High cholesterol")));
    }
}
