package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EcNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // EP: null EcNumber
        assertThrows(NullPointerException.class, () -> new EcNumber(null));
    }

    @Test
    public void constructor_invalidEcNumber_throwsIllegalArgumentException() {
        String invalidEcNumber = "12";
        // EP: invalid EcNumber
        assertThrows(IllegalArgumentException.class, () -> new EcNumber(invalidEcNumber));
    }

    @Test
    public void constructor_validEcNumber() {
        String validEcNumber1 = "123";
        String validEcNumber2 = "";

        // EP: valid EcNumber
        assertDoesNotThrow(() -> new EcNumber(validEcNumber1)); // Boundary Value: 3 digits
        assertDoesNotThrow(() -> new EcNumber(validEcNumber2)); // Boundary Value: Empty String
    }

    @Test
    public void isValidEcNumber() {
        // EP: null phone number
        assertFalse(EcNumber.isValidEcNumber(null));

        // EP: invalid phone numbers
        assertFalse(EcNumber.isValidEcNumber(" ")); // spaces only
        assertFalse(EcNumber.isValidEcNumber("91")); // less than 3 digits
        assertFalse(EcNumber.isValidEcNumber("phone")); // non-numeric
        assertFalse(EcNumber.isValidEcNumber("9011p041")); // alphabets within digits
        assertFalse(EcNumber.isValidEcNumber("9312 1534")); // spaces within digits

        // EP: valid phone numbers
        assertTrue(EcNumber.isValidEcNumber("")); // Boundary value: empty string
        assertTrue(EcNumber.isValidEcNumber("123")); // Boundary value: 3 digits
    }

    @Test
    public void toInt() {
        EcNumber ecNumber = new EcNumber("91234567");

        // EP: integer strings
        assertEquals(ecNumber.toInt(), 91234567);
        assertNotEquals(ecNumber.toInt(), 98765432);

        // EP: empty string
        ecNumber = new EcNumber(""); // Boundary value: empty string
        assertEquals(ecNumber.toInt(), Integer.MAX_VALUE);
        assertNotEquals(ecNumber.toInt(), 0);
    }

    @Test
    public void equals() {
        EcNumber ecNumber = new EcNumber("91234567");

        // EP: same values -> returns true
        assertTrue(ecNumber.equals(new EcNumber("91234567")));

        // EP: same object -> returns true
        assertTrue(ecNumber.equals(ecNumber));

        // EP: null -> returns false
        assertFalse(ecNumber.equals(null));

        // EP: different types -> returns false
        assertFalse(ecNumber.equals(5.0f));

        // EP: different values -> returns false
        assertFalse(ecNumber.equals(new Phone("98765432")));
    }
}
