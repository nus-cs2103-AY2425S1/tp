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

        // invalid ages
        assertFalse(Age.isValidAge("")); // empty string
        assertFalse(Age.isValidAge(" ")); // spaces only
        assertFalse(Age.isValidAge("9111")); // more than 3 numbers
        assertFalse(Age.isValidAge("9111111")); // more than 3 numbers
        assertFalse(Age.isValidAge("age")); // non-numeric
        assertFalse(Age.isValidAge("10a")); // alphabets within digits

        // valid age
        assertTrue(Age.isValidAge("100")); // exactly 3 numbers
        assertTrue(Age.isValidAge("10")); // less than 3 numbers
        assertTrue(Age.isValidAge("1")); // less than 3 numbers
    }

    @Test
    public void equals() {
        Age age = new Age("99");

        // same values -> returns true
        assertTrue(age.equals(new Age("99")));

        // same object -> returns true
        assertTrue(age.equals(age));

        // null -> returns false
        assertFalse(age.equals(null));

        // different types -> returns false
        assertFalse(age.equals(5.0f));

        // different values -> returns false
        assertFalse(age.equals(new Age("10")));
    }
}
