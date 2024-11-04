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
        Tag tag1 = new Tag("friend");
        Tag tag2 = new Tag("friend");
        Tag tag3 = new Tag("family");

        // same object -> returns true
        assertTrue(tag1.equals(tag1));

        // same values -> returns true
        assertTrue(tag1.equals(tag2));

        // different values -> returns false
        assertFalse(tag1.equals(tag3));

        // null -> returns false
        assertFalse(tag1.equals(null));

        // different type -> returns false
        assertFalse(tag1.equals("friend"));
    }

}
