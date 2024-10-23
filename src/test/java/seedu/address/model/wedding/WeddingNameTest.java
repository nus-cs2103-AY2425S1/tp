package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class WeddingNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WeddingName(null));
    }

    @Test
    public void constructor_invalidWeddingName_throwsIllegalArgumentException() {
        String invalidWeddingName = "";
        assertThrows(IllegalArgumentException.class, () -> new WeddingName(invalidWeddingName));
    }

    @Test
    public void isValidWeddingName() {
        // null name
        assertThrows(NullPointerException.class, () -> WeddingName.isValidWeddingName(null));

        // invalid names
        assertFalse(WeddingName.isValidWeddingName("")); // empty string
        assertFalse(WeddingName.isValidWeddingName(" ")); // spaces only
        assertFalse(WeddingName.isValidWeddingName("^")); // only non-alphanumeric characters
        assertFalse(WeddingName.isValidWeddingName("john*jean")); // contains non-alphanumeric characters
        assertFalse(WeddingName.isValidWeddingName("john and jean")); // does not contain &

        // valid names
        assertTrue(WeddingName.isValidWeddingName("John Loh & Jean Tan")); // names with ampersand
        assertTrue(WeddingName.isValidWeddingName("Adam & Eve")); // common names
    }

    @Test
    public void equals() {
        WeddingName name = new WeddingName("John Loh & Jean Tan");

        // same values -> returns true
        assertTrue(name.equals(new WeddingName("John Loh & Jean Tan")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new WeddingName("Adam & Eve")));
    }
}

