package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmergencyContactTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EmergencyContact(null));
    }

    @Test
    public void constructor_invalidEmergencyContact_throwsIllegalArgumentException() {
        String invalidEmergencyContact = "";
        assertThrows(IllegalArgumentException.class, () -> new EmergencyContact(invalidEmergencyContact));
    }

    @Test
    public void isValidEmergencyContact() {
        // null emergency contact
        assertThrows(NullPointerException.class, () -> EmergencyContact.isValidEmergencyContact(null));

        // invalid emergency contacts
        assertFalse(EmergencyContact.isValidEmergencyContact("")); // empty string
        assertFalse(EmergencyContact.isValidEmergencyContact(" ")); // spaces only
        assertFalse(EmergencyContact.isValidEmergencyContact("phone")); // non-numeric
        assertFalse(EmergencyContact.isValidEmergencyContact("9011p041")); // alphabets within digits
        assertFalse(EmergencyContact.isValidEmergencyContact("9312 1534")); // spaces within digits
        assertFalse(EmergencyContact.isValidEmergencyContact("1234")); // not 8 digits long
        assertFalse(EmergencyContact.isValidEmergencyContact("123456789")); // not 8 digits long

        // valid emergency contacts
        assertTrue(EmergencyContact.isValidEmergencyContact("00000000")); // exactly 8 zeros
        assertTrue(EmergencyContact.isValidEmergencyContact("93121534")); // normal-looking mobile number
        assertTrue(EmergencyContact.isValidEmergencyContact("62429384")); // normal-looking landline number
    }

    @Test
    public void equals() {
        EmergencyContact emergencyContact = new EmergencyContact("99990000");

        // same values -> returns true
        assertTrue(emergencyContact.equals(new EmergencyContact("99990000")));

        // same object -> returns true
        assertTrue(emergencyContact.equals(emergencyContact));

        // null -> returns false
        assertFalse(emergencyContact.equals(null));

        // different values -> returns false
        assertFalse(emergencyContact.equals(new EmergencyContact("99501234")));
    }

}
