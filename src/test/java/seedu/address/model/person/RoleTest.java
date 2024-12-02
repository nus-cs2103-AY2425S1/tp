package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        String invalidRole = "invalid";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void testIsValidRole() {
        // null role
        assertThrows(NullPointerException.class, () -> Role.isValidRole(null));

        // invalid roles
        assertFalse(Role.isValidRole("")); // empty string
        assertFalse(Role.isValidRole("12345")); // numbers
        assertFalse(Role.isValidRole("inval")); // incomplete role
        assertFalse(Role.isValidRole("  brUdder"));
        assertFalse(Role.isValidRole("mUdder  "));

        // valid roles
        assertTrue(Role.isValidRole("brUdder"));
        assertTrue(Role.isValidRole("mUdder"));
    }

    @Test
    public void testEquals() {
        Role role = new Role("brUdder");

        // same values -> returns true
        assertTrue(role.equals(new Role("brUdder")));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        // different types -> returns false
        assertFalse(role.equals(5.0f));

        // different values -> returns false
        assertFalse(role.equals(new Role("mUdder")));
    }

    @Test
    public void hashCode_sameRoleSameHashCode() {
        Role role1 = new Role("brUdder");
        Role role2 = new Role("brUdder");

        // same values -> same hashcode
        assertEquals(role1.hashCode(), role2.hashCode());
    }

    @Test
    public void hashCode_differentRoleDifferentHashCode() {
        Role role1 = new Role("mUdder");
        Role role2 = new Role("brUdder");

        // different values -> different hashcode
        assertNotEquals(role1.hashCode(), role2.hashCode());
    }
}
