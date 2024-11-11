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
    public void constructor_emptyTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "best friend";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName_nullTagName_throwsNullPointerException() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void isValidTagName_validTags() {
        // valid tags
        assertTrue(Tag.isValidTagName("best-friend"));
        assertTrue(Tag.isValidTagName("friend"));
    }

    @Test
    public void isValidTagName_invalidTags() {
        // invalid tags
        assertFalse(Tag.isValidTagName("best friend"));
        assertFalse(Tag.isValidTagName("-friend"));
        assertFalse(Tag.isValidTagName("friend-"));
        assertFalse(Tag.isValidTagName("friend--best"));
        assertFalse(Tag.isValidTagName("-"));
    }

}
