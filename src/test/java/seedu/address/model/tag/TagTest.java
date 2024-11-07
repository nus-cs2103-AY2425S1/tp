package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.address.model.delivery.Quantity;

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
        
        // blank tag
        assertFalse(Tag.isValidTagName("")); // empty string
        assertFalse(Tag.isValidTagName(" ")); // spaces only

        // invalid tag name
        assertFalse(Tag.isValidTagName("linke$")); // not alphanumeric
        assertFalse(Tag.isValidTagName("a".repeat(51))); // 51 characters

        // valid quantities
        assertTrue(Tag.isValidTagName("hello"));
        assertTrue(Tag.isValidTagName("a".repeat(49))); // 49 characters
        assertTrue(Tag.isValidTagName("a".repeat(50))); // 50 characters

    }

}
