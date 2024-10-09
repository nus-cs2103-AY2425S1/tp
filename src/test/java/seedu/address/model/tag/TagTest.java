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
        String invalidTagName = "friends";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tag name
        assertFalse(Tag.isValidTagName("friends"));
        assertFalse(Tag.isValidTagName(" "));
        assertFalse(Tag.isValidTagName("admin"));
        assertFalse(Tag.isValidTagName("marketing"));

        // valid tag name
        assertTrue(Tag.isValidTagName("President"));
        assertTrue(Tag.isValidTagName("Vice President"));
        assertTrue(Tag.isValidTagName("Admin"));
        assertTrue(Tag.isValidTagName("Marketing"));
        assertTrue(Tag.isValidTagName("Events (internal)"));
        assertTrue(Tag.isValidTagName("Events (external)"));
        assertTrue(Tag.isValidTagName("External Relations"));
    }

}
