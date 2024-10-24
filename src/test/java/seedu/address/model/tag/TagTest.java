package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.tag.Tag.BUYER_TAG;
import static seedu.address.model.tag.Tag.FAVOURITE_TAG;
import static seedu.address.model.tag.Tag.SELLER_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {
    public static final String VALID_TAG_NAME = "person";
    public static final Tag VALID_TAG_USING_VALID_NAME = Tag.of(VALID_TAG_NAME);
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
    public void tagsAreEqual() {
        assertEquals(FAVOURITE_TAG, Tag.of("favourite"));
        assertEquals(BUYER_TAG, Tag.of("buyer"));
        assertEquals(SELLER_TAG, Tag.of("seller"));
        assertEquals(VALID_TAG_USING_VALID_NAME, new Tag(VALID_TAG_NAME));
    }
}
