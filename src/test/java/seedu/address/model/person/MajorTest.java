package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MajorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Major(null));
    }

    @Test
    public void constructor_invalidMajor_throwsIllegalArgumentException() {
        String invalidMajor = "invalid";
        assertThrows(IllegalArgumentException.class, () -> new Major(invalidMajor));
    }

    @Test
    public void testIsValidMajor() {
        // null major
        assertThrows(NullPointerException.class, () -> Major.isValidMajor(null));

        // invalid majors
        assertFalse(Major.isValidMajor("")); // empty string
        assertFalse(Major.isValidMajor("12345")); // numbers
        assertFalse(Major.isValidMajor("ce")); // incomplete major
        assertFalse(Major.isValidMajor(" cs"));
        assertFalse(Major.isValidMajor("cs   "));
        assertFalse(Major.isValidMajor("CS")); // uppercase major

        // valid majors
        assertTrue(Major.isValidMajor("cs"));
        assertTrue(Major.isValidMajor("bza"));
        assertTrue(Major.isValidMajor("ceg"));
    }

    @Test
    public void testEquals() {
        Major major = new Major("cs");

        // same values -> returns true
        assertTrue(major.equals(new Major("cs")));

        // same object -> returns true
        assertTrue(major.equals(major));

        // null -> returns false
        assertFalse(major.equals(null));

        // different types -> returns false
        assertFalse(major.equals(5.0f));

        // different values -> returns false
        assertFalse(major.equals(new Major("bza")));
    }

    @Test
    public void hashCode_sameMajorSameHashCode() {
        Major major1 = new Major("cs");
        Major major2 = new Major("cs");

        // same values -> same hashcode
        assertEquals(major1.hashCode(), major2.hashCode());
    }

    @Test
    public void hashCode_differentMajorDifferentHashCode() {
        Major major1 = new Major("cs");
        Major major2 = new Major("bza");

        // different values -> different hashcode
        assertNotEquals(major1.hashCode(), major2.hashCode());
    }
}

