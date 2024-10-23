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

        // valid identity numbers (S or T [Type NRIC])
        assertTrue(IdentityNumber.isValidIdentityNumber("S6116686F"));
        assertTrue(IdentityNumber.isValidIdentityNumber("S1005756F"));
        assertTrue(IdentityNumber.isValidIdentityNumber("S1210769B"));
        assertTrue(IdentityNumber.isValidIdentityNumber("S4222724B"));
        assertTrue(IdentityNumber.isValidIdentityNumber("S1383485G"));
        assertTrue(IdentityNumber.isValidIdentityNumber("S7147129B"));
        assertTrue(IdentityNumber.isValidIdentityNumber("S5940368J"));

        assertTrue(IdentityNumber.isValidIdentityNumber("T2938587J"));
        assertTrue(IdentityNumber.isValidIdentityNumber("T2611250D"));
        assertTrue(IdentityNumber.isValidIdentityNumber("T9733087A"));
        assertTrue(IdentityNumber.isValidIdentityNumber("T6169846E"));
        assertTrue(IdentityNumber.isValidIdentityNumber("T9704009A"));
        assertTrue(IdentityNumber.isValidIdentityNumber("T5362621H"));
        assertTrue(IdentityNumber.isValidIdentityNumber("T6659225H"));

        //valid identity numbers (F or G [Type FIN])
        assertTrue(IdentityNumber.isValidIdentityNumber("F5999705W"));
        assertTrue(IdentityNumber.isValidIdentityNumber("F5586227M"));
        assertTrue(IdentityNumber.isValidIdentityNumber("F7378724Q"));
        assertTrue(IdentityNumber.isValidIdentityNumber("F2135323W"));
        assertTrue(IdentityNumber.isValidIdentityNumber("F5934095Q"));
        assertTrue(IdentityNumber.isValidIdentityNumber("F8194626N"));
        assertTrue(IdentityNumber.isValidIdentityNumber("F5944510M"));

        assertTrue(IdentityNumber.isValidIdentityNumber("G5384954M"));
        assertTrue(IdentityNumber.isValidIdentityNumber("G0313597M"));
        assertTrue(IdentityNumber.isValidIdentityNumber("G6723425T"));
        assertTrue(IdentityNumber.isValidIdentityNumber("G5382668M"));
        assertTrue(IdentityNumber.isValidIdentityNumber("G8103638T"));
        assertTrue(IdentityNumber.isValidIdentityNumber("G2867837T"));
        assertTrue(IdentityNumber.isValidIdentityNumber("G9276190N"));
    }

    @Test
    public void equals() {
        IdentityNumber identityNumber = new IdentityNumber("F5999705W");

        // same values -> returns true
        assertTrue(identityNumber.equals(new IdentityNumber("F5999705W")));

        // same object -> returns true
        assertTrue(identityNumber.equals(identityNumber));

        // null -> returns false
        assertFalse(identityNumber.equals(null));

        // different types -> returns false
        assertFalse(identityNumber.equals(0x534));

        // different values -> returns false
        assertFalse(identityNumber.equals(new IdentityNumber("S2065586J")));
    }
}
