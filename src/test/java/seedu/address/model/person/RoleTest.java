package seedu.address.model.person;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidRole_throwsIllegalArgumentException() {
        String invalidRole = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));

        String invalidRole2 = "organi";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole2));

        String invalidRole3 = "stage managerr";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole3));
    }

    @Test
    public void isValidRole() {
        // null role
        assertThrows(NullPointerException.class, () -> Role.isValidRole(null));

        // invalid role
        assertFalse(Role.isValidRole("")); // empty string
        assertFalse(Role.isValidRole(" ")); // spaces only
        assertFalse(Role.isValidRole("^")); // only non-alphabetical characters
        assertFalse(Role.isValidRole("organiser*")); // contains non-alphabetical characters
        assertFalse(Role.isValidRole("stage_manager")); // contains non-alphabetical characters
        assertFalse(Role.isValidRole("fake role")); // role not in enum
        assertFalse(Role.isValidRole("stage  manager")); // contains extra space in between words
        assertFalse(Role.isValidRole(" stage manager ")); // contains extra space before and after words

        // valid role
        assertTrue(Role.isValidRole("organiser")); // lower-case
        assertTrue(Role.isValidRole("ORGANISER")); // upper-case
        assertTrue(Role.isValidRole("oRgAnIsEr")); // mixed-case
        assertTrue(Role.isValidRole("sound technician")); // two-word role
        assertTrue(Role.isValidRole("sOuND TEChniCIaN")); // mixed-case two-word role
    }

    @Test
    public void equals() {
        Role role = new Role("organiser");

        // same values -> returns true
        assertTrue(role.equals(new Role("organiser")));

        // same object -> returns true
        assertTrue(role.equals(role));

        // different case -> returns true
        assertTrue(role.equals(new Role("ORGANISER")));

        // null -> returns false
        assertFalse(role.equals(null));

        // different types -> returns false
        assertFalse(role.equals(5));

        // different values -> returns false
        assertFalse(role.equals(new Role("stage manager")));
    }
}
