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
    public void isValidTagName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void isValidTagName_validTag_success() {
        boolean isValid = Tag.isValidTagName("tagtest");
        assertTrue(isValid);
    }
    @Test
    public void isValidTagName_validTagWithSpace_success() {
        boolean isValid = Tag.isValidTagName("tag test");
        assertTrue(isValid);
    }

    @Test
    public void isValidTagName_validTagWithParenthesis_success() {
        boolean isValid = Tag.isValidTagName("(tagtest)");
        assertTrue(isValid);
    }

    @Test
    public void isValidTagName_validTagWithApostrophe_success() {
        boolean isValid = Tag.isValidTagName("tag's test");
        assertTrue(isValid);
    }

    @Test
    public void isValidTagName_emptyString_failure() {
        boolean isValid = Tag.isValidTagName("");
        assertFalse(isValid);
    }

    @Test
    public void isValidTagName_exceedsMaxLength_failure() {
        boolean isValid = Tag.isValidTagName("a".repeat(51));
        assertFalse(isValid);
    }

    @Test
    public void isValidTagName_invalidCharactersAscii_failure() {
        boolean isValid = Tag.isValidTagName("@^/");
        assertFalse(isValid);
    }

    @Test
    public void isValidTagName_invalidCharactersNonAscii_failure() {
        boolean isValid = Tag.isValidTagName("∞•™§");
        assertFalse(isValid);
    }
}
