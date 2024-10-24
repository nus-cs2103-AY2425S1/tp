package seedu.address.logic.commands.edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

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
                new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.STUDENT});
        ModuleRoleMap mapToAdd = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS2103T")}, new RoleType[]{RoleType.TUTOR});
        ModuleRoleMap expectedMap = DEFAULT_MODULE_ROLE_MAP;

        assertEquals(expectedMap, new AddModuleRoleOperation(mapToAdd).execute(initialMap));

        // Add multiple module role pairs
        initialMap = new ModuleRoleMap(new ModuleCode[]{}, new RoleType[]{});
        mapToAdd = new ModuleRoleMap(DEFAULT_MODULE_CODES, DEFAULT_ROLE_TYPES);

        assertEquals(expectedMap, new AddModuleRoleOperation(mapToAdd).execute(initialMap));
    }

    @Test
    public void execute_addsModuleRolePairs_failure() {
        // Add module role pair already exists -> returns same map
        ModuleRoleMap initialMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.STUDENT});
        ModuleRoleMap mapToAdd = initialMap;
        ModuleRoleMap expectedMap = initialMap;

        assertEquals(expectedMap, new AddModuleRoleOperation(mapToAdd).execute(initialMap));

        // Add module role pair having same module code but different role as pre-existing one -> returns same map
        mapToAdd = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.TUTOR});

        assertEquals(expectedMap, new AddModuleRoleOperation(mapToAdd).execute(initialMap));
    }

    @Test
    public void execute_addsModuleRolePairs_partialSuccess() {
        // Add multiple module role pairs, some already exist
        ModuleRoleMap initialMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.STUDENT});
        ModuleRoleMap mapToAdd = new ModuleRoleMap(DEFAULT_MODULE_CODES, DEFAULT_ROLE_TYPES);
        ModuleRoleMap expectedMap = DEFAULT_MODULE_ROLE_MAP;

        assertEquals(expectedMap, new AddModuleRoleOperation(mapToAdd).execute(initialMap));
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
