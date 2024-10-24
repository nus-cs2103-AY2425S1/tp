package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EcNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EcNumber(null));
    }

    @Test
    public void constructor_invalidEmergencyPhone_throwsIllegalArgumentException() {
        String invalidPhone = "123";
        assertThrows(IllegalArgumentException.class, () -> new EcNumber(invalidPhone));
    }

    @Test
    public void isValidEcNumber() {
        // null phone number
        assertFalse(EcNumber.isValidEcNumber(null));

        // invalid phone numbers
        assertFalse(EcNumber.isValidEcNumber(" ")); // spaces only
        assertFalse(EcNumber.isValidEcNumber("91")); // less than 8 numbers
        assertFalse(EcNumber.isValidEcNumber("phone")); // non-numeric
        assertFalse(EcNumber.isValidEcNumber("9011p041")); // alphabets within digits
        assertFalse(EcNumber.isValidEcNumber("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(EcNumber.isValidEcNumber("")); // empty string
        assertTrue(EcNumber.isValidEcNumber("93121534")); // exactly 8 numbers
    }

    @Test
    public void toInt() {
        EcNumber ecNumber = new EcNumber("91234567");

        assertEquals(ecNumber.toInt(), 91234567);
        assertNotEquals(ecNumber.toInt(), 98765432);

        // empty ecNumber
        ecNumber = new EcNumber("");
        assertEquals(ecNumber.toInt(), Integer.MAX_VALUE);
        assertNotEquals(ecNumber.toInt(), 0);
    }

    @Test
    public void equals() {
        EcNumber ecNumber = new EcNumber("91234567");

        // same values -> returns true
        assertTrue(ecNumber.equals(new EcNumber("91234567")));

        // same object -> returns true
        assertTrue(ecNumber.equals(ecNumber));

        // null -> returns false
        assertFalse(ecNumber.equals(null));

        // different types -> returns false
        assertFalse(ecNumber.equals(5.0f));

        // different values -> returns false
        assertFalse(ecNumber.equals(new Phone("98765432")));
    }
}
