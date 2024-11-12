package seedu.academyassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.academyassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IcTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Ic(null));
    }

    @Test
    public void constructor_invalidIc_throwsIllegalArgumentException() {
        String invalidIc = "";
        assertThrows(IllegalArgumentException.class, () -> new Ic(invalidIc));
    }

    @Test
    public void isValidIc() {
        // null IC
        assertThrows(NullPointerException.class, () -> Ic.isValidIc(null));

        // invalid IC numbers
        assertFalse(Ic.isValidIc("")); // empty string
        assertFalse(Ic.isValidIc(" ")); // spaces only
        assertFalse(Ic.isValidIc("S91D")); // less than 7 numbers in between
        assertFalse(Ic.isValidIc("P1234567D")); // starts with an alphabet other than S,T,F,G,M
        assertFalse(Ic.isValidIc("o1234567D")); // starts with a lower case alphabet other than S,T,F,G,M
        assertFalse(Ic.isValidIc("S12345671234493423B")); // more than 7 numbers in between
        assertFalse(Ic.isValidIc("S123456 7F")); // spaces within IC
        assertFalse(Ic.isValidIc("S 1234567F")); // spaces within IC
        assertFalse(Ic.isValidIc("1234567F")); // does not start with a valid alphabet
        assertFalse(Ic.isValidIc("F1234567")); // does not end with a valid alphabet

        // valid IC numbers
        assertTrue(Ic.isValidIc("F1234567B")); // start with F
        assertTrue(Ic.isValidIc("S9876432H")); // start with S
        assertTrue(Ic.isValidIc("T0123780X")); // start with T
        assertTrue(Ic.isValidIc("G0123790P")); // start with G
        assertTrue(Ic.isValidIc("M0127890K")); // start with M
        assertTrue(Ic.isValidIc("G0000000K")); // same numbers in between
        assertTrue(Ic.isValidIc("f7300609K")); // starts with lower case
        assertTrue(Ic.isValidIc("M1625098k")); // ends with lower case
        assertTrue(Ic.isValidIc("s5672367b")); // starts and ends with lower case
    }

    @Test
    public void equals() {
        Ic ic = new Ic("M1234567X");

        // same values -> returns true
        assertTrue(ic.equals(new Ic("M1234567X")));

        // same object -> returns true
        assertTrue(ic.equals(ic));

        // upper case and lower case of the same ic -> returns true
        assertTrue(ic.equals(new Ic("m1234567x"))); // starts and ends with lower case letter
        assertTrue(ic.equals(new Ic("M1234567x"))); // ends with lower case letter
        assertTrue(ic.equals(new Ic("m1234567X"))); // start with lower case letter

        // null -> returns false
        assertFalse(ic.equals(null));

        // different types -> returns false
        assertFalse(ic.equals(5.0f));

        // different values -> returns false
        assertFalse(ic.equals(new Ic("F0987543M")));
        assertFalse(ic.equals(new Ic("F1234567X"))); // different beginning letter
        assertFalse(ic.equals(new Ic("M1234567B"))); // different ending letter
        assertFalse(ic.equals(new Ic("M1234067X"))); // different number in between
    }
}
