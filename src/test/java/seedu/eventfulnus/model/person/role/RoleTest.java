package seedu.eventfulnus.model.person.role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventfulnus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidRoleName_throwsIllegalArgumentException() {
        String invalidRoleName = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRoleName));
    }

    @Test
    public void isValidRoleName() {
        // null role name
        assertThrows(NullPointerException.class, () -> Role.isValidRoleName(null));
    }

    @Test
    public void isValidRoleName_validRoleName_returnsTrue() {
        assertTrue(Role.isValidRoleName("Manager"));
    }

    @Test
    public void isValidRoleName_roleNameWithSpaces_returnsTrue() {
        assertTrue(Role.isValidRoleName("Project Manager"));
    }

    @Test
    public void isValidRoleName_roleNameWithPunctuation_returnsTrue() {
        assertTrue(Role.isValidRoleName("Manager, Sales"));
    }

    @Test
    public void isValidRoleName_roleNameWithNumbers_returnsTrue() {
        assertTrue(Role.isValidRoleName("Manager123"));
    }

    @Test
    public void isValidRoleName_emptyRoleName_returnsFalse() {
        assertFalse(Role.isValidRoleName(""));
    }

    @Test
    public void isValidRoleName_roleNameWithSpecialCharacters_returnsFalse() {
        assertFalse(Role.isValidRoleName("Manager@Sales"));
    }

    @Test
    public void equals_sameRoleObject_returnsTrue() {
        Role role = new Role("Manager");
        assertEquals(role, role);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Role role = new Role("Manager");
        assertNotEquals(role, 1);
    }

    @Test
    public void equals_differentRoleName_returnsFalse() {
        Role role = new Role("Manager");
        assertNotEquals(role, new Role("Developer"));
    }

    @Test
    public void hashCode_sameRoleName_returnsSameHashCode() {
        Role role = new Role("Manager");
        assertEquals(role.hashCode(), new Role("Manager").hashCode());
    }

    @Test
    public void hashCode_differentRoleName_returnsDifferentHashCode() {
        Role role = new Role("Manager");
        assertNotEquals(role.hashCode(), new Role("Developer").hashCode());
    }
}
