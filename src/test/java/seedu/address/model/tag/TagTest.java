package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void equals() {
        Tag firstTag = new Tag("first");
        Tag secondTag = new Tag("second");

        // same object -> returns true
        assertTrue(firstTag.equals(firstTag));

        // same values -> returns true
        assertTrue(firstTag.equals(new Tag("first")));
        assertTrue(firstTag.equals(new Tag("FIrst")));

        // different types -> returns false
        assertFalse(firstTag.equals(1));

        // null -> returns false
        assertFalse(firstTag.equals(null));

        // different values -> returns false
        assertFalse(firstTag.equals(secondTag));
    }
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
        assertFalse(Tag.isValidTagName(""));
        assertFalse(Tag.isValidTagName("ab#"));

        // valid tag name
        assertTrue(Tag.isValidTagName("first"));
        assertTrue(Tag.isValidTagName("with  space "));
    }


}
