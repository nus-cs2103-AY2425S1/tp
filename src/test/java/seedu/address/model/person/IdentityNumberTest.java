package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IdentityNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new IdentityNumber(null));
    }

    @Test
    public void constructor_invalidIdentificationNumber_throwsIllegalArgumentException() {
        String invalidIdentificationNumber = "";
        assertThrows(IllegalArgumentException.class, () -> new IdentityNumber(invalidIdentificationNumber));
    }

    @Test
    public void isValidIdentityNumber() {
        //null IdentityNumber
        assertThrows(NullPointerException.class, () -> IdentityNumber.isValidIdentityNumber(null));

        //blank IdentityNumber
        assertFalse(IdentityNumber.isValidIdentityNumber("")); // empty string
        assertFalse(IdentityNumber.isValidIdentityNumber(" ")); //spaces only

        // missing parts
        assertFalse(IdentityNumber.isValidIdentityNumber("S792827H")); // missing one number
        assertFalse(IdentityNumber.isValidIdentityNumber("S7275H")); // missing Several numbers
        assertFalse(IdentityNumber.isValidIdentityNumber("S7928275")); // missing suffix
        assertFalse(IdentityNumber.isValidIdentityNumber("7928275H")); // missing prefix

        // invalid structures
        assertFalse(IdentityNumber.isValidIdentityNumber("Z7928275F")); // invalid prefix
        assertFalse(IdentityNumber.isValidIdentityNumber("S3924638A")); // invalid suffix

        // invalid checksum
        assertFalse(IdentityNumber.isValidIdentityNumber("S1234567C"));
        assertFalse(IdentityNumber.isValidIdentityNumber("S7654321D"));

        // valid identity numbers
        assertTrue(IdentityNumber.isValidIdentityNumber("S6116686F"));
        assertTrue(IdentityNumber.isValidIdentityNumber("S1005756F"));
        assertTrue(IdentityNumber.isValidIdentityNumber("S1210769B"));
        assertTrue(IdentityNumber.isValidIdentityNumber("S4222724B"));
        assertTrue(IdentityNumber.isValidIdentityNumber("S1383485G"));
        assertTrue(IdentityNumber.isValidIdentityNumber("S7147129B"));
        assertTrue(IdentityNumber.isValidIdentityNumber("S5940368J"));
    }
}
