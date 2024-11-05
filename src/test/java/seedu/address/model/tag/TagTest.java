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

        assertTrue(Tag.isValidTagName("elderly")); // normal looking tag
        assertTrue(Tag.isValidTagName("ex-convict")); // tag with a hyphen
        assertTrue(Tag.isValidTagName("dairy allergy")); // tag with a space
        assertTrue(Tag.isValidTagName("cute dog!!!! :D")); // tag with multiple special characters

        assertFalse(Tag.isValidTagName("")); // empty string
        assertFalse(Tag.isValidTagName("   ")); // string with only spaces
        assertFalse(Tag.isValidTagName("   \t\n  ")); // string with different whitespace characters
    }

}
