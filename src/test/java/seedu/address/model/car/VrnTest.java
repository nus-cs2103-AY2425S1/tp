package seedu.address.model.car;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class VrnTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Vrn(null));
    }

    @Test
    public void constructor_invalidVrn_throwsIllegalArgumentException() {
        String invalidVrn = "";
        assertThrows(IllegalArgumentException.class, () -> new Vrn(invalidVrn));
    }

    @Test
    public void isValidVrn() {
        // null vrn
        assertThrows(NullPointerException.class, () -> Vrn.isValidVrn(null));

        // blank vrn
        assertFalse(Vrn.isValidVrn("")); // empty string
        assertFalse(Vrn.isValidVrn(" ")); // spaces only

        // missing parts
        assertFalse(Vrn.isValidVrn(" 9515 P")); // missing prefix
        assertFalse(Vrn.isValidVrn("SJH  P")); // missing number
        assertFalse(Vrn.isValidVrn("SJH 9514 ")); // missing suffix

        // invalid parts
        assertFalse(Vrn.isValidVrn("S7H 9514 P")); // invalid prefix
        assertFalse(Vrn.isValidVrn("SJH 951A P")); // invalid number
        assertFalse(Vrn.isValidVrn("SJH 9514 9")); // invalid suffix
        assertFalse(Vrn.isValidVrn("S_H 9514 9")); // invalid characters in prefix
        assertFalse(Vrn.isValidVrn("SJH 95@4 *")); // invalid characters in number
        assertFalse(Vrn.isValidVrn("SJH 9514 *")); // invalid characters in suffix

        // invalid spacing
        assertFalse(Vrn.isValidVrn("SJH9514 P")); // invalid prefix spacing
        assertFalse(Vrn.isValidVrn("SJH 9514P")); // invalid suffix spacing
        assertFalse(Vrn.isValidVrn("SJH9514P")); // invalid prefix and suffix spacing

        // valid Vrn
        assertTrue(Vrn.isValidVrn("SJH 9514 P"));
        assertTrue(Vrn.isValidVrn("SH 8942 L"));
        assertTrue(Vrn.isValidVrn("S 6780 S"));
        assertTrue(Vrn.isValidVrn("SHA 781 D"));
        assertTrue(Vrn.isValidVrn("SM 563 Z"));
        assertTrue(Vrn.isValidVrn("S 224 X"));
        assertTrue(Vrn.isValidVrn("SJH 81 E"));
        assertTrue(Vrn.isValidVrn("SL 53 J"));
        assertTrue(Vrn.isValidVrn("S 14 K"));
        assertTrue(Vrn.isValidVrn("SNG 9 Z"));
        assertTrue(Vrn.isValidVrn("SM 4 X"));
        assertTrue(Vrn.isValidVrn("S 1 Y"));

        // invalid Vrn (fails checksum)
        assertFalse(Vrn.isValidVrn("SHA 7891 A")); // Z
        assertFalse(Vrn.isValidVrn("SM 2234 B")); // K
        assertFalse(Vrn.isValidVrn("S 3871 C")); // H
        assertFalse(Vrn.isValidVrn("SJH 910 D")); // M
        assertFalse(Vrn.isValidVrn("SL 782 E")); // M
        assertFalse(Vrn.isValidVrn("S 293 F")); // X
        assertFalse(Vrn.isValidVrn("SNG 62 G")); // T
        assertFalse(Vrn.isValidVrn("SM 55 H")); // Z
        assertFalse(Vrn.isValidVrn("S 23 I")); // J
        assertFalse(Vrn.isValidVrn("SJH 7 J")); // X
        assertFalse(Vrn.isValidVrn("SH 6 K")); // S
        assertFalse(Vrn.isValidVrn("S 2 L")); // U
    }

    @Test
    public void equals() {
        Vrn vrn = new Vrn("SJH 9514 P");

        // same values -> returns true
        assertTrue(vrn.equals(new Vrn("SJH 9514 P")));

        // same object -> returns true
        assertTrue(vrn.equals(vrn));

        // null -> returns false
        assertFalse(vrn.equals(null));

        // different types -> returns false
        assertFalse(vrn.equals(5.0f));

        // different values -> returns false
        assertFalse(vrn.equals(new Vrn("SH 8942 L")));
    }
}
