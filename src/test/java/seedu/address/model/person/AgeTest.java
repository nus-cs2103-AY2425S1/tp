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
        assertFalse(Age.isValidAge(" ")); // spaces only
        assertFalse(Age.isValidAge("one")); // non-numeric
        assertFalse(Age.isValidAge("0.00")); // non-integer
        assertFalse(Age.isValidAge("1 7")); // space within digits
        assertFalse(Age.isValidAge("-100")); // negative; unequal to lower limit
        assertFalse(Age.isValidAge("-0")); // negative; equal to lower limit
        assertFalse(Age.isValidAge("1000")); // exceeds upper limit

        // valid age
        assertTrue(Age.isValidAge("27")); // between lower and upper limit
        assertTrue(Age.isValidAge("0")); // lower limit
        assertTrue(Age.isValidAge("150")); // upper limit
        assertTrue(Age.isValidAge("00001")); // leading zero input
        assertTrue(Age.isValidAge("00000")); // leading zero input with value 0
    }

    @Test
    public void equals() {
        Age age = new Age("50");

        // same values -> returns true
        assertTrue(age.equals(new Age("50")));

        // same object -> returns true
        assertTrue(age.equals(age));

        // null -> returns false
        assertFalse(age.equals(null));

        // different types -> returns false
        assertFalse(age.equals(5.0f));

        // different values -> returns false
        assertFalse(age.equals(new Age("20")));
    }

}
