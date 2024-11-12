package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MajorTest {

    @Test
    public void factory_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Major.makeMajor(null));
    }

    @Test
    public void factory_emptyMajor_returnsSameInstance() {
        Major expectedMajor = Major.makeMajor("");
        assertSame(expectedMajor, Major.makeMajor(""));
    }


    @Test
    public void isValidMajor() {
        // null major
        assertThrows(NullPointerException.class, () -> Major.isValidMajor(null));

        // invalid majors
        assertFalse(Major.isValidMajor("")); // empty string
        assertFalse(Major.isValidMajor(" ")); // spaces only

        // valid majors
        assertTrue(Major.isValidMajor("Computer Science"));
        assertTrue(Major.isValidMajor("-")); // one character
        assertTrue(Major.isValidMajor("Information Security")); // long major
    }

    @Test
    public void equals() {
        Major major = Major.makeMajor("Valid Major");

        // same values -> returns true
        assertTrue(major.equals(Major.makeMajor("Valid Major")));

        // same object -> returns true
        assertTrue(major.equals(major));

        // null -> returns false
        assertFalse(major.equals(null));

        // different types -> returns false
        assertFalse(major.equals(5.0f));

        // different values -> returns false
        assertFalse(major.equals(Major.makeMajor("Other Valid Major")));
    }
}
