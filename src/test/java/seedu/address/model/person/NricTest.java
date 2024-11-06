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
        assertFalse(Nric.isValidNric("S12345678Z")); // extra numbers
        assertFalse(Nric.isValidNric("e1234567Z")); // wrong initial letter
        assertFalse(Nric.isValidNric("S1234567")); // no last letter
        assertFalse(Nric.isValidNric("1234567z")); // no initial letter
        assertFalse(Nric.isValidNric("S123456Z")); // less numbers
        assertFalse(Nric.isValidNric("E1234HELLo567Z")); // random letters

        // valid nric
        assertTrue(Nric.isValidNric("s1234567Z"));
        assertTrue(Nric.isValidNric("T1234567e"));
        assertTrue(Nric.isValidNric("f1234567a"));
        assertTrue(Nric.isValidNric("G1234567B"));
        assertTrue(Nric.isValidNric("M1234567C"));

    }

    @Test
    public void equals() {
        Nric nric = new Nric("T1234567Z");

        assertTrue(nric.equals(new Nric("T1234567Z")));
        assertTrue(nric.equals(new Nric("t1234567z")));
        assertTrue(nric.equals(new Nric("T1234567z")));
        assertTrue(nric.equals(nric));
        assertFalse(nric.equals(null));
        assertFalse(nric.equals(new Nric("F9876543E")));
    }
}
