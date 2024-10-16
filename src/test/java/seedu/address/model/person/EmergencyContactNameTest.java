package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EmergencyContactNameTest {

    @Test
    public void isValidEcName() {
        // invalid name
        assertFalse(EmergencyContactName.isValidEmergencyContactName(null));
        assertFalse(EmergencyContactName.isValidEmergencyContactName(" ")); // spaces only
        assertFalse(EmergencyContactName.isValidEmergencyContactName("^")); // only non-alphanumeric characters
        assertFalse(EmergencyContactName.isValidEmergencyContactName("jane*"));
        // contains non-alphanumeric characters

        // valid name
        assertTrue(EmergencyContactName.isValidEmergencyContactName("")); // empty string
        assertTrue(EmergencyContactName.isValidEmergencyContactName("leo wilson")); // alphabets only
        assertTrue(EmergencyContactName.isValidEmergencyContactName("12345")); // numbers only
        assertTrue(EmergencyContactName.isValidEmergencyContactName("Jack the 3rd")); // alphanumeric characters
        assertTrue(EmergencyContactName.isValidEmergencyContactName("Capital Tan")); // with capital letters
        assertTrue(EmergencyContactName.isValidEmergencyContactName("David Roger Jackson Ray Jr 2nd"));
        // long names
    }
    @Test
    public void equals() {
        EmergencyContactName ecName = new EmergencyContactName("Valid Name");

        // same values -> returns true
        assertTrue(ecName.equals(new EmergencyContactName("Valid Name")));

        // same object -> returns true
        assertTrue(ecName.equals(ecName));

        // null -> returns false
        assertFalse(ecName.equals(null));

        // different types -> returns false
        assertFalse(ecName.equals(5.0f));

        // different values -> returns false
        assertFalse(ecName.equals(new Name("Jack Name")));
    }
}
