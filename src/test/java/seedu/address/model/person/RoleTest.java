package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

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
        assertThrows(NullPointerException.class, () -> Role.isValidRole(null));
    }

    @Test
    public void isValidRole_invalidRole_returnsFalse() {
        String invalidRole = "Admin";
        assertFalse(Role.RoleType.isValidRole(invalidRole));
    }

    @Test
    public void equals_nonRoleObject_returnsFalse() {
        Role role = new Role("Student");
        String nonRoleObject = "NotARole";

        assertFalse(role.equals(nonRoleObject));
    }

}
