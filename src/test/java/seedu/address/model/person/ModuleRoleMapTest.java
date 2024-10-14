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

public class RoleTest {
    @Test
    public void defaultConstructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new ModuleRoleMap(null, null);
        });
    }

    @Test
    public void defaultConstructor_invalidRoleTypes_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ModuleRoleMap(new ModuleCode[]{new ModuleCode("CS1101S")},
                    new String[]{"Hello"});
        });
    }

    @Test
    public void defaultConstructor_inputArraysWithDifferentLength_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ModuleRoleMap(new ModuleCode[]{new ModuleCode("CS1101S")},
                    new String[]{"Hello", "Goodbye"});
        });
    }

    @Test
    public void alternateConstructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new ModuleRoleMap(null);
        });
    }

    @Test
    public void areValidRoleTypes() {
        assertThrows(NullPointerException.class, () -> {
            ModuleRoleMap.areValidRoleTypes(null);
        });

        assertTrue(ModuleRoleMap.areValidRoleTypes(new String[]{""}));
        assertTrue(ModuleRoleMap.areValidRoleTypes(new String[]{"student"}));
        assertTrue(ModuleRoleMap.areValidRoleTypes(new String[]{"tutor"}));
        assertTrue(ModuleRoleMap.areValidRoleTypes(new String[]{"ta"}));
        assertTrue(ModuleRoleMap.areValidRoleTypes(new String[]{"professor"}));
        assertTrue(ModuleRoleMap.areValidRoleTypes(new String[]{"prof"}));
        assertTrue(ModuleRoleMap.areValidRoleTypes(new String[]{"", "prof", "ta"}));

        assertFalse(ModuleRoleMap.areValidRoleTypes(new String[]{"Hello"}));
        assertFalse(ModuleRoleMap.areValidRoleTypes(new String[]{"Teacher"}));
        assertFalse(ModuleRoleMap.areValidRoleTypes(new String[]{"&(!&$!"}));
        assertFalse(ModuleRoleMap.areValidRoleTypes(new String[]{"ta", "daffd"}));
    }

    @Test
    public void roleTypeStringToEnum() {
        assertThrows(NullPointerException.class, () -> {
            ModuleRoleMap.roleTypeStringToEnum(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            ModuleRoleMap.roleTypeStringToEnum("adfa");
        });
        assertEquals(RoleType.STUDENT, ModuleRoleMap.roleTypeStringToEnum(""));
        assertEquals(RoleType.STUDENT, ModuleRoleMap.roleTypeStringToEnum("student"));
        assertEquals(RoleType.TUTOR, ModuleRoleMap.roleTypeStringToEnum("tutor"));
        assertEquals(RoleType.TUTOR, ModuleRoleMap.roleTypeStringToEnum("ta"));
        assertEquals(RoleType.PROFESSOR, ModuleRoleMap.roleTypeStringToEnum("professor"));
        assertEquals(RoleType.PROFESSOR, ModuleRoleMap.roleTypeStringToEnum("prof"));
    }

    @Test
    public void getFilteredModuleCodes_null_throwsNullPointerException() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS1101S"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(roles);
        assertThrows(NullPointerException.class, () -> {
            moduleRoleMap.getFilteredModuleCodes(null);
        });
    }

    @Test
    public void getFilteredModuleCodes_validRoleTypeAndOneMatch() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS1101S"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(roles);
        assertArrayEquals(Stream.of(new ModuleCode("CS1101S")).toArray(ModuleCode[]::new),
                moduleRoleMap.getFilteredModuleCodes(RoleType.TUTOR).toArray(ModuleCode[]::new));
    }

    @Test
    public void getFilteredModuleCodes_validRoleTypeAndMultipleMatches() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS1101S"), RoleType.TUTOR);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        roles.put(new ModuleCode("CS3230"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(roles);
        assertArrayEquals(Stream.of(new ModuleCode("MA1522"),
                        new ModuleCode("CS3230"),
                        new ModuleCode("CS1101S")).toArray(ModuleCode[]::new),
                moduleRoleMap.getFilteredModuleCodes(RoleType.TUTOR).toArray(ModuleCode[]::new));
    }

    @Test
    public void getFilteredModuleCodes_validRoleTypeButNoMatches_emptyStream() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS1101S"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(roles);
        assertTrue(moduleRoleMap.getFilteredModuleCodes(RoleType.STUDENT).findAny().isEmpty());
    }

    @Test
    public void getModuleCodeString_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            ModuleRoleMap.getModuleCodeString(null);
        });
    }

    @Test
    public void getModuleCodeString_emptyStream_emptyString() {
        assertTrue(ModuleRoleMap.getModuleCodeString(Stream.of()).isEmpty());
    }

    @Test
    public void getModuleCodeString_streamWithOneModuleCode() {
        Stream<ModuleCode> moduleCodes = Stream.of(new ModuleCode("CS1101S"));
        assertEquals("CS1101S", ModuleRoleMap.getModuleCodeString(moduleCodes));
    }

    @Test
    public void getModuleCodeString_streamWithMultipleModuleCodes() {
        Stream<ModuleCode> moduleCodes = Stream.of(new ModuleCode("CS1101S"),
                new ModuleCode("MA1522"), new ModuleCode("CS3230"), new ModuleCode("IS1108"));
        assertEquals("CS1101S, MA1522, CS3230, IS1108", ModuleRoleMap.getModuleCodeString(moduleCodes));
    }

    @Test
    public void getRoleDescription_null_throwsNullPointerException() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS3241"), RoleType.STUDENT);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(roles);
        assertThrows(NullPointerException.class, () -> {
            moduleRoleMap.getRoleDescription(null);
        });
    }

    @Test
    public void getRoleDescription_studentRole() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS3241"), RoleType.STUDENT);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(roles);
        assertEquals("Student of: CS3241",
                moduleRoleMap.getRoleDescription(RoleType.STUDENT));
    }

    @Test
    public void getRoleDescription_tutorRole() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS3241"), RoleType.STUDENT);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(roles);
        assertEquals("Tutor of: MA1522",
                moduleRoleMap.getRoleDescription(RoleType.TUTOR));
    }

    @Test
    public void getRoleDescription_professorRole() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS3241"), RoleType.STUDENT);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        roles.put(new ModuleCode("CS2106"), RoleType.PROFESSOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(roles);
        assertEquals("Professor of: CS2106",
                moduleRoleMap.getRoleDescription(RoleType.PROFESSOR));
    }

    @Test
    public void getRoleDescription_noMatchingRoles_emptyString() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS3241"), RoleType.STUDENT);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(roles);
        assertEquals("",
                moduleRoleMap.getRoleDescription(RoleType.PROFESSOR));
    }

    @Test
    public void toString_singleModuleAndSingleRoleType() {
        HashMap<ModuleCode, RoleType> newRoles = new HashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(newRoles);
        assertEquals("Student of: CS1101S\n", moduleRoleMap.toString());
    }

    @Test
    public void toString_singleModuleAndMultipleRoleTypes() {
        HashMap<ModuleCode, RoleType> newRoles = new HashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(newRoles);
        assertEquals("Student of: CS1101S\nTutor of: MA1522\n", moduleRoleMap.toString());
    }

    @Test
    public void toString_singleModuleAndAllRoleTypes() {
        HashMap<ModuleCode, RoleType> newRoles = new HashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        newRoles.put(new ModuleCode("CS3241"), RoleType.PROFESSOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(newRoles);
        assertEquals("Student of: CS1101S\nTutor of: MA1522\nProfessor of: CS3241\n", moduleRoleMap.toString());
    }

    @Test
    public void toString_multipleModulesAndSingleRoleType() {
        HashMap<ModuleCode, RoleType> newRoles = new HashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("MA1522"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("CS3241"), RoleType.STUDENT);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(newRoles);
        assertEquals("Student of: MA1522, CS3241, CS1101S\n", moduleRoleMap.toString());
    }

    @Test
    public void toString_multipleModulesAndMultipleRoleTypes() {
        HashMap<ModuleCode, RoleType> newRoles = new HashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("MA1522"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("CS3241"), RoleType.TUTOR);
        newRoles.put(new ModuleCode("CS2105"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(newRoles);
        assertEquals("Student of: MA1522, CS1101S\nTutor of: CS2105, CS3241\n", moduleRoleMap.toString());
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
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(newRoles);
        assertEquals(
                "Student of: MA1522, CS1101S\nTutor of: CS3241, CS2105\nProfessor of: CS2106, CS2107\n",
                moduleRoleMap.toString());
    }

    @Test
    public void equals() {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>();
        roles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        roles.put(new ModuleCode("CS3241"), RoleType.PROFESSOR);
        ModuleRoleMap moduleRoleMap1 = new ModuleRoleMap(roles);
        ModuleRoleMap moduleRoleMap2 = new ModuleRoleMap(
                new ModuleCode[] { new ModuleCode("CS1101S"), new ModuleCode("MA1522"), new ModuleCode("CS3241")},
                new String[] {"", "ta", "prof"});
        ModuleRoleMap moduleRoleMap3 = new ModuleRoleMap(new ModuleCode[]{new ModuleCode("MA1521")}, new String[]{"ta"});
        ModuleRoleMap moduleRoleMap4 = new ModuleRoleMap(
                new ModuleCode[] {new ModuleCode("MA1522"), new ModuleCode("CS3241"), new ModuleCode("CS1101S")},
                new String[] {"ta", "prof", ""});
        // same objects -> returns true
        assertEquals(moduleRoleMap1, moduleRoleMap1);
        assertEquals(moduleRoleMap2, moduleRoleMap2);

        // null -> returns false
        assertNotEquals(null, moduleRoleMap1);
        assertNotEquals(null, moduleRoleMap2);

        // different types -> returns false
        assertNotEquals(5.0f, moduleRoleMap1);
        assertNotEquals(5.0f, moduleRoleMap2);

        // different objects but same roles -> returns true
        assertEquals(moduleRoleMap1, moduleRoleMap2);

        // different objects and different values -> returns false
        assertNotEquals(moduleRoleMap1, moduleRoleMap3);
        assertNotEquals(moduleRoleMap2, moduleRoleMap3);

        // different objects but same roles in different order -> returns true
        assertEquals(moduleRoleMap1, moduleRoleMap4);
        assertEquals(moduleRoleMap2, moduleRoleMap4);
    }
}
