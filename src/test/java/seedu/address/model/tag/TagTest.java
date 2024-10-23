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
        assertThrows(AssertionError.class, () -> Tag.isValidTagName(null));

        // invalid tags
        assertFalse(Tag.isValidTagName("")); // empty string
        assertFalse(Tag.isValidTagName(" ")); // spaces only
        assertFalse(Tag.isValidTagName("software engineer")); // spaces within tag
        assertFalse(Tag.isValidTagName("ui/ux")); // non alphanumeric characters

        // valid tags
        assertTrue(Tag.isValidTagName("a")); // exactly 1 character
        assertTrue(Tag.isValidTagName("swe")); // alphabets only
        assertTrue(Tag.isValidTagName("softwareEngineer")); // Mixed case
        assertTrue(Tag.isValidTagName("123")); // numbers only
        assertTrue(Tag.isValidTagName("web3")); // alphanumeric
        assertTrue(Tag.isValidTagName(
                "softwareInTestSeniorEngineerForWeb3andSaasDevelopment")); // long tag
    }

    @Test
    public void equals() {
        Tag tag = new Tag("swe");

        // same values -> returns true
        assertTrue(tag.equals(new Tag("swe")));

        // same object -> returns true
        assertTrue(tag.equals(tag));

        // null -> returns false
        assertFalse(tag.equals(null));

        // different types -> returns false
        assertFalse(tag.equals(5.0f));

        // different values -> returns false
        assertFalse(tag.equals(new Tag("backend")));
    }

}
