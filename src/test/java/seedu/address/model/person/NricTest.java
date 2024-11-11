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
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidNric = "";
        assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void isValidNric() {
        // null nric
        assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));

        // invalid nric
        assertFalse(Nric.isValidNric("")); // empty string
        assertFalse(Nric.isValidNric(" ")); // spaces only
        assertFalse(Nric.isValidNric("^")); // only invalid characters
        assertFalse(Nric.isValidNric("Z1234567A")); // invalid first letter
        assertFalse(Nric.isValidNric("S12345672")); // invalid last letter
        assertFalse(Nric.isValidNric("S1234567A example")); // extra characters

        // valid nric
        assertTrue(Nric.isValidNric("S1234567A")); // valid nric all uppercase
        assertTrue(Nric.isValidNric("s1234567a")); // valid nric all lowercase
    }

    @Test
    public void equals() {
        Nric nric = new Nric("S1234567A");

        // same values -> returns true
        assertTrue(nric.equals(new Nric("S1234567A")));

        // same values but lowercase -> returns true
        assertTrue(nric.equals(new Nric("s1234567a")));

        // same object -> returns true
        assertTrue(nric.equals(nric));

        // null -> returns false
        assertFalse(nric.equals(null));

        // different types -> returns false
        assertFalse(nric.equals(5.0f));

        // different values -> returns false
        assertFalse(nric.equals(new Nric("S1234567B")));
    }
}
