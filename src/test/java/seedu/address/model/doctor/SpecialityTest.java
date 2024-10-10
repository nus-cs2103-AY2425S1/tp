package seedu.address.model.doctor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SpecialityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Speciality(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidSpeciality = "";
        assertThrows(IllegalArgumentException.class, () -> new Speciality(invalidSpeciality));
    }

    @Test
    public void isValidSpeciality() {
        // null speciality
        assertThrows(NullPointerException.class, () -> Speciality.isValidSpeciality(null));

        // invalid specialities
        assertFalse(Speciality.isValidSpeciality("")); // empty string
        assertFalse(Speciality.isValidSpeciality(" ")); // spaces only
        assertFalse(Speciality.isValidSpeciality("0123")); // numeric characters

        // valid specialities
        assertTrue(Speciality.isValidSpeciality("Dentist"));
        assertTrue(Speciality.isValidSpeciality("Dermatology"));
    }

    @Test
    public void equals() {
        Speciality speciality = new Speciality("ValidSpeciality");

        // same values -> returns true
        assertTrue(speciality.equals(new Speciality("ValidSpeciality")));

        // same object -> returns true
        assertTrue(speciality.equals(speciality));

        // null -> returns false
        assertFalse(speciality.equals(null));

        // different types -> returns false
        assertFalse(speciality.equals(5.0f));

        // different values -> returns false
        assertFalse(speciality.equals(new Speciality("OtherSpeciality")));
    }
}
