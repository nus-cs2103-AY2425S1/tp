package tutorease.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

    @Test
    void hashCode_sameValue_sameHashCode() {
        // Arrange: Create two Role objects with the same value
        Role role1 = new Role(Role.STUDENT);
        Role role2 = new Role(Role.STUDENT);

        // Act & Assert: Verify that their hashCodes are equal
        assertEquals(role1.hashCode(), role2.hashCode(),
                "Expected both Role objects with the same value to have the same hashCode");
    }

    @Test
    void hashCode_differentValue_differentHashCode() {
        // Arrange: Create two Role objects with different values
        Role role1 = new Role(Role.STUDENT);
        Role role2 = new Role(Role.GUARDIAN);

        // Act & Assert: Verify that their hashCodes are not equal
        assertNotEquals(role1.hashCode(), role2.hashCode(),
                "Expected Role objects with different values to have different hashCodes");
    }
}
