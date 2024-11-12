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
    public void constructor_tagExceedsLengthLimit_throwsIllegalArgumentException() {
        String longTagName = "abcdefghijklmnopqrstuvwxyz123456789";
        assertThrows(IllegalArgumentException.class, () -> new Tag(longTagName));
    }

    @Test
    public void equals() {
        Tag firstTag = new Tag("1");
        Tag copy = new Tag("1");
        Tag secondTag = new Tag("2");
        assertTrue(firstTag.equals(firstTag));
        assertFalse(firstTag.equals(null));
        assertTrue(firstTag.equals(copy));
        assertFalse(firstTag.equals(secondTag));
    }
}
