package seedu.address.model.tag;

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

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void equals() {
        Tag tag = new Tag("tag");
        // same values -> returns true
        Tag tagCopy = new Tag("tag");
        assert(tag.equals(tagCopy));
        // same object -> returns true
        assert(tag.equals(tag));
        // null -> returns false
        assert(!tag.equals(null));
        // different type -> returns false
        assert(!tag.equals(5));
        // different tag name -> returns false
        Tag differentTag = new Tag("different");
        assert(!tag.equals(differentTag));
    }

}
