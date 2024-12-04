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
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void equals() {
        Tag tag = new Tag("Test");

        // same object -> returns true
        assertTrue(tag.equals(tag));

        // null -> return false
        assertFalse(tag.equals(null));

        // different type -> returns false
        assertFalse(tag.equals(5));

        // different string -> returns false
        Tag otherTag = new Tag("A");
        assertFalse(tag.equals(otherTag));

        // same string -> returns true
        otherTag = new Tag("Test");
        assertTrue(tag.equals(otherTag));
    }

}
