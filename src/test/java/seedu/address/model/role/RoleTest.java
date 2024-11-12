package seedu.address.model.role;

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
        // null role name
        assertThrows(NullPointerException.class, () -> Role.isValidRoleName(null));
    }

}
