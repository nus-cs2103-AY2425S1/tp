package seedu.eventtory.model.commons.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventtory.testutil.Assert.assertThrows;

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

        // just hypyens and underscores
        assertFalse(() -> Tag.isValidTagName("-"));
        assertFalse(() -> Tag.isValidTagName("_"));
        assertFalse(() -> Tag.isValidTagName("-_"));
        assertFalse(() -> Tag.isValidTagName("abcdefghijklmnopqrstuvwxyz12345")); // length 31, too long
        assertFalse(() -> Tag.isValidTagName("abcdefghijklmnopqrstuvwxyz1234567890")); // length 36, too long

        // valid tags
        assertTrue(() -> Tag.isValidTagName("-tag"));
        assertTrue(() -> Tag.isValidTagName("_tag"));
        assertTrue(() -> Tag.isValidTagName("tag-name"));
        assertTrue(() -> Tag.isValidTagName("tag_name"));
        assertTrue(() -> Tag.isValidTagName("tagname"));
        assertTrue(() -> Tag.isValidTagName("TAGNAME"));
        assertTrue(() -> Tag.isValidTagName("tagname"));
        assertTrue(() -> Tag.isValidTagName("abcdefghijklmnopqrstuvwxyz1234")); // length 30, acceptable
    }

    @Test
    public void equals() {
        Tag tag = new Tag("tag");
        // same values -> returns true
        assertTrue(tag.equals(new Tag("tag")));

        // same object -> returns true
        assertTrue(tag.equals(tag));

        // null -> returns false
        assertFalse(tag.equals(null));

        // different type -> returns false
        assertFalse(tag.equals(5));

        // different tag -> returns false
        assertFalse(tag.equals(new Tag("different")));

        // same tags but difference case -> returns true
        assertTrue(tag.equals(new Tag("TAG")));
    }

}
