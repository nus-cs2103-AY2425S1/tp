package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmergencyPhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EmergencyPhone(null));
    }

    @Test
    public void constructor_invalidEmergencyPhone_throwsIllegalArgumentException() {
        String invalidPhone = "123";
        assertThrows(IllegalArgumentException.class, () -> new EmergencyPhone(invalidPhone));
    }

    @Test
    public void isValidEmergencyPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> EmergencyPhone.isValidEmergencyPhone(null));

        // invalid phone numbers
        assertFalse(EmergencyPhone.isValidEmergencyPhone(" ")); // spaces only
        assertFalse(EmergencyPhone.isValidEmergencyPhone("91")); // less than 8 numbers
        assertFalse(EmergencyPhone.isValidEmergencyPhone("phone")); // non-numeric
        assertFalse(EmergencyPhone.isValidEmergencyPhone("9011p041")); // alphabets within digits
        assertFalse(EmergencyPhone.isValidEmergencyPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(EmergencyPhone.isValidEmergencyPhone("")); // empty string
        assertTrue(EmergencyPhone.isValidEmergencyPhone("93121534")); // exactly 8 numbers
    }

    @Test
    public void equals() {
        EmergencyPhone emergencyPhone = new EmergencyPhone("91234567");

        // same values -> returns true
        assertTrue(emergencyPhone.equals(new EmergencyPhone("91234567")));

        // same object -> returns true
        assertTrue(emergencyPhone.equals(emergencyPhone));

        // null -> returns false
        assertFalse(emergencyPhone.equals(null));

        // different types -> returns false
        assertFalse(emergencyPhone.equals(5.0f));

        // different values -> returns false
        assertFalse(emergencyPhone.equals(new Phone("98765432")));
    }
}
