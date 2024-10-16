package seedu.address.model.wedding;

import org.junit.jupiter.api.Test;
import seedu.address.model.wedding.WeddingName;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;

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
        WeddingName WeddingName = new WeddingName("friend");

        assertTrue(WeddingName.equals(WeddingName));

        WeddingName WeddingNameCopy = new WeddingName("friend");
        assertTrue(WeddingName.equals(WeddingNameCopy));

        assertFalse(WeddingName.equals(5));

        assertFalse(WeddingName.equals(null));

        WeddingName differentWeddingName = new WeddingName("colleague");
        assertFalse(WeddingName.equals(differentWeddingName));
    }

    @Test
    public void hashCode_sameWeddingName_returnsSameHashCode() {
        WeddingName WeddingName = new WeddingName("friend");
        WeddingName WeddingNameCopy = new WeddingName("friend");
        assertEquals(WeddingName.hashCode(), WeddingNameCopy.hashCode());
    }

    @Test
    public void hashCode_differentWeddingName_returnsDifferentHashCode() {
        WeddingName WeddingName = new WeddingName("friend");
        WeddingName differentWeddingName = new WeddingName("colleague");
        assertFalse(WeddingName.hashCode() == differentWeddingName.hashCode());
    }

    @Test
    public void toString_validWeddingName_returnsStringRepresentation() {
        WeddingName WeddingName = new WeddingName("friend");
        assertEquals("friend", WeddingName.toString());
    }

    @Test
    public void matches_validRegex_returnsTrue() {
        WeddingName WeddingName = new WeddingName("friend");
        assertTrue(WeddingName.matches("[\\p{Alnum}][\\p{Alnum} ]*"));
    }
}
