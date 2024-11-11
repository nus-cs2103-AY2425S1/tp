package seedu.address.model.owner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IdentificationCardNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new IdentificationCardNumber(null));
    }

    @Test
    public void constructor_invalidIc_throwsIllegalArgumentException() {
        String invalid = "";
        assertThrows(IllegalArgumentException.class, () -> new IdentificationCardNumber(invalid));
        assertThrows(IllegalArgumentException.class, () -> new IdentificationCardNumber("S1234567A"));
        assertThrows(IllegalArgumentException.class, () -> new IdentificationCardNumber("S1234567B"));
        assertThrows(IllegalArgumentException.class, () -> new IdentificationCardNumber("S1234567C"));
        assertThrows(IllegalArgumentException.class, () -> new IdentificationCardNumber("S1234567E"));
    }

    @Test
    public void isValidIcNumber() {
        // null IC number
        assertThrows(NullPointerException.class, () -> IdentificationCardNumber.isValidIcNumber(null));

        // invalid IC numbers
        assertFalse(IdentificationCardNumber.isValidIcNumber("")); // empty string
        assertFalse(IdentificationCardNumber.isValidIcNumber(" ")); // spaces only

        // valid IC numbers
        assertTrue(IdentificationCardNumber.isValidIcNumber("S1234567D"));
        assertTrue(IdentificationCardNumber.isValidIcNumber("T1234567J"));
        assertTrue(IdentificationCardNumber.isValidIcNumber("F1234567N"));
        assertTrue(IdentificationCardNumber.isValidIcNumber("G1234567X"));
    }

    @Test
    public void equals() {
        IdentificationCardNumber validIcNumber = new IdentificationCardNumber("S1234567D");

        // same values -> returns true
        assertTrue(validIcNumber.equals(new IdentificationCardNumber("S1234567D")));

        // same object -> returns true
        assertTrue(validIcNumber.equals(validIcNumber));

        // null -> returns false
        assertFalse(validIcNumber.equals(null));

        // different types -> returns false
        assertFalse(validIcNumber.equals(5.0f));

        // different values -> returns false
        assertFalse(validIcNumber.equals(new IdentificationCardNumber("T1234567J")));
    }
}
