package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void constructor_validTagName_success() {
        // Valid tag names
        new Tag("High Risk");
        new Tag("Medium Risk");
        new Tag("Low Risk");
        new Tag("low risk");
        new Tag("mEDIUM RiSK");
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tag names
        assertFalse(Tag.isValidTagName("")); // empty string
        assertFalse(Tag.isValidTagName("High")); // partial match

        // valid tag names
        assertTrue(Tag.isValidTagName("High Risk"));
        assertTrue(Tag.isValidTagName("Medium Risk"));
        assertTrue(Tag.isValidTagName("Low Risk"));
        assertTrue(Tag.isValidTagName("high risk")); // lowercase
        assertTrue(Tag.isValidTagName("hiGH riSK")); // mixed case

    }
    @Test
    public void equals() {
        Tag tag1 = new Tag("High Risk");
        Tag tag2 = new Tag("High Risk");
        Tag tag3 = new Tag("Low Risk");
        Tag tag4 = new Tag("low risk");

        // same object -> returns true
        assertTrue(tag1.equals(tag1));

        // same values -> returns true
        assertTrue(tag1.equals(tag2));

        //only capitalization is different -> returns true
        assertTrue(tag3.equals(tag4));

        // different values -> returns false
        assertFalse(tag1.equals(tag3));

        // null -> returns false
        assertFalse(tag1.equals(null));

        // different types -> returns false
        assertFalse(tag1.equals(5)); // comparing with integer
    }

    @Test
    public void hashCode_sameTagName_sameHashCode() {
        Tag tag1 = new Tag("High Risk");
        Tag tag2 = new Tag("High Risk");

        assertEquals(tag1.hashCode(), tag2.hashCode());

        Tag tag3 = new Tag("HiGH RiSk"); //mixed case tags are still considered the same
        assertEquals(tag1.hashCode(), tag3.hashCode());

    }
    @Test
    public void toString_validTagName_correctString() {
        Tag tag = new Tag("Medium Risk");
        assertEquals("Medium Risk", tag.toString());
    }
    @Test
    public void testStandardizeTagName() {
        // Test case 1: "low risk" should return "Low Risk"
        assertEquals("Low Risk", Tag.standardizeTagName("low risk"));

        // Test case 2: "medium risk" should return "Medium Risk"
        assertEquals("Medium Risk", Tag.standardizeTagName("medium risk"));

        // Test case 3: "high risk" should return "High Risk"
        assertEquals("High Risk", Tag.standardizeTagName("high risk"));

    }
}
