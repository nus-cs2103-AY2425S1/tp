package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModuleRolePairTest {

    private static final ModuleCode DEFAULT_MODULE_CODE = new ModuleCode("CS1101S");
    private static final RoleType DEFAULT_ROLE_TYPE = RoleType.STUDENT;

    @Test
    public void constructor_nullModuleCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleRolePair(null, DEFAULT_ROLE_TYPE));
    }

    @Test
    public void constructor_nullRoleType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleRolePair(DEFAULT_MODULE_CODE, null));
    }

    @Test
    public void equals() {
        ModuleRolePair pair = new ModuleRolePair(DEFAULT_MODULE_CODE, DEFAULT_ROLE_TYPE);

        // same values -> returns true
        assertTrue(pair.equals(new ModuleRolePair(DEFAULT_MODULE_CODE, DEFAULT_ROLE_TYPE)));

        // same object -> returns true
        assertTrue(pair.equals(pair));

        // null -> returns false
        assertFalse(pair.equals(null));

        // different types -> returns false
        assertFalse(pair.equals(5.0f));

        // different module codes -> returns false
        assertFalse(pair.equals(new ModuleRolePair(new ModuleCode("MA1521"), DEFAULT_ROLE_TYPE)));

        // different role types -> returns false
        assertFalse(pair.equals(new ModuleRolePair(DEFAULT_MODULE_CODE, RoleType.TUTOR)));
    }
}
