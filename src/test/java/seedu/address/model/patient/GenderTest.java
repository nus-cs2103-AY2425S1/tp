package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GenderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Gender(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidGender = "A";
        assertThrows(IllegalArgumentException.class, () -> new Gender(invalidGender));
    }

    @Test
    public void isValidGender() {
        // null gender
        assertThrows(NullPointerException.class, () -> Gender.isValidGender(null));

        // invalid genders
        assertFalse(Gender.isValidGender("")); // empty string
        assertFalse(Gender.isValidGender("P")); // not "M", "F" or "O"
        assertFalse(Gender.isValidGender("0")); // numeric characters
        assertFalse(Gender.isValidGender("m")); // lowercase
        assertFalse(Gender.isValidGender("MM")); // more than one character

        // valid genders
        assertTrue(Gender.isValidGender("M"));
        assertTrue(Gender.isValidGender("F"));
        assertTrue(Gender.isValidGender("O"));
    }

    @Test
    public void equals() {
        Gender gender = new Gender("M");

        // same values -> returns true
        assertTrue(gender.equals(new Gender("M")));

        // same object -> returns true
        assertTrue(gender.equals(gender));

        // null -> returns false
        assertFalse(gender.equals(null));

        // different types -> returns false
        assertFalse(gender.equals(5.0f));

        // different values -> returns false
        assertFalse(gender.equals(new Gender("F")));
    }
}
