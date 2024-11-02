package tuteez.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tuteez.testutil.Assert.assertThrows;

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

    @Test
    public void isValidTagName_validInputs_returnTrue() {
        assertTrue(Tag.isValidTagName("Secondary 4 Math"));
        assertTrue(Tag.isValidTagName("Science123"));
    }
    @Test
    public void isValidTagName_invalidInputs_returnFalse() {
        assertFalse(Tag.isValidTagName("Secondary 4 Math!"));
        assertFalse(Tag.isValidTagName("(Secondary 1 Science)+"));
    }

}
