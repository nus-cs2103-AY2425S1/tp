package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class ModuleRoleMapTest {
    @Test
    public void defaultConstructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new ModuleRoleMap(null, null);
        });
    }

    @Test
    public void defaultConstructor_nullRoleTypes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new ModuleRoleMap(new ModuleCode[]{new ModuleCode("CS1101S")},
                    new RoleType[]{null});
        });
    }
    @Test
    public void defaultConstructor_nullModuleCodes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new ModuleRoleMap(new ModuleCode[]{null},
                    new RoleType[]{RoleType.TUTOR});
        });
    }

    @Test
    public void defaultConstructor_inputArraysWithDifferentLength_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ModuleRoleMap(new ModuleCode[]{new ModuleCode("CS1101S")},
                    new RoleType[]{RoleType.TUTOR, RoleType.STUDENT});
        });
    }

    @Test
    public void alternateConstructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new ModuleRoleMap(null);
        });
    }

    @Test
    public void areValidRoleTypeKeywords() {
        assertThrows(NullPointerException.class, () -> {
            RoleType.isValidRoleTypeKeyword(null);
        });

        assertTrue(RoleType.isValidRoleTypeKeyword(""));
        assertTrue(RoleType.isValidRoleTypeKeyword("student"));
        assertTrue(RoleType.isValidRoleTypeKeyword("tutor"));
        assertTrue(RoleType.isValidRoleTypeKeyword("ta"));
        assertTrue(RoleType.isValidRoleTypeKeyword("professor"));
        assertTrue(RoleType.isValidRoleTypeKeyword("prof"));
        assertTrue(RoleType.isValidRoleTypeKeyword("Student"));
        assertTrue(RoleType.isValidRoleTypeKeyword("TA"));
        assertTrue(RoleType.isValidRoleTypeKeyword("Tutor"));
        assertTrue(RoleType.isValidRoleTypeKeyword("Professor"));
        assertTrue(RoleType.isValidRoleTypeKeyword("Prof"));
        assertTrue(RoleType.isValidRoleTypeKeyword("StUDent"));
        assertTrue(RoleType.isValidRoleTypeKeyword("ProF"));
        assertTrue(RoleType.isValidRoleTypeKeyword("TuTOR"));
        assertTrue(RoleType.isValidRoleTypeKeyword("Ta"));
        assertTrue(RoleType.isValidRoleTypeKeyword("ProFESsor"));

        assertFalse(RoleType.isValidRoleTypeKeyword("Hello"));
        assertFalse(RoleType.isValidRoleTypeKeyword("Teacher"));
        assertFalse(RoleType.isValidRoleTypeKeyword("&(!&$!"));
        assertFalse(RoleType.isValidRoleTypeKeyword("dafafdas"));
    }

    @Test
    public void getFilteredModuleCodes_null_throwsNullPointerException() {
        LinkedHashMap<ModuleCode, RoleType> roles = new LinkedHashMap<>();
        roles.put(new ModuleCode("CS1101S"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(roles);
        assertThrows(NullPointerException.class, () -> {
            moduleRoleMap.getFilteredModuleCodes(null);
        });
    }

    @Test
    public void getFilteredModuleCodes_validRoleTypeAndOneMatch() {
        LinkedHashMap<ModuleCode, RoleType> roles = new LinkedHashMap<>();
        roles.put(new ModuleCode("CS1101S"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(roles);
        assertArrayEquals(Stream.of(new ModuleCode("CS1101S")).toArray(ModuleCode[]::new),
                moduleRoleMap.getFilteredModuleCodes(RoleType.TUTOR).toArray(ModuleCode[]::new));
    }

    @Test
    public void getFilteredModuleCodes_validRoleTypeAndMultipleMatches() {
        LinkedHashMap<ModuleCode, RoleType> roles = new LinkedHashMap<>();
        roles.put(new ModuleCode("CS1101S"), RoleType.TUTOR);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        roles.put(new ModuleCode("CS3230"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(roles);
        assertArrayEquals(Stream.of(new ModuleCode("CS1101S"),
                        new ModuleCode("MA1522"),
                        new ModuleCode("CS3230")).toArray(ModuleCode[]::new),
                moduleRoleMap.getFilteredModuleCodes(RoleType.TUTOR).toArray(ModuleCode[]::new));
    }

    @Test
    public void getFilteredModuleCodes_validRoleTypeButNoMatches_emptyStream() {
        LinkedHashMap<ModuleCode, RoleType> roles = new LinkedHashMap<>();
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
        LinkedHashMap<ModuleCode, RoleType> roles = new LinkedHashMap<>();
        roles.put(new ModuleCode("CS3241"), RoleType.STUDENT);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(roles);
        assertThrows(NullPointerException.class, () -> {
            moduleRoleMap.getRoleDescription(null);
        });
    }

    @Test
    public void getRoleDescription_studentRole() {
        LinkedHashMap<ModuleCode, RoleType> roles = new LinkedHashMap<>();
        roles.put(new ModuleCode("CS3241"), RoleType.STUDENT);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(roles);
        assertEquals("Student of: CS3241",
                moduleRoleMap.getRoleDescription(RoleType.STUDENT));
    }

    @Test
    public void getRoleDescription_tutorRole() {
        LinkedHashMap<ModuleCode, RoleType> roles = new LinkedHashMap<>();
        roles.put(new ModuleCode("CS3241"), RoleType.STUDENT);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(roles);
        assertEquals("Tutor of: MA1522",
                moduleRoleMap.getRoleDescription(RoleType.TUTOR));
    }

    @Test
    public void getRoleDescription_professorRole() {
        LinkedHashMap<ModuleCode, RoleType> roles = new LinkedHashMap<>();
        roles.put(new ModuleCode("CS3241"), RoleType.STUDENT);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        roles.put(new ModuleCode("CS2106"), RoleType.PROFESSOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(roles);
        assertEquals("Professor of: CS2106",
                moduleRoleMap.getRoleDescription(RoleType.PROFESSOR));
    }

    @Test
    public void getRoleDescription_noMatchingRoles_emptyString() {
        LinkedHashMap<ModuleCode, RoleType> roles = new LinkedHashMap<>();
        roles.put(new ModuleCode("CS3241"), RoleType.STUDENT);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(roles);
        assertEquals("",
                moduleRoleMap.getRoleDescription(RoleType.PROFESSOR));
    }

    @Test
    public void toString_singleModuleAndSingleRoleType() {
        LinkedHashMap<ModuleCode, RoleType> newRoles = new LinkedHashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(newRoles);
        assertEquals("Student of: CS1101S\n", moduleRoleMap.toString());
    }

    @Test
    public void toString_singleModuleAndMultipleRoleTypes() {
        LinkedHashMap<ModuleCode, RoleType> newRoles = new LinkedHashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(newRoles);
        assertEquals("Student of: CS1101S\nTutor of: MA1522\n", moduleRoleMap.toString());
    }

    @Test
    public void toString_singleModuleAndAllRoleTypes() {
        LinkedHashMap<ModuleCode, RoleType> newRoles = new LinkedHashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        newRoles.put(new ModuleCode("CS3241"), RoleType.PROFESSOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(newRoles);
        assertEquals("Student of: CS1101S\nTutor of: MA1522\nProfessor of: CS3241\n", moduleRoleMap.toString());
    }

    @Test
    public void toString_multipleModulesAndSingleRoleType() {
        LinkedHashMap<ModuleCode, RoleType> newRoles = new LinkedHashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("MA1522"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("CS3241"), RoleType.STUDENT);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(newRoles);
        assertEquals("Student of: CS1101S, MA1522, CS3241\n", moduleRoleMap.toString());
    }

    @Test
    public void toString_multipleModulesAndMultipleRoleTypes() {
        LinkedHashMap<ModuleCode, RoleType> newRoles = new LinkedHashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("MA1522"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("CS3241"), RoleType.TUTOR);
        newRoles.put(new ModuleCode("CS2105"), RoleType.TUTOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(newRoles);
        assertEquals("Student of: CS1101S, MA1522\nTutor of: CS3241, CS2105\n", moduleRoleMap.toString());
    }

    @Test
    public void toString_multipleModulesAndAllRoleTypes() {
        LinkedHashMap<ModuleCode, RoleType> newRoles = new LinkedHashMap<>();
        newRoles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("MA1522"), RoleType.STUDENT);
        newRoles.put(new ModuleCode("CS3241"), RoleType.TUTOR);
        newRoles.put(new ModuleCode("CS2105"), RoleType.TUTOR);
        newRoles.put(new ModuleCode("CS2106"), RoleType.PROFESSOR);
        newRoles.put(new ModuleCode("CS2107"), RoleType.PROFESSOR);
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(newRoles);
        assertEquals(
                "Student of: CS1101S, MA1522\nTutor of: CS3241, CS2105\nProfessor of: CS2106, CS2107\n",
                moduleRoleMap.toString());
    }

    @Test
    public void equals() {
        LinkedHashMap<ModuleCode, RoleType> roles = new LinkedHashMap<>();
        roles.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        roles.put(new ModuleCode("MA1522"), RoleType.TUTOR);
        roles.put(new ModuleCode("CS3241"), RoleType.PROFESSOR);
        ModuleRoleMap moduleRoleMap1 = new ModuleRoleMap(roles);
        ModuleRoleMap moduleRoleMap2 = new ModuleRoleMap(
                new ModuleCode[] { new ModuleCode("CS1101S"), new ModuleCode("MA1522"), new ModuleCode("CS3241")},
                new RoleType[] {RoleType.STUDENT, RoleType.TUTOR, RoleType.PROFESSOR});
        ModuleRoleMap moduleRoleMap3 = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("MA1521")}, new RoleType[]{RoleType.PROFESSOR});
        ModuleRoleMap moduleRoleMap4 = new ModuleRoleMap(
                new ModuleCode[] {new ModuleCode("MA1522"), new ModuleCode("CS3241"), new ModuleCode("CS1101S")},
                new RoleType[] {RoleType.TUTOR, RoleType.PROFESSOR, RoleType.STUDENT});
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
