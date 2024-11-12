package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagName(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new TagName(invalidTagName));
    }

    @Test
    public void isValidName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> TagName.isValidName(null));
    }

    @Test
    public void isValidName_validName_returnsTrue() {
        // Valid tag names
        assertTrue(TagName.isValidName("friend"));
        assertTrue(TagName.isValidName("Work"));
        assertTrue(TagName.isValidName("123"));
        assertTrue(TagName.isValidName("friend 123"));
        assertTrue(TagName.isValidName("Family Time")); // Spaces and alphabets
    }

    @Test
    public void isValidName_invalidName_returnsFalse() {
        // Invalid tag names
        assertFalse(TagName.isValidName("")); // Empty string
        assertFalse(TagName.isValidName(" ")); // Spaces only
        assertFalse(TagName.isValidName("@home")); // Special character '@'
        assertFalse(TagName.isValidName("home!")); // Special character '!'
        assertFalse(TagName.isValidName(" friend")); // Leading space
    }

    @Test
    public void equals() {
        TagName tagName = new TagName("friend");

        assertTrue(tagName.equals(tagName));

        TagName tagNameCopy = new TagName("friend");
        assertTrue(tagName.equals(tagNameCopy));

        assertFalse(tagName.equals(5));

        assertFalse(tagName.equals(null));

        TagName differentTagName = new TagName("colleague");
        assertFalse(tagName.equals(differentTagName));
    }

    @Test
    public void hashCode_sameTagName_returnsSameHashCode() {
        TagName tagName = new TagName("friend");
        TagName tagNameCopy = new TagName("friend");
        assertEquals(tagName.hashCode(), tagNameCopy.hashCode());
    }

    @Test
    public void hashCode_differentTagName_returnsDifferentHashCode() {
        TagName tagName = new TagName("friend");
        TagName differentTagName = new TagName("colleague");
        assertFalse(tagName.hashCode() == differentTagName.hashCode());
    }

    @Test
    public void toString_validTagName_returnsStringRepresentation() {
        TagName tagName = new TagName("friend");
        assertEquals("friend", tagName.toString());
    }

    @Test
    public void matches_validRegex_returnsTrue() {
        TagName tagName = new TagName("friend");
        assertTrue(tagName.matches("[\\p{Alnum}][\\p{Alnum} ]*"));
    }
}
