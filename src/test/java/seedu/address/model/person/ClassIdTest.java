package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class ClassIdTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ClassId(null));
    }

    @Test
    public void constructor_invalidClassId_throwsIllegalArgumentException() {
        String invalidClassId = "-5";
        assertThrows(IllegalArgumentException.class, () -> new ClassId(invalidClassId));
    }

    @Test
    public void isValidClassId() {
        // null address
        assertThrows(NullPointerException.class, () -> ClassId.isValidClassId(null));

        // invalid addresses
        assertFalse(ClassId.isValidClassId("-1")); // negative number
        assertFalse(ClassId.isValidClassId(" ")); // spaces only

        // valid addresses
        assertTrue(ClassId.isValidClassId("5"));
        assertTrue(ClassId.isValidClassId("1")); // one character
        assertTrue(ClassId.isValidClassId("100"));
    }

    @Test
    public void equals() {
        ClassId classId = new ClassId("5");

        // same values -> returns true
        assertTrue(classId.equals(new ClassId("5")));

        // same object -> returns true
        assertTrue(classId.equals(classId));

        // null -> returns false
        assertFalse(classId.equals(null));

        // different types -> returns false
        assertFalse(classId.equals(5.0f));

        // different values -> returns false
        assertFalse(classId.equals(new Address("Other Valid Address")));
    }
}
