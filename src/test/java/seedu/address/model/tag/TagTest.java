package seedu.address.model.tag;

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
    public void constructor_whiteSpaceTagName_throwsIllegalArgumentException() {
        String invalidTagName = "   ";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }


    @Test
    public void constructor_moreThanOneWordTagName_throwsIllegalArgumentException() {
        String invalidTagName = "Best Friend";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void constructor_invalidSymbolsTagName_throwsIllegalArgumentException() {
        String invalidTagName = "Florist!";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void equalsOwnTagName() {
        Tag tag = new Tag("friend");
        assertTrue(tag.equals(tag));
    }

    @Test
    public void equalsSameTagName() {
        Tag tag = new Tag("friend");
        Tag other = new Tag("friend");
        assertTrue(tag.equals(other));
    }

    @Test
    public void equalsUpperCaseTagName() {
        Tag tag = new Tag("friend");
        Tag other = new Tag("FRIEND");
        assertTrue(tag.equals(other));
    }
}
