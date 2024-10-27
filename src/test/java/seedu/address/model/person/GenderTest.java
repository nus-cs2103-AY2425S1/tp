package seedu.address.model.person;

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
    public void constructor_invalidGender_throwsIllegalArgumentException() {
        String invalidGender = "";
        assertThrows(IllegalArgumentException.class, () -> new Gender(invalidGender));
    }

    @Test
    public void isValidGender() {
        // null agender
        assertThrows(NullPointerException.class, () -> Gender.isValidGender(null));

        // invalid age
        assertFalse(Gender.isValidGender("")); // empty string
        assertFalse(Gender.isValidGender(" ")); // whitespace
        assertFalse(Gender.isValidGender("-1")); // negative
        assertFalse(Gender.isValidGender("abc")); // alphabets
        assertFalse(Gender.isValidGender("1000")); // numbers
        assertFalse(Gender.isValidGender("1abc00")); // numbers
        assertFalse(Gender.isValidGender("B"));


        // valid age
        assertTrue(Gender.isValidGender("F"));
        assertTrue(Gender.isValidGender("M"));
        assertTrue(Gender.isValidGender("f"));
        assertTrue(Gender.isValidGender("m"));

    }

    @Test
    public void equals() {
        Gender gender = new Gender("M");

        assertTrue(gender.equals(new Gender("M")));
        assertTrue(gender.equals(gender));
        assertFalse(gender.equals(null));
        assertFalse(gender.equals(new Gender("F")));

    }
}
