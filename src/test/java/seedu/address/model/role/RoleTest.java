package seedu.address.model.role;

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
        String invalidRoleName = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRoleName));
    }

    @Test
    public void constructor_whiteSpaceRoleName_throwsIllegalArgumentException() {
        String invalidRoleName = "   ";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRoleName));
    }


    @Test
    public void constructor_moreThanOneWordRoleName_throwsIllegalArgumentException() {
        String invalidRoleName = "Best Friend";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRoleName));
    }

    @Test
    public void constructor_invalidSymbolsRoleName_throwsIllegalArgumentException() {
        String invalidRoleName = "Florist!";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRoleName));
    }

    @Test
    public void isValidRoleName() {
        // null role name
        assertThrows(NullPointerException.class, () -> Role.isValidRoleName(null));
    }

    @Test
    public void equalsOwnRoleName() {
        Role role = new Role("friend");
        assertTrue(role.equals(role));
    }

    @Test
    public void equalsSameRoleName() {
        Role role = new Role("friend");
        Role other = new Role("friend");
        assertTrue(role.equals(other));
    }

    @Test
    public void equalsUpperCaseTagName() {
        Role role = new Role("friend");
        Role other = new Role("FRIEND");
        assertTrue(role.equals(other));
    }
}
