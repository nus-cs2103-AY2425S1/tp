package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // valid tag names
        assertTrue(Tag.isValidTagName("friend"));
        assertTrue(Tag.isValidTagName("groupA123"));

        // invalid tag names
        assertFalse(Tag.isValidTagName("friend!"));
        assertFalse(Tag.isValidTagName(""));
    }

    @Test
    public void equals() {
        Tag tag1 = new Tag("friend");
        Tag tag2 = new Tag("friend");
        Tag tag3 = new Tag("colleague");

        // same object -> returns true
        assertTrue(tag1.equals(tag1));

        // different object, same name -> returns true
        assertEquals(tag1, tag2);

        // different object, different name -> returns false
        assertNotEquals(tag1, tag3);

        // null -> returns false
        assertNotEquals(null, tag1);

        // different type -> returns false
        assertNotEquals("friend", tag1);
    }

    @Test
    public void hashCode_sameTagName_sameHashCode() {
        Tag tag1 = new Tag("friend");
        Tag tag2 = new Tag("friend");

        assertEquals(tag1.hashCode(), tag2.hashCode());
    }

    @Test
    public void hashCode_differentTagName_differentHashCode() {
        Tag tag1 = new Tag("friend");
        Tag tag2 = new Tag("colleague");

        assertNotEquals(tag1.hashCode(), tag2.hashCode());
    }
}
