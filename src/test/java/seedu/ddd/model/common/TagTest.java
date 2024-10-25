package seedu.ddd.model.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.ddd.model.common.Tag;

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

        // invalid tag name
        assertFalse(Tag.isValidTagName("")); // empty string
        assertFalse(Tag.isValidTagName(" ")); // spaces only
        assertFalse(Tag.isValidTagName("peter jack")); // contains space
        assertFalse(Tag.isValidTagName("^!@*")); // invalid characters only
        assertFalse(Tag.isValidTagName("peter*")); // contains non-alphanumeric characters

        // valid tag name
        assertTrue(Tag.isValidTagName("12345")); // numbers only
        assertTrue(Tag.isValidTagName("peter")); // alphabet only
        assertTrue(Tag.isValidTagName("abc123")); // alphanumeric characters
        assertTrue(Tag.isValidTagName("a-b-c")); // contains dashes
    }

}
