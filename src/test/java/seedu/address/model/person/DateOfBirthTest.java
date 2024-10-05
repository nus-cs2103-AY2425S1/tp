package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class DateOfBirthTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateOfBirth(null));
    }

    @Test
    public void constructor_invalidDateOfBirth_throwsIllegalArgumentException() {
        String invalidDateOfBirth = "invalid-date";
        assertThrows(IllegalArgumentException.class, () -> new DateOfBirth(invalidDateOfBirth));
    }

    @Test
    public void isValidDateOfBirth() {
        String futureDate = LocalDate.now().plusDays(2).toString();
        // null date of birth
        assertThrows(NullPointerException.class, () -> DateOfBirth.isValidDateOfBirth(null));

        // invalid date of birth
        assertFalse(DateOfBirth.isValidDateOfBirth(futureDate)); // future date

        // valid date of birth
        assertTrue(DateOfBirth.isValidDateOfBirth("2023-01-01")); // valid date
    }

    @Test
    public void equals() {
        DateOfBirth dateOfBirth = new DateOfBirth("2000-01-01");

        // same values -> returns true
        assertTrue(dateOfBirth.equals(new DateOfBirth("2000-01-01")));

        // same object -> returns true
        assertTrue(dateOfBirth.equals(dateOfBirth));

        // null -> returns false
        assertFalse(dateOfBirth.equals(null));

        // different types -> returns false
        assertFalse(dateOfBirth.equals(5.0f));

        // different values -> returns false
        assertFalse(dateOfBirth.equals(new DateOfBirth("1999-01-01")));
    }
}
