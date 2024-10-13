package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class RoleTest {
    @Test
    public void defaultConstructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new Role(null, null);
        });
    }

    @Test
    public void defaultConstructor_invalidRoleTypes_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Role(new ModuleCode[]{new ModuleCode("CS1101S")},
                    new String[]{"Hello"});
        });
    }

    @Test
    public void alternateConstructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new Role(null);
        });
    }

    @Test
    public void areValidRoleTypes() {

    }

    @Test
    public void toStringTest() {
        HashMap<ModuleCode, RoleType> newRoles = new HashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        Role role = new Role(newRoles);
        assertEquals("Student of CS1101S\n", role.toString());
    }

    @Test
    public void toStringTest2() {
        HashMap<ModuleCode, RoleType> newRoles = new HashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        Role role = new Role(newRoles);
        assertEquals("Student of CS1101S\nTutor of MA1522\n", role.toString());
    }
}
