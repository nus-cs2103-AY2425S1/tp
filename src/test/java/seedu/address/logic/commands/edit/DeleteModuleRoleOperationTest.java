package seedu.address.logic.commands.edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.edit.DeleteModuleRoleOperation.DeleteModuleRoleDescriptor;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleRoleMap;
import seedu.address.model.person.ModuleRolePair;
import seedu.address.model.person.RoleType;

public class DeleteModuleRoleOperationTest {

    private static final DeleteModuleRoleDescriptor DEFAULT_DESCRIPTOR_ROLE_SPECIFIED = new DeleteModuleRoleDescriptor(
            List.of(new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT)),
            null);
    private static final DeleteModuleRoleDescriptor DEFAULT_DESCRIPTOR_ANY_ROLE = new DeleteModuleRoleDescriptor(
            null,
            List.of(new ModuleCode("CS2103T"))
    );

    private static final DeleteModuleRoleDescriptor DEFAULT_DESCRIPTOR_COMBINED = new DeleteModuleRoleDescriptor(
            List.of(new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT)),
            List.of(new ModuleCode("CS2103T"), new ModuleCode("MA1522"))
    );

    @Test
    public void equals() {
        // same values -> returns true
        DeleteModuleRoleOperation deleteModuleRoleOperation =
                new DeleteModuleRoleOperation(DEFAULT_DESCRIPTOR_ROLE_SPECIFIED);
        DeleteModuleRoleOperation deleteModuleRoleOperationCopy =
                new DeleteModuleRoleOperation(DEFAULT_DESCRIPTOR_ROLE_SPECIFIED);
        assertEquals(deleteModuleRoleOperation, deleteModuleRoleOperationCopy);
        // same object -> returns true
        assertEquals(deleteModuleRoleOperation, deleteModuleRoleOperation);
        // null -> returns false
        assertNotEquals(deleteModuleRoleOperation, null);
        // different types -> returns false
        assertNotEquals(deleteModuleRoleOperation, 0);
        // different values -> returns false
        DeleteModuleRoleOperation differentDeleteModuleRoleOperation =
                new DeleteModuleRoleOperation(DEFAULT_DESCRIPTOR_ANY_ROLE);
        assertNotEquals(deleteModuleRoleOperation, differentDeleteModuleRoleOperation);
    }

    @Test
    public void execute_deletesModuleRolePairs_success() {
        // Delete single module role pair
        ModuleRoleMap initialMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S"), new ModuleCode("CS2103T")},
                new RoleType[]{RoleType.STUDENT, RoleType.TUTOR});
        DeleteModuleRoleDescriptor descriptor = new DeleteModuleRoleDescriptor(
                List.of(new ModuleRolePair(new ModuleCode("CS2103T"), RoleType.TUTOR)),
                null);

        ModuleRoleMap expectedMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.STUDENT});

        try {
            assertEquals(expectedMap, new DeleteModuleRoleOperation(descriptor).execute(initialMap));
        } catch (CommandException e) {
            fail("CommandException should not be thrown.");
        }

        // Delete multiple module role pairs
        initialMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S"), new ModuleCode("CS2103T"), new ModuleCode("MA1522")},
                new RoleType[]{RoleType.STUDENT, RoleType.TUTOR, RoleType.PROFESSOR}
        );

        descriptor = new DeleteModuleRoleDescriptor(
                List.of(new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT),
                        new ModuleRolePair(new ModuleCode("CS2103T"), RoleType.TUTOR)),
                null);

        expectedMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("MA1522")}, new RoleType[]{RoleType.PROFESSOR});

        try {
            assertEquals(expectedMap, new DeleteModuleRoleOperation(descriptor).execute(initialMap));
        } catch (CommandException e) {
            fail("CommandException should not be thrown.");
        }

        // delete module codes with any role
        initialMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S"), new ModuleCode("CS2103T"), new ModuleCode("MA1522")},
                new RoleType[]{RoleType.STUDENT, RoleType.TUTOR, RoleType.PROFESSOR}
        );
        descriptor = new DeleteModuleRoleDescriptor(
                null,
                List.of(new ModuleCode("CS1101S"), new ModuleCode("CS2103T"))
        );
        expectedMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("MA1522")}, new RoleType[]{RoleType.PROFESSOR});
        try {
            assertEquals(expectedMap, new DeleteModuleRoleOperation(descriptor).execute(initialMap));
        } catch (CommandException e) {
            fail("CommandException should not be thrown.");
        }

        // delete mixing any role and specific role
        initialMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S"), new ModuleCode("CS2103T"), new ModuleCode("MA1522")},
                new RoleType[]{RoleType.STUDENT, RoleType.TUTOR, RoleType.PROFESSOR}
        );
        descriptor = new DeleteModuleRoleDescriptor(
                List.of(new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT)),
                List.of(new ModuleCode("CS2103T"), new ModuleCode("MA1522"))
        );
        expectedMap = new ModuleRoleMap(
                new ModuleCode[]{}, new RoleType[]{}
        );
        try {
            assertEquals(expectedMap, new DeleteModuleRoleOperation(descriptor).execute(initialMap));
        } catch (CommandException e) {
            fail("CommandException should not be thrown.");
        }
    }

    @Test
    public void execute_deletesModuleRolePairs_failure() {
        // Delete non-existent module role pairs -> throws CommandException
        ModuleRoleMap initialMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S")}, new RoleType[]{RoleType.STUDENT});

        DeleteModuleRoleDescriptor descriptor = new DeleteModuleRoleDescriptor(
                List.of(new ModuleRolePair(new ModuleCode("CS2103T"), RoleType.TUTOR)),
                null);

        assertThrows(CommandException.class, () -> new DeleteModuleRoleOperation(descriptor).execute(initialMap));

        // Delete module role pair with same module code but different role as pre-existing one
        // -> raises CommandException
        DeleteModuleRoleDescriptor descriptor2 = new DeleteModuleRoleDescriptor(
                List.of(new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.TUTOR)),
                null);

        assertThrows(CommandException.class, () -> new DeleteModuleRoleOperation(descriptor2).execute(initialMap));

        // Delete module code ignoring role but the module code does not exist at all
        DeleteModuleRoleDescriptor descriptor3 = new DeleteModuleRoleDescriptor(
                null,
                List.of(new ModuleCode("CS2103T"))
        );
        assertThrows(CommandException.class, () -> new DeleteModuleRoleOperation(descriptor3).execute(initialMap));
    }

    @Test
    public void execute_deletesModuleRolePairs_partialFailure() {
        // Delete multiple module role pairs, some do not exist -> throws CommandException
        ModuleRoleMap initialMap = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS1101S"), new ModuleCode("CS2103T"), new ModuleCode("MA1522")},
                new RoleType[]{RoleType.STUDENT, RoleType.TUTOR, RoleType.PROFESSOR});

        DeleteModuleRoleDescriptor descriptor = new DeleteModuleRoleDescriptor(
                List.of(new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT),
                        new ModuleRolePair(new ModuleCode("CS2309"), RoleType.STUDENT)),
                List.of(new ModuleCode("CS2103T"), new ModuleCode("MA1521")));
        assertThrows(CommandException.class, () -> new DeleteModuleRoleOperation(descriptor).execute(initialMap));
    }

    @Test
    public void toString_validModuleRoleMap_returnsCorrectString() {
        DeleteModuleRoleDescriptor deleteModuleRoleDescriptor = new DeleteModuleRoleDescriptor(
                List.of(
                        new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT),
                        new ModuleRolePair(new ModuleCode("CS2103T"), RoleType.TUTOR)
                ),
                List.of(new ModuleCode("CS2103T"), new ModuleCode("MA1522"))
        );
        DeleteModuleRoleOperation deleteModuleRoleOperation = new DeleteModuleRoleOperation(deleteModuleRoleDescriptor);

        String expectedString = "-[CS1101S-Student, CS2103T-Tutor] [CS2103T, MA1522]";
        assertEquals(expectedString, deleteModuleRoleOperation.toString());
    }
}
