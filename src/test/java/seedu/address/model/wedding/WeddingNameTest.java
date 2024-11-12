package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWeddings.VALID_WEDDING_STRING_AMY_WEDDING;
import static seedu.address.testutil.TypicalWeddings.VALID_WEDDING_STRING_BOB_WEDDING;

import org.junit.jupiter.api.Test;

public class WeddingNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WeddingName(null));
    }

    @Test
    public void constructor_invalidWeddingName_throwsIllegalArgumentException() {
        String invalidWeddingName = "";
        assertThrows(IllegalArgumentException.class, () -> new WeddingName(invalidWeddingName));
    }

    @Test
    public void isValidName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> WeddingName.isValidName(null));
    }

    @Test
    public void isValidName_validName_returnsTrue() {
        // Valid Wedding names
        assertTrue(WeddingName.isValidName("friend"));
        assertTrue(WeddingName.isValidName("Work"));
        assertTrue(WeddingName.isValidName("123"));
        assertTrue(WeddingName.isValidName("friend 123"));
        assertTrue(WeddingName.isValidName("Family Time")); // Spaces and alphabets
        assertTrue(WeddingName.isValidName("Name /.,'&:()"));
    }

    @Test
    public void isValidName_invalidName_returnsFalse() {
        // Invalid Wedding names
        assertFalse(WeddingName.isValidName("")); // Empty string
        assertFalse(WeddingName.isValidName(" ")); // Spaces only
        assertFalse(WeddingName.isValidName("@home")); // Special character '@'
        assertFalse(WeddingName.isValidName("home!")); // Special character '!'
        assertFalse(WeddingName.isValidName(" friend")); // Leading space
    }

    @Test
    public void equals() {
        WeddingName weddingName = new WeddingName(VALID_WEDDING_STRING_AMY_WEDDING);

        assertTrue(weddingName.equals(weddingName));

        WeddingName weddingNameCopy = new WeddingName(VALID_WEDDING_STRING_AMY_WEDDING);
        assertTrue(weddingName.equals(weddingNameCopy));

        assertFalse(weddingName.equals(5));

        assertFalse(weddingName.equals(null));

        WeddingName differentWeddingName = new WeddingName(VALID_WEDDING_STRING_BOB_WEDDING);
        assertFalse(weddingName.equals(differentWeddingName));
    }

    @Test
    public void hashCode_sameWeddingName_returnsSameHashCode() {
        WeddingName weddingName = new WeddingName(VALID_WEDDING_STRING_AMY_WEDDING);
        WeddingName weddingNameCopy = new WeddingName(VALID_WEDDING_STRING_AMY_WEDDING);
        assertEquals(weddingName.hashCode(), weddingNameCopy.hashCode());
    }

    @Test
    public void hashCode_differentWeddingName_returnsDifferentHashCode() {
        WeddingName weddingName = new WeddingName(VALID_WEDDING_STRING_AMY_WEDDING);
        WeddingName differentWeddingName = new WeddingName(VALID_WEDDING_STRING_BOB_WEDDING);
        assertFalse(weddingName.hashCode() == differentWeddingName.hashCode());
    }

    @Test
    public void toString_validWeddingName_returnsStringRepresentation() {
        WeddingName amyWeddingName = new WeddingName(VALID_WEDDING_STRING_AMY_WEDDING);
        assertEquals("Amy's Wedding", amyWeddingName.toString());
    }

    @Test
    public void matches_validRegex_returnsTrue() {
        WeddingName amyWeddingName = new WeddingName(VALID_WEDDING_STRING_AMY_WEDDING);
        assertTrue(amyWeddingName.matches("[\\p{Alnum}'][\\p{Alnum} ']*"));
    }
}
