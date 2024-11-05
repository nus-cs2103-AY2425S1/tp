package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
        assertThrows(NullPointerException.class, () -> Tag.isValidTag(null));
    }

    @Test
    public void isValidTag_invalidTag_returnsFalse() {
        String invalidTag = "Admin";
        assertFalse(Tag.TagType.isValidTag(invalidTag));
    }

    @Test
    public void equals_nonTagObject_returnsFalse() {
        Tag tag = new Tag("Student");
        String nonTagObject = "NotATag";

        assertFalse(tag.equals(nonTagObject));
    }

}
