package seedu.address.commons.core.filename;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FilenameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Filename(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidFilename = "/";
        assertThrows(IllegalArgumentException.class, () -> new Filename(invalidFilename));
    }

    @Test
    public void isValidFilename() {
        // null name
        assertThrows(NullPointerException.class, () -> Filename.isValidFilename(null));

        // invalid name
        assertFalse(Filename.isValidFilename("/")); // forward slash
        assertFalse(Filename.isValidFilename(" \\")); // backslash
        assertFalse(Filename.isValidFilename("test?")); // question mark
        assertFalse(Filename.isValidFilename("Test *")); // asterisk

        // valid name
        assertTrue(Filename.isValidFilename("peter jack")); // alphabets only
        assertTrue(Filename.isValidFilename("12345")); // numbers only
        assertTrue(Filename.isValidFilename("peter the 2nd")); // alphanumeric characters
        assertTrue(Filename.isValidFilename("Capital Tan")); // with capital letters
        assertTrue(Filename.isValidFilename("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        Filename filename = new Filename("Valid Name");

        // same values -> returns true
        assertTrue(filename.equals(new Filename("Valid Name")));

        // same object -> returns true
        assertTrue(filename.equals(filename));

        // null -> returns false
        assertFalse(filename.equals(null));

        // different types -> returns false
        assertFalse(filename.equals(5.0f));

        // different values -> returns false
        assertFalse(filename.equals(new Filename("Other Valid Name")));
    }
}
