package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NricTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Nric(null));
    }

    @Test
    public void constructor_invalidNric_throwsIllegalArgumentException() {
        String invalidNric = "A123"; // Clearly invalid NRIC format
        assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void isValidNric() {
        // null NRIC
        assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));

        // invalid NRICs
        assertFalse(Nric.isValidNric("")); // empty string
        assertFalse(Nric.isValidNric(" ")); // spaces only
        assertFalse(Nric.isValidNric("S12345678")); // 8 numbers
        assertFalse(Nric.isValidNric("S123456E")); // only 7 characters total
        assertFalse(Nric.isValidNric("S12345E7A")); // non-digit in the middle
        assertFalse(Nric.isValidNric("1234567A")); // no alphabet at the start
        assertFalse(Nric.isValidNric("S1234567")); // no alphabet at the end
        assertFalse(Nric.isValidNric("S1234567*")); // invalid character at the end

        // valid NRICs
        assertTrue(Nric.isValidNric("S1234567A"));
        assertTrue(Nric.isValidNric("T7654321Z"));
        assertTrue(Nric.isValidNric("F0987654B")); // mixed with different first alphabets
        assertTrue(Nric.isValidNric("G2345678K")); // different ending alphabet
        assertTrue(Nric.isValidNric("s1234567a")); // lowercase letters at start and end
    }

    @Test
    public void equals() {
        Nric nric = new Nric("S1234567A");

        // same values -> returns true
        assertTrue(nric.equals(new Nric("S1234567A")));

        // same object -> returns true
        assertTrue(nric.equals(nric));

        // null -> returns false
        assertFalse(nric.equals(null));

        // different types -> returns false
        assertFalse(nric.equals(5.0f));

        // different values -> returns false
        assertFalse(nric.equals(new Nric("T7654321Z")));
    }

    @Test
    public void toStringMethod() {
        Nric nric = new Nric("S1234567A");
        assertTrue(nric.toString().equals("S1234567A"));
    }
}
