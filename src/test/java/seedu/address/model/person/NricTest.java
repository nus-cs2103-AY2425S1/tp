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
        assertFalse(Nric.isValidNric("S64065D")); // only 5 digits
        assertFalse(Nric.isValidNric("S6406542Z")); // wrong checksum
        assertFalse(Nric.isValidNric("P6406542D")); // starting with P

        // valid nric
        assertTrue(Nric.isValidNric("s6406542d")); // lowercase alphabets
        assertTrue(Nric.isValidNric("S6406542D")); // uppercase alphabets
        assertTrue(Nric.isValidNric("T5517825E")); // starting with T
        assertTrue(Nric.isValidNric("F3895385X")); // starting with F
        assertTrue(Nric.isValidNric("G8413738W")); // starting with G
    }

    @Test
    public void equals() {
        Nric nric = new Nric("S6406542D");

        // same values -> returns true
        assertTrue(nric.equals(new Nric("S6406542D")));

        // same object -> returns true
        assertTrue(nric.equals(nric));

        // null -> returns false
        assertFalse(nric.equals(null));

        // different types -> returns false
        assertFalse(nric.equals(5.0f));

        // different values -> returns false
        assertFalse(nric.equals(new Nric("S9798929Z")));
    }
}
