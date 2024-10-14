package tutorease.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class RoleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void equals() {
        Role role = new Role(Role.STUDENT);
        // same object -> returns true
        assertTrue(role.equals(role));
        // same values -> returns true
        Role roleCopy = new Role(role.value);
        assertTrue(role.equals(roleCopy));
        // different types -> returns false
        assertFalse(role.equals(1));
        // null -> returns false
        assertFalse(role.equals(null));
        // different remark -> returns false
        Role differentRole = new Role(Role.GUARDIAN);
        assertFalse(role.equals(differentRole));
    }


}
