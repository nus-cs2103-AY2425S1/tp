package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(new TagName(invalidTagName)));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void isValidTagName_validTagName_returnsTrue() {
        // Valid tag names
        assertTrue(Tag.isValidTagName("friend"));
        assertTrue(Tag.isValidTagName("work"));
        assertTrue(Tag.isValidTagName("123"));
        assertTrue(Tag.isValidTagName("friend 123")); // Alphanumeric with spaces
    }

    @Test
    public void isValidTagName_invalidTagName_returnsFalse() {
        // Invalid tag names
        assertFalse(Tag.isValidTagName("")); // Empty string
        assertFalse(Tag.isValidTagName(" ")); // Spaces only
        assertFalse(Tag.isValidTagName("@home")); // Special character not allowed
        assertFalse(Tag.isValidTagName("friend!")); // Special character not allowed
    }

    @Test
    public void isSameTag_sameTag_returnsTrue() {
        Tag tag = new Tag(new TagName("friend"));
        assertTrue(tag.isSameTag(tag));
    }

    @Test
    public void isSameTag_identicalTagName_returnsTrue() {
        Tag tag1 = new Tag(new TagName("friend"));
        Tag tag2 = new Tag(new TagName("friend"));
        assertTrue(tag1.isSameTag(tag2));
    }

    @Test
    public void isSameTag_differentTagName_returnsFalse() {
        Tag tag1 = new Tag(new TagName("friend"));
        Tag tag2 = new Tag(new TagName("colleague"));
        assertFalse(tag1.isSameTag(tag2));
    }

    @Test
    public void equals_identicalTagName_returnsTrue() {
        Tag tag1 = new Tag(new TagName("friend"));
        Tag tag2 = new Tag(new TagName("friend"));
        assertTrue(tag1.equals(tag2));
    }

    @Test
    public void equals_differentTagName_returnsFalse() {
        Tag tag1 = new Tag(new TagName("friend"));
        Tag tag2 = new Tag(new TagName("colleague"));
        assertFalse(tag1.equals(tag2));
    }

    @Test
    public void toString_validTag_returnsExpectedFormat() {
        Tag tag = new Tag(new TagName("friend"));
        assertTrue(tag.toString().equals("[friend]"));
    }

}
