package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BloodTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BloodType(null));
    }

    @Test
    public void constructor_invalidBloodType_throwsIllegalArgumentException() {
        String invalidBloodType = "";
        assertThrows(IllegalArgumentException.class, () -> new BloodType(invalidBloodType));
    }

    @Test
    public void isValidBloodType() {
        // null blood type
        assertThrows(NullPointerException.class, () -> BloodType.isValidBloodType(null));

        // invalid blood types
        assertFalse(BloodType.isValidBloodType("")); // empty string
        assertFalse(BloodType.isValidBloodType(" ")); // spaces only
        assertFalse(BloodType.isValidBloodType("A")); // without the Rhesus factor

        // valid blood types
        assertTrue(BloodType.isValidBloodType("A+"));
        assertTrue(BloodType.isValidBloodType("A-"));
        assertTrue(BloodType.isValidBloodType("AB+"));
        assertTrue(BloodType.isValidBloodType("AB-"));
        assertTrue(BloodType.isValidBloodType("B+"));
        assertTrue(BloodType.isValidBloodType("B-"));
        assertTrue(BloodType.isValidBloodType("O+"));
        assertTrue(BloodType.isValidBloodType("O-"));
    }

    @Test
    public void equals() {
        BloodType bloodType = new BloodType("A+");

        // same values -> returns true
        assertTrue(bloodType.equals(new BloodType("A+")));

        // same object -> returns true
        assertTrue(bloodType.equals(bloodType));

        // null -> returns false
        assertFalse(bloodType.equals(null));

        // different types -> returns false
        assertFalse(bloodType.equals(5.0f));

        // different values -> returns false
        assertFalse(bloodType.equals(new BloodType("A-")));
    }
}
