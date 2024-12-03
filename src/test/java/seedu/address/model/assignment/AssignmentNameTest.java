package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AssignmentNameTest {

    @Test
    public void constructor_validName_success() {
        // valid names
        AssignmentName name1 = new AssignmentName("Math Assignment");
        AssignmentName name2 = new AssignmentName("Assignment123");

        assertEquals("Math Assignment", name1.fullName);
        assertEquals("Assignment123", name2.fullName);
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        // invalid names
        assertThrows(IllegalArgumentException.class, () -> new AssignmentName("")); // empty string
        assertThrows(IllegalArgumentException.class, () -> new AssignmentName(" ")); // blank string
        assertThrows(IllegalArgumentException.class, () -> new AssignmentName("!@#$%")); // special characters
        assertThrows(IllegalArgumentException.class, () -> new AssignmentName("123*456"));
        // special character in string
    }

    @Test
    public void isValidName() {
        // valid names
        assertTrue(AssignmentName.isValidName("Math Assignment")); // regular name with space
        assertTrue(AssignmentName.isValidName("Assignment123")); // alphanumeric
        assertTrue(AssignmentName.isValidName("History")); // single word

        // invalid names
        assertFalse(AssignmentName.isValidName("")); // empty string
        assertFalse(AssignmentName.isValidName(" ")); // spaces only
        assertFalse(AssignmentName.isValidName("^")); // only special characters
        assertFalse(AssignmentName.isValidName("Math Assignment!")); // special characters at the end
        assertFalse(AssignmentName.isValidName("  Math")); // leading spaces
    }

    @Test
    public void testToString() {
        AssignmentName name = new AssignmentName("Math Assignment");
        assertEquals("Math Assignment", name.toString());
    }

    @Test
    public void testEquals() {
        AssignmentName name1 = new AssignmentName("Math Assignment");
        AssignmentName name2 = new AssignmentName("Math Assignment");
        AssignmentName name3 = new AssignmentName("Science Assignment");

        assertTrue(name1.equals(name2)); // same name
        assertFalse(name1.equals(name3)); // different name
        assertFalse(name1.equals(null)); // null
        assertFalse(name1.equals("Math Assignment")); // different type
    }

    @Test
    public void testHashCode() {
        AssignmentName name1 = new AssignmentName("Math Assignment");
        AssignmentName name2 = new AssignmentName("Math Assignment");

        assertEquals(name1.hashCode(), name2.hashCode()); // same string should have the same hash code
    }
}
