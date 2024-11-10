package seedu.address.model.contact;

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
    public void constructor_invalidRoleName_throwsIllegalArgumentException() {
        String invalidRoleName = "friends";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRoleName));
    }

    @Test
    public void isValidRoleName() {
        // null role
        assertThrows(NullPointerException.class, () -> Role.isValidRoleName(null));

        // invalid role
        assertFalse(Role.isValidRoleName("friends"));
        assertFalse(Role.isValidRoleName(" "));

        // valid role
        assertTrue(Role.isValidRoleName("President"));
        assertTrue(Role.isValidRoleName("Vice President"));
        assertTrue(Role.isValidRoleName("Admin"));
        assertTrue(Role.isValidRoleName("Marketing"));
        assertTrue(Role.isValidRoleName("Events (Internal)  "));
        assertTrue(Role.isValidRoleName("Events (External)"));
        assertTrue(Role.isValidRoleName("External Relations"));
        assertTrue(Role.isValidRoleName("admin"));
        assertTrue(Role.isValidRoleName("  marketing"));
        assertTrue(Role.isValidRoleName("Events (internal)"));
        assertTrue(Role.isValidRoleName("Events (external)"));
    }

    @Test
    public void equals() {
        Role president = new Role(Role.PRESIDENT);
        // same object
        assertTrue(president.equals(president));

        // same role
        assertTrue(new Role(Role.PRESIDENT).equals(new Role(Role.PRESIDENT)));

        // not even a role class
        assertFalse(new Role(Role.PRESIDENT).equals(null));
    }

}
