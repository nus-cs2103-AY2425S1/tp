package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

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
    public void constructor_tagNamedAll_throwsIllegalArgumentException() {
        String invalidTagName = "all";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void stringToTagSet_emptyString_emptySet() {
        String userInput = "";
        Set<Tag> tagSet = Tag.stringToTagSet(userInput);
        assertEquals(tagSet, new HashSet<Tag>());
    }

    @Test
    public void equals_sameCharsDiffCase_true() {
        Tag tag = new Tag("test");
        Tag other = new Tag("TEST");
        assertEquals(tag, other);
    }

    @Test
    public void equals_diffChars_false() {
        Tag tag = new Tag("test");
        Tag other = new Tag("best");
        assertNotEquals(tag, other);
    }

}
