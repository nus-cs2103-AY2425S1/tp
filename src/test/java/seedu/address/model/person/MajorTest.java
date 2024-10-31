package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MajorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Major.makeMajor(null));
    }

    @Test
    public void isValidMajor() {
        // null address
        assertThrows(NullPointerException.class, () -> Major.isValidMajor(null));

        // invalid addresses
        assertFalse(Major.isValidMajor("")); // empty string
        assertFalse(Major.isValidMajor(" ")); // spaces only

        // valid addresses
        assertTrue(Major.isValidMajor("Blk 456, Den Road, #01-355"));
        assertTrue(Major.isValidMajor("-")); // one character
        assertTrue(Major.isValidMajor("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

    @Test
    public void equals() {
        Major address = Major.makeMajor("Valid Address");

        // same values -> returns true
        assertTrue(address.equals(Major.makeMajor("Valid Address")));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(Major.makeMajor("Other Valid Address")));
    }
}
