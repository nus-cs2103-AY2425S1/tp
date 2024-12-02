package seedu.address.logic.commands.edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.edit.AddModuleRoleOperation.AddModuleRoleDescriptor;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleRoleMap;
import seedu.address.model.person.ModuleRolePair;
import seedu.address.model.person.RoleType;

public class AddModuleRoleOperationTest {
    private static final AddModuleRoleDescriptor DEFAULT_DESCRIPTOR = new AddModuleRoleDescriptor(
            List.of(
                    new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT),
                    new ModuleRolePair(new ModuleCode("CS2103T"), RoleType.TUTOR)
            )
    );

    private static final AddModuleRoleDescriptor DEFAULT_DESCRIPTOR_2 = new AddModuleRoleDescriptor(
            List.of(
                    new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT),
                    new ModuleRolePair(new ModuleCode("MA1522"), RoleType.TUTOR)
            )
    );
    @Test
    public void equals() {
        // same descriptor -> returns true
        AddModuleRoleOperation addModuleRoleOperation = new AddModuleRoleOperation(DEFAULT_DESCRIPTOR);
        AddModuleRoleOperation addModuleRoleOperationCopy = new AddModuleRoleOperation(DEFAULT_DESCRIPTOR);
        assertEquals(addModuleRoleOperation, addModuleRoleOperationCopy);
        // same object -> returns true
        assertEquals(addModuleRoleOperation, addModuleRoleOperation);
        // null -> returns false
        assertNotEquals(addModuleRoleOperation, null);
        // different types -> returns false
        assertNotEquals(addModuleRoleOperation, 0);
        // different values -> returns false
        AddModuleRoleOperation differentAddModuleRoleOperation = new AddModuleRoleOperation(DEFAULT_DESCRIPTOR_2);
        assertNotEquals(addModuleRoleOperation, differentAddModuleRoleOperation);
    }

    @Test
    public void execute_addsModuleRolePairs_success() {
        // Add single module role pair
        ModuleRoleMap initialMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S")},
                new RoleType[]{RoleType.STUDENT});
        AddModuleRoleDescriptor descriptor = new AddModuleRoleDescriptor(
                List.of(new ModuleRolePair(new ModuleCode("CS2103T"), RoleType.TUTOR)));

        ModuleRoleMap expectedMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S"), new ModuleCode("CS2103T")},
                new RoleType[]{RoleType.STUDENT, RoleType.TUTOR});

        try {
            assertEquals(expectedMap, new AddModuleRoleOperation(descriptor).execute(initialMap));
        } catch (CommandException e) {
            fail("CommandException should not be thrown.");
        }

        // Add multiple module role pairs
        initialMap = new ModuleRoleMap(new ModuleCode[]{}, new RoleType[]{});

        try {
            assertEquals(expectedMap, new AddModuleRoleOperation(DEFAULT_DESCRIPTOR).execute(initialMap));
        } catch (CommandException e) {
            fail("CommandException should not be thrown.");
        }
    }

    @Test
    public void execute_addsModuleRolePairs_failure() {
        // Add module role pair already exists -> throws CommandException
        ModuleRoleMap initialMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.STUDENT});
        AddModuleRoleDescriptor descriptor = new AddModuleRoleDescriptor(
                List.of(new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT)));

        assertThrows(CommandException.class, () -> new AddModuleRoleOperation(descriptor).execute(initialMap));

        // Add module role pair with same module code but different role as pre-existing one -> throws CommandException
        AddModuleRoleDescriptor descriptor2 = new AddModuleRoleDescriptor(
                List.of(new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.TUTOR)));
        assertThrows(CommandException.class, () -> new AddModuleRoleOperation(descriptor2).execute(initialMap));
    }

    @Test
    public void execute_addsModuleRolePairs_partialFailure() {
        // Add multiple module role pairs, some already exist -> throws CommandException
        ModuleRoleMap initialMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.STUDENT});

        assertThrows(CommandException.class, () -> new AddModuleRoleOperation(DEFAULT_DESCRIPTOR).execute(initialMap));
    }

    @Test
    public void toString_validModuleRoleMap_returnsCorrectString() {
        AddModuleRoleDescriptor descriptor = new AddModuleRoleDescriptor(
                List.of(
                        new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT),
                        new ModuleRolePair(new ModuleCode("CS2103T"), RoleType.TUTOR))
        );
        AddModuleRoleOperation addModuleRoleOperation = new AddModuleRoleOperation(descriptor);

        String expectedString = "+[CS1101S-Student, CS2103T-Tutor]";
        assertEquals(expectedString, addModuleRoleOperation.toString());
    }
}
