package seedu.sellsavvy.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.testutil.Assert.assertThrows;

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
    public void isSimilarTo() {
        Tag tag = new Tag("ValidTag");

        // same values -> returns true
        assertTrue(tag.isSimilarTo(new Tag("ValidTag")));

        // same object -> returns true
        assertTrue(tag.isSimilarTo(tag));

        // null -> returns false
        assertThrows(NullPointerException.class, () -> tag.isSimilarTo(null));

        // different values -> returns false
        assertFalse(tag.isSimilarTo(new Tag("Tag")));

        // Only different in casing -> returns true
        assertTrue(tag.isSimilarTo(new Tag("validtag")));

    }

}
