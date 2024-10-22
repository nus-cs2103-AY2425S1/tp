package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateOfBirthTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateOfBirth(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidDateOfBirth = "A";
        assertThrows(IllegalArgumentException.class, () -> new DateOfBirth(invalidDateOfBirth));
    }

    @Test
    public void isValidDateOfBirth() {
        // null date
        assertThrows(NullPointerException.class, () -> DateOfBirth.isValidDate(null));

        // invalid dates
        assertFalse(DateOfBirth.isValidDate("")); // empty string
        assertFalse(DateOfBirth.isValidDate("11/02/2024")); // wrong format
        assertFalse(DateOfBirth.isValidDate("32-02-2024")); // invalid day
        assertFalse(DateOfBirth.isValidDate("11-13-2024")); // invalid month
        assertFalse(DateOfBirth.isValidDate("11-02")); // missing year

        // valid dates
        assertTrue(DateOfBirth.isValidDate("11-02-2024"));
        assertTrue(DateOfBirth.isValidDate("28-09-1995"));
    }

    @Test
    public void equals() {
        DateOfBirth dateOfBirth = new DateOfBirth("11-02-2024");

        // same values -> returns true
        assertTrue(dateOfBirth.equals(new DateOfBirth("11-02-2024")));

        // same object -> returns true
        assertTrue(dateOfBirth.equals(dateOfBirth));

        // null -> returns false
        assertFalse(dateOfBirth.equals(null));

        // different types -> returns false
        assertFalse(dateOfBirth.equals(5.0f));

        // different values -> returns false
        assertFalse(dateOfBirth.equals(new DateOfBirth("28-09-1995")));
    }
}
