package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_validTagName_returnsTagObject() {
        // Single word
        assertEquals(new Tag("Name"), new Tag("Name"));
        // Single space, two words
        assertEquals(new Tag("Na me"), new Tag("Na me"));
        // Space before
        assertEquals(new Tag(" Name"), new Tag("Name"));
        assertEquals(new Tag(" Na me"), new Tag("Na me"));
        // Space after
        assertEquals(new Tag("Name "), new Tag("Name"));
        assertEquals(new Tag("Na me "), new Tag("Na me"));
        // More than one space between
        assertEquals(new Tag("Na     me"), new Tag("Na me"));
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
}
