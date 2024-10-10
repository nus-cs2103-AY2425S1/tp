package seedu.address.model.tag;

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
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "friends";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidTagName));
    }

    @Test
    public void isValidRoleName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Role.isValidRoleName(null));

        // invalid tag name
        assertFalse(Role.isValidRoleName("friends"));
        assertFalse(Role.isValidRoleName(" "));
        assertFalse(Role.isValidRoleName("admin"));
        assertFalse(Role.isValidRoleName("marketing"));

        // valid tag name
        assertTrue(Role.isValidRoleName("President"));
        assertTrue(Role.isValidRoleName("Vice President"));
        assertTrue(Role.isValidRoleName("Admin"));
        assertTrue(Role.isValidRoleName("Marketing"));
        assertTrue(Role.isValidRoleName("Events (internal)"));
        assertTrue(Role.isValidRoleName("Events (external)"));
        assertTrue(Role.isValidRoleName("External Relations"));
    }

}
