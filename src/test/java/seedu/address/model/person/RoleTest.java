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
    }

    @Test
    public void isValidRole() {
        // null role
        assertThrows(NullPointerException.class, () -> Role.isValidRole(null));

        // invalid roles
        assertFalse(Role.isValidRole("asdfasdf")); // not one of "student" or "parent"
        assertFalse(Role.isValidRole(" ")); // spaces only
        assertFalse(Role.isValidRole("12345")); // numbers


        // valid roles
        assertTrue(Role.isValidRole("student"));
        assertTrue(Role.isValidRole("STUDENT"));
        assertTrue(Role.isValidRole("ParENt"));
    }

    @Test
    public void equals() {
        Role role = new Role("student");

        // same values -> returns true
        assertTrue(role.equals(new Role("student")));
        assertTrue(role.equals(new Role("STUDENT")));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        // different types -> returns false
        assertFalse(role.equals(5.0f));

        // different values -> returns false
        assertFalse(role.equals(new Role("parent")));
    }
}
