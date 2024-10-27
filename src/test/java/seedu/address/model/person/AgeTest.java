package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AgeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Age(null));
    }

    @Test
    public void constructor_invalidAge_throwsIllegalArgumentException() {
        String invalidAge = "";
        assertThrows(IllegalArgumentException.class, () -> new Age(invalidAge));
    }

    @Test
    public void isValidAge() {
        // null age
        assertThrows(NullPointerException.class, () -> Age.isValidAge(null));

        // invalid age
        assertFalse(Age.isValidAge("")); // empty string
        assertFalse(Age.isValidAge(" ")); // whitespace
        assertFalse(Age.isValidAge("-1")); // negative
        assertFalse(Age.isValidAge("abc")); // alphabets
        assertFalse(Age.isValidAge("1000")); // over 999


        // valid age
        assertTrue(Age.isValidAge("0"));
        assertTrue(Age.isValidAge("12"));
        assertTrue(Age.isValidAge("999"));
    }

    @Test
    public void equals() {
        Age age = new Age("12");

        assertTrue(age.equals(new Age("12")));
        assertTrue(age.equals(age));
        assertFalse(age.equals(null));
        assertFalse(age.equals(new Age("69")));
    }
}
