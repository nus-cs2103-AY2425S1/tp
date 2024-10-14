package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.Module;

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
    public void defaultConstructor_inputArraysWithDifferentLength_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Role(new ModuleCode[]{new ModuleCode("CS1101S")},
                    new String[]{"Hello", "Goodbye"});
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
        assertThrows(NullPointerException.class, () -> {
            Role.areValidRoleTypes(null);
        });

        assertTrue(Role.areValidRoleTypes(new String[]{""}));
        assertTrue(Role.areValidRoleTypes(new String[]{"student"}));
        assertTrue(Role.areValidRoleTypes(new String[]{"tutor"}));
        assertTrue(Role.areValidRoleTypes(new String[]{"ta"}));
        assertTrue(Role.areValidRoleTypes(new String[]{"professor"}));
        assertTrue(Role.areValidRoleTypes(new String[]{"prof"}));
        assertTrue(Role.areValidRoleTypes(new String[]{"", "prof", "ta"}));

        assertFalse(Role.areValidRoleTypes(new String[]{"Hello"}));
        assertFalse(Role.areValidRoleTypes(new String[]{"Teacher"}));
        assertFalse(Role.areValidRoleTypes(new String[]{"&(!&$!"}));
        assertFalse(Role.areValidRoleTypes(new String[]{"ta", "daffd"}));
    }

    @Test
    public void roleTypeStringToEnum() {
        assertThrows(NullPointerException.class, () -> {
            Role.roleTypeStringToEnum(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Role.roleTypeStringToEnum("adfa");
        });
        assertEquals(RoleType.STUDENT, Role.roleTypeStringToEnum(""));
        assertEquals(RoleType.STUDENT, Role.roleTypeStringToEnum("student"));
        assertEquals(RoleType.TUTOR, Role.roleTypeStringToEnum("tutor"));
        assertEquals(RoleType.TUTOR, Role.roleTypeStringToEnum("ta"));
        assertEquals(RoleType.PROFESSOR, Role.roleTypeStringToEnum("professor"));
        assertEquals(RoleType.PROFESSOR, Role.roleTypeStringToEnum("prof"));
    }

    @Test
    public void getFilteredModuleCodes_null_throwsNullPointerException() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS1101S"), RoleType.TUTOR);
        Role role = new Role(roles);
        assertThrows(NullPointerException.class, () -> {
            role.getFilteredModuleCodes(null);
        });
    }

    @Test
    public void getFilteredModuleCodes_validRoleTypeAndOneMatch() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS1101S"), RoleType.TUTOR);
        Role role = new Role(roles);
        assertArrayEquals(Stream.of(new ModuleCode("CS1101S")).toArray(ModuleCode[]::new),
                role.getFilteredModuleCodes(RoleType.TUTOR).toArray(ModuleCode[]::new));
    }

    @Test
    public void getFilteredModuleCodes_validRoleTypeAndMultipleMatches() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS1101S"), RoleType.TUTOR);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        roles.put(new ModuleCode("CS3230"), RoleType.TUTOR);
        Role role = new Role(roles);
        assertArrayEquals(Stream.of(new ModuleCode("MA1522"),
                        new ModuleCode("CS3230"),
                        new ModuleCode("CS1101S")).toArray(ModuleCode[]::new),
                role.getFilteredModuleCodes(RoleType.TUTOR).toArray(ModuleCode[]::new));
    }

    @Test
    public void getFilteredModuleCodes_validRoleTypeButNoMatches_emptyStream() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS1101S"), RoleType.TUTOR);
        Role role = new Role(roles);
        assertTrue(role.getFilteredModuleCodes(RoleType.STUDENT).findAny().isEmpty());
    }

    @Test
    public void getModuleCodeString_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            Role.getModuleCodeString(null);
        });
    }

    @Test
    public void getModuleCodeString_emptyStream_emptyString() {
        assertTrue(Role.getModuleCodeString(Stream.of()).isEmpty());
    }

    @Test
    public void getModuleCodeString_streamWithOneModuleCode() {
        Stream<ModuleCode> moduleCodes = Stream.of(new ModuleCode("CS1101S"));
        assertEquals("CS1101S", Role.getModuleCodeString(moduleCodes));
    }

    @Test
    public void getModuleCodeString_streamWithMultipleModuleCodes() {
        Stream<ModuleCode> moduleCodes = Stream.of(new ModuleCode("CS1101S"),
                new ModuleCode("MA1522"), new ModuleCode("CS3230"), new ModuleCode("IS1108"));
        assertEquals("CS1101S, MA1522, CS3230, IS1108", Role.getModuleCodeString(moduleCodes));
    }

    @Test
    public void getRoleDescription_null_throwsNullPointerException() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS3241"), RoleType.STUDENT);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        Role role = new Role(roles);
        assertThrows(NullPointerException.class, () -> {
            role.getRoleDescription(null);
        });
    }

    @Test
    public void getRoleDescription_studentRole() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS3241"), RoleType.STUDENT);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        Role role = new Role(roles);
        assertEquals("Student of: CS3241",
                role.getRoleDescription(RoleType.STUDENT));
    }

    @Test
    public void getRoleDescription_tutorRole() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS3241"), RoleType.STUDENT);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        Role role = new Role(roles);
        assertEquals("Tutor of: MA1522",
                role.getRoleDescription(RoleType.TUTOR));
    }

    @Test
    public void getRoleDescription_professorRole() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS3241"), RoleType.STUDENT);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        roles.put(new ModuleCode("CS2106"), RoleType.PROFESSOR);
        Role role = new Role(roles);
        assertEquals("Professor of: CS2106",
                role.getRoleDescription(RoleType.PROFESSOR));
    }

    @Test
    public void getRoleDescription_noMatchingRoles_emptyString() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS3241"), RoleType.STUDENT);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        Role role = new Role(roles);
        assertEquals("",
                role.getRoleDescription(RoleType.PROFESSOR));
    }

    @Test
    public void toString_singleModuleAndSingleRoleType() {
        HashMap<ModuleCode, RoleType> newRoles = new HashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        Role role = new Role(newRoles);
        assertEquals("Student of: CS1101S\n", role.toString());
    }

    @Test
    public void toString_singleModuleAndMultipleRoleTypes() {
        HashMap<ModuleCode, RoleType> newRoles = new HashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        Role role = new Role(newRoles);
        assertEquals("Student of: CS1101S\nTutor of: MA1522\n", role.toString());
    }

    @Test
    public void toString_singleModuleAndAllRoleTypes() {
        HashMap<ModuleCode, RoleType> newRoles = new HashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        newRoles.put(new ModuleCode("CS3241"), RoleType.PROFESSOR);
        Role role = new Role(newRoles);
        assertEquals("Student of: CS1101S\nTutor of: MA1522\nProfessor of: CS3241\n", role.toString());
    }

    @Test
    public void toString_multipleModulesAndSingleRoleType() {
        HashMap<ModuleCode, RoleType> newRoles = new HashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("MA1522"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("CS3241"), RoleType.STUDENT);
        Role role = new Role(newRoles);
        assertEquals("Student of: MA1522, CS3241, CS1101S\n", role.toString());
    }

    @Test
    public void toString_multipleModulesAndMultipleRoleTypes() {
        HashMap<ModuleCode, RoleType> newRoles = new HashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("MA1522"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("CS3241"), RoleType.TUTOR);
        newRoles.put(new ModuleCode("CS2105"), RoleType.TUTOR);
        Role role = new Role(newRoles);
        assertEquals("Student of: MA1522, CS1101S\nTutor of: CS2105, CS3241\n", role.toString());
    }

    @Test
    public void toString_multipleModulesAndAllRoleTypes() {
        HashMap<ModuleCode, RoleType> newRoles = new HashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("MA1522"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("CS3241"), RoleType.TUTOR);
        newRoles.put(new ModuleCode("CS2105"), RoleType.TUTOR);
        newRoles.put(new ModuleCode("CS2106"), RoleType.PROFESSOR);
        newRoles.put(new ModuleCode("CS2107"), RoleType.PROFESSOR);
        Role role = new Role(newRoles);
        assertEquals(
                "Student of: MA1522, CS1101S\nTutor of: CS3241, CS2105\nProfessor of: CS2106, CS2107\n",
                role.toString());
    }

    @Test
    public void equals() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        roles.put(new ModuleCode("CS3241"), RoleType.PROFESSOR);
        Role role1 = new Role(roles);
        Role role2 = new Role(
                new ModuleCode[] { new ModuleCode("CS1101S"), new ModuleCode("MA1522"), new ModuleCode("CS3241")},
                new String[] {"", "ta", "prof"});
        Role role3 = new Role(new ModuleCode[]{new ModuleCode("MA1521")}, new String[]{"ta"});
        Role role4 = new Role(
                new ModuleCode[] {new ModuleCode("MA1522"), new ModuleCode("CS3241"), new ModuleCode("CS1101S")},
                new String[] {"ta", "prof", ""});
        // same objects -> returns true
        assertEquals(role1, role1);
        assertEquals(role2, role2);

        // null -> returns false
        assertNotEquals(null, role1);
        assertNotEquals(null, role2);

        // different types -> returns false
        assertNotEquals(5.0f, role1);
        assertNotEquals(5.0f, role2);

        // different objects but same roles -> returns true
        assertEquals(role1, role2);

        // different objects and different values -> returns false
        assertNotEquals(role1, role3);
        assertNotEquals(role2, role3);

        // different objects but same roles in different order -> returns true
        assertEquals(role1, role4);
        assertEquals(role2, role4);
    }
}
