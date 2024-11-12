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
        assertFalse(EmergencyContact.isValidEmergencyContact("   \t\n  ")); // whitespaces only
        assertFalse(EmergencyContact.isValidEmergencyContact("phone")); // non-numeric
        assertFalse(EmergencyContact.isValidEmergencyContact("9011p041")); // alphabets within digits
        assertFalse(EmergencyContact.isValidEmergencyContact("9234")); // less than 8 digits
        assertFalse(EmergencyContact.isValidEmergencyContact("623456789")); // more than 8 digits
        assertFalse(EmergencyContact.isValidEmergencyContact("82345 678")); // whitespace in weird place
        assertFalse(EmergencyContact.isValidEmergencyContact("00000000")); // does not start with 6, 8, or 9
        assertFalse(EmergencyContact.isValidEmergencyContact("7918 2933")); // does not start with 6, 8, or 9

        // valid emergency contacts
        assertTrue(EmergencyContact.isValidEmergencyContact("93121534")); // normal-looking mobile number
        assertTrue(EmergencyContact.isValidEmergencyContact("62429384")); // normal-looking landline number
        assertTrue(EmergencyContact.isValidEmergencyContact("9312 1534")); // spaces within digits
        assertTrue(EmergencyContact.isValidEmergencyContact("8234   3234")); // multiple spaces within number
        assertTrue(EmergencyContact.isValidEmergencyContact("9992\t2933")); // tab within number
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
