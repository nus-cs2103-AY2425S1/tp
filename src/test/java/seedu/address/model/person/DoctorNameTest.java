package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DoctorNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DoctorName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new DoctorName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> DoctorName.isValidName(null));

        // invalid name
        assertFalse(DoctorName.isValidName("")); // empty string
        assertFalse(DoctorName.isValidName(" ")); // spaces only
        assertFalse(DoctorName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(DoctorName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(DoctorName.isValidName("peter jack")); // alphabets only
        assertTrue(DoctorName.isValidName("12345")); // numbers only
        assertTrue(DoctorName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(DoctorName.isValidName("Capital Tan")); // with capital letters
        assertTrue(DoctorName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        DoctorName name = new DoctorName("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new DoctorName("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new DoctorName("Other Valid Name")));
    }
}
