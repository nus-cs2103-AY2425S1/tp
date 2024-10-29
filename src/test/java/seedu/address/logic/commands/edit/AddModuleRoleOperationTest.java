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

public class AddModuleRoleOperationTest {
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
        AddModuleRoleOperation addModuleRoleOperation = new AddModuleRoleOperation(DEFAULT_MODULE_ROLE_MAP);
        AddModuleRoleOperation addModuleRoleOperationCopy = new AddModuleRoleOperation(DEFAULT_MODULE_ROLE_MAP);
        assertEquals(addModuleRoleOperation, addModuleRoleOperationCopy);
        // same object -> returns true
        assertEquals(addModuleRoleOperation, addModuleRoleOperation);
        // null -> returns false
        assertNotEquals(addModuleRoleOperation, null);
        // different types -> returns false
        assertNotEquals(addModuleRoleOperation, 0);
        // different values -> returns false
        AddModuleRoleOperation differentAddModuleRoleOperation = new AddModuleRoleOperation(DEFAULT_MODULE_ROLE_MAP_2);
        assertNotEquals(addModuleRoleOperation, differentAddModuleRoleOperation);
    }

    @Test
    public void execute_addsModuleRolePairs_success() {
        // Add single module role pair
        ModuleRoleMap initialMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S")},
                new RoleType[]{RoleType.STUDENT});
        ModuleRoleMap mapToAdd = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS2103T")},
                new RoleType[]{RoleType.TUTOR});

        ModuleRoleMap expectedMap = DEFAULT_MODULE_ROLE_MAP;

        try {
            assertEquals(expectedMap, new AddModuleRoleOperation(mapToAdd).execute(initialMap));
        } catch (CommandException e) {
            fail("CommandException should not be thrown.");
        }

        // Add multiple module role pairs
        initialMap = new ModuleRoleMap(new ModuleCode[]{}, new RoleType[]{});
        mapToAdd = new ModuleRoleMap(DEFAULT_MODULE_CODES, DEFAULT_ROLE_TYPES);

        try {
            assertEquals(expectedMap, new AddModuleRoleOperation(mapToAdd).execute(initialMap));
        } catch (CommandException e) {
            fail("CommandException should not be thrown.");
        }
    }

    @Test
    public void execute_addsModuleRolePairs_failure() {
        // Add module role pair already exists -> throws CommandException
        ModuleRoleMap initialMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.STUDENT});
        final ModuleRoleMap mapToAdd1 = initialMap;

        assertThrows(CommandException.class, () -> new AddModuleRoleOperation(mapToAdd1).execute(initialMap));

        // Add module role pair having same module code but different role as pre-existing one -> returns same map
        final ModuleRoleMap mapToAdd2 = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.TUTOR});

        assertThrows(CommandException.class, () -> new AddModuleRoleOperation(mapToAdd2).execute(initialMap));
    }

    @Test
    public void execute_addsModuleRolePairs_partialFailure() {
        // Add multiple module role pairs, some already exist -> throws CommandException
        ModuleRoleMap initialMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.STUDENT});
        ModuleRoleMap mapToAdd = new ModuleRoleMap(DEFAULT_MODULE_CODES, DEFAULT_ROLE_TYPES);

        assertThrows(CommandException.class, () -> new AddModuleRoleOperation(mapToAdd).execute(initialMap));
    }

    @Test
    public void toString_validModuleRoleMap_returnsCorrectString() {
        ModuleRoleMap moduleRoleMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.STUDENT});
        AddModuleRoleOperation addModuleRoleOperation = new AddModuleRoleOperation(moduleRoleMap);

        String expectedString = "+ Student of: CS1101S\n";
        assertEquals(expectedString, addModuleRoleOperation.toString());
    }
}
