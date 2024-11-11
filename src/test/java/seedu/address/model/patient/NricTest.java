package seedu.address.model.patient;
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
    public void constructor_emptyNric_throwsIllegalArgumentException() {
        String invalidNric = "";
        assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void constructor_spaceOnlyNric_throwsIllegalArgumentException() {
        String invalidNric = " ";
        assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void constructor_invalidStartingCharacterNric_throwsIllegalArgumentException() {
        String invalidNric = "A1234567B";
        assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void constructor_invalidEndingCharacterNric_throwsIllegalArgumentException() {
        String invalidNric = "S12345678";
        assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void constructor_lessThanSevenDigitsNric_throwsIllegalArgumentException() {
        String invalidNric = "S123456B";
        assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void constructor_moreThanSevenDigitsNric_throwsIllegalArgumentException() {
        String invalidNric = "S12345678B";
        assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void constructor_endingCharacterNotUppercaseNric_throwsIllegalArgumentException() {
        String invalidNric = "S1234567b";
        assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void constructor_startingCharacterNotUppercaseNric_throwsIllegalArgumentException() {
        String invalidNric = "s1234567B";
        assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void isValidNric() {
        // null NRIC
        assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));

        // invalid NRICs
        assertFalse(Nric.isValidNric("")); // empty string
        assertFalse(Nric.isValidNric(" ")); // spaces only
        assertFalse(Nric.isValidNric("A1234567B")); // invalid starting character
        assertFalse(Nric.isValidNric("S12345678")); // invalid ending character
        assertFalse(Nric.isValidNric("S123456B")); // less than 7 digits
        assertFalse(Nric.isValidNric("S12345678B")); // more than 7 digits
        assertFalse(Nric.isValidNric("S1234567b")); // ending character not uppercase
        assertFalse(Nric.isValidNric("s1234567B")); // starting character not uppercase

        // valid NRICs
        assertTrue(Nric.isValidNric("S1234567A"));
        assertTrue(Nric.isValidNric("T7654321Z"));
        assertTrue(Nric.isValidNric("F1234567X"));
        assertTrue(Nric.isValidNric("G7654321Y"));
        assertTrue(Nric.isValidNric("M1234567K"));
    }

    @Test
    public void equals() {
        Nric nric1 = new Nric("S1234567A");
        Nric nric2 = new Nric("S1234567A");
        Nric nric3 = new Nric("T7654321Z");

        // same object -> returns true
        assertTrue(nric1.equals(nric1));

        // same values -> returns true
        assertTrue(nric1.equals(nric2));

        // different types -> returns false
        assertFalse(nric1.equals(1));

        // null -> returns false
        assertFalse(nric1.equals(null));

        // different NRIC -> returns false
        assertFalse(nric1.equals(nric3));
    }
}
