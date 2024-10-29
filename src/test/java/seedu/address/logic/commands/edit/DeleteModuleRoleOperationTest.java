package seedu.address.logic.commands.edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleRoleMap;
import seedu.address.model.person.RoleType;

public class DeleteModuleRoleOperationTest {
    private static final ModuleCode[] DEFAULT_MODULE_CODES =
            new ModuleCode[]{new ModuleCode("CS1101S"), new ModuleCode("CS2103T")};
    private static final ModuleCode[] DEFAULT_MODULE_CODES_2 =
            new ModuleCode[]{new ModuleCode("CS1101S"), new ModuleCode("MA1522")};
    private static final RoleType[] DEFAULT_ROLE_TYPES = new RoleType[]{RoleType.STUDENT, RoleType.TUTOR};
    private static final ModuleRoleMap DEFAULT_MODULE_ROLE_MAP = new ModuleRoleMap(
            DEFAULT_MODULE_CODES, DEFAULT_ROLE_TYPES);
    private static final ModuleRoleMap DEFAULT_MODULE_ROLE_MAP_2 = new ModuleRoleMap(
            DEFAULT_MODULE_CODES_2, DEFAULT_ROLE_TYPES);
    @Test
    public void equals() {
        // same values -> returns true
        DeleteModuleRoleOperation deleteModuleRoleOperation =
                new DeleteModuleRoleOperation(DEFAULT_MODULE_ROLE_MAP);
        DeleteModuleRoleOperation deleteModuleRoleOperationCopy =
                new DeleteModuleRoleOperation(DEFAULT_MODULE_ROLE_MAP);
        assertEquals(deleteModuleRoleOperation, deleteModuleRoleOperationCopy);
        // same object -> returns true
        assertEquals(deleteModuleRoleOperation, deleteModuleRoleOperation);
        // null -> returns false
        assertNotEquals(deleteModuleRoleOperation, null);
        // different types -> returns false
        assertNotEquals(deleteModuleRoleOperation, 0);
        // different values -> returns false
        DeleteModuleRoleOperation differentDeleteModuleRoleOperation =
                new DeleteModuleRoleOperation(DEFAULT_MODULE_ROLE_MAP_2);
        assertNotEquals(deleteModuleRoleOperation, differentDeleteModuleRoleOperation);
    }

    @Test
    public void execute_deletesModuleRolePairs_success() {
        // Delete single module role pair
        ModuleRoleMap initialMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S"), new ModuleCode("CS2103T")},
                new RoleType[]{RoleType.STUDENT, RoleType.TUTOR});
        ModuleRoleMap mapToDelete = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS2103T")}, new RoleType[]{RoleType.TUTOR});

        ModuleRoleMap expectedMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.STUDENT});

        try {
            assertEquals(expectedMap, new DeleteModuleRoleOperation(mapToDelete).execute(initialMap));
        } catch (CommandException e) {
            fail("CommandException should not be thrown.");
        }

        // Delete multiple module role pairs
        initialMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S"), new ModuleCode("CS2103T"), new ModuleCode("MA1522")},
                new RoleType[]{RoleType.STUDENT, RoleType.TUTOR, RoleType.PROFESSOR}
        );
        mapToDelete = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S"), new ModuleCode("CS2103T")},
                new RoleType[]{RoleType.STUDENT, RoleType.TUTOR}
        );

        expectedMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("MA1522")}, new RoleType[]{RoleType.PROFESSOR});

        try {
            assertEquals(expectedMap, new DeleteModuleRoleOperation(mapToDelete).execute(initialMap));
        } catch (CommandException e) {
            fail("CommandException should not be thrown.");
        }
    }

    @Test
    public void execute_deletesModuleRolePairs_failure() {
        // Delete non-existent module role pairs -> throws CommandException
        ModuleRoleMap initialMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.STUDENT});

        final ModuleRoleMap mapToDelete1 = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS2103T")}, new RoleType[]{RoleType.TUTOR});

        assertThrows(CommandException.class, () -> new DeleteModuleRoleOperation(mapToDelete1).execute(initialMap));

        // Delete module role pair having same module code but different role as pre-existing one -> returns same map
        final ModuleRoleMap mapToDelete2 = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.TUTOR});

        assertThrows(CommandException.class, () -> new DeleteModuleRoleOperation(mapToDelete2).execute(initialMap));
    }

    @Test
    public void execute_deletesModuleRolePairs_partialFailure() {
        // Delete multiple module role pairs, some do not exist -> throws CommandException
        ModuleRoleMap initialMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S"), new ModuleCode("CS2103T"), new ModuleCode("MA1522")},
                new RoleType[]{RoleType.STUDENT, RoleType.TUTOR, RoleType.PROFESSOR});

        ModuleRoleMap mapToDelete = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S"), new ModuleCode("CS2103T"), new ModuleCode("CS3230")},
                new RoleType[]{RoleType.STUDENT, RoleType.STUDENT, RoleType.PROFESSOR});

        ModuleRoleMap expectedMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS2103T"), new ModuleCode("MA1522")},
                new RoleType[]{RoleType.TUTOR, RoleType.PROFESSOR});

        assertThrows(CommandException.class, () -> new DeleteModuleRoleOperation(mapToDelete).execute(initialMap));
    }

    @Test
    public void toString_validModuleRoleMap_returnsCorrectString() {
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.STUDENT});
        DeleteModuleRoleOperation deleteModuleRoleOperation = new DeleteModuleRoleOperation(moduleRoleMap);

        String expectedString = "- Student of: CS1101S\n";
        assertEquals(expectedString, deleteModuleRoleOperation.toString());
    }
}
