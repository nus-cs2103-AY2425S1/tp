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
        assertFalse(Vrn.isValidVrn("9515P")); // missing prefix
        assertFalse(Vrn.isValidVrn("SJHP")); // missing number
        assertFalse(Vrn.isValidVrn("SJH9514")); // missing suffix

        // invalid parts
        assertFalse(Vrn.isValidVrn("S7H9514P")); // invalid prefix
        assertFalse(Vrn.isValidVrn("SJH951AP")); // invalid number
        assertFalse(Vrn.isValidVrn("SJH95149")); // invalid suffix
        assertFalse(Vrn.isValidVrn("S_H95149")); // invalid characters in prefix
        assertFalse(Vrn.isValidVrn("SJH95@4*")); // invalid characters in number
        assertFalse(Vrn.isValidVrn("SJH9514*")); // invalid characters in suffix

        // invalid spacing
        assertFalse(Vrn.isValidVrn("SJH9514 P")); // invalid prefix spacing
        assertFalse(Vrn.isValidVrn("SJH 9514P")); // invalid suffix spacing
        assertFalse(Vrn.isValidVrn("SJH 9514 P")); // invalid prefix and suffix spacing

        // valid Vrn
        assertTrue(Vrn.isValidVrn("SJH9514P"));
        assertTrue(Vrn.isValidVrn("SH8942L"));
        assertTrue(Vrn.isValidVrn("S6780S"));
        assertTrue(Vrn.isValidVrn("SHA781D"));
        assertTrue(Vrn.isValidVrn("SM563Z"));
        assertTrue(Vrn.isValidVrn("S224X"));
        assertTrue(Vrn.isValidVrn("SJH81E"));
        assertTrue(Vrn.isValidVrn("SL53J"));
        assertTrue(Vrn.isValidVrn("S14K"));
        assertTrue(Vrn.isValidVrn("SNG9Z"));
        assertTrue(Vrn.isValidVrn("SM4X"));
        assertTrue(Vrn.isValidVrn("S1Y"));

        // invalid Vrn (fails checksum)
        assertFalse(Vrn.isValidVrn("SHA7891A")); // Z
        assertFalse(Vrn.isValidVrn("SM2234B")); // K
        assertFalse(Vrn.isValidVrn("S3871C")); // H
        assertFalse(Vrn.isValidVrn("SJH910D")); // M
        assertFalse(Vrn.isValidVrn("SL782E")); // M
        assertFalse(Vrn.isValidVrn("S293F")); // X
        assertFalse(Vrn.isValidVrn("SNG62G")); // T
        assertFalse(Vrn.isValidVrn("SM55H")); // Z
        assertFalse(Vrn.isValidVrn("S23I")); // J
        assertFalse(Vrn.isValidVrn("SJH7J")); // X
        assertFalse(Vrn.isValidVrn("SH6K")); // S
        assertFalse(Vrn.isValidVrn("S2L")); // U
    }

    @Test
    public void equals() {
        Vrn vrn = new Vrn("SJH9514P");

        // same values -> returns true
        assertTrue(vrn.equals(new Vrn("SJH9514P")));

        // same object -> returns true
        assertTrue(vrn.equals(vrn));

        // null -> returns false
        assertFalse(vrn.equals(null));

        // different types -> returns false
        assertFalse(vrn.equals(5.0f));

        // different values -> returns false
        assertFalse(vrn.equals(new Vrn("SH8942L")));
    }
}
