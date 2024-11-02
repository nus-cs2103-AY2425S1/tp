package seedu.address.logic.commands.edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.edit.DeleteModuleRoleOperation.DeleteModuleRoleDescriptor;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleRolePair;
import seedu.address.model.person.RoleType;

public class DeleteModuleRoleDescriptorTest {
    @Test
    public void equals() {

        DeleteModuleRoleDescriptor descriptor1 = new DeleteModuleRoleDescriptor(
                List.of(
                        new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT),
                        new ModuleRolePair(new ModuleCode("CS2103T"), RoleType.TUTOR)
                ),
                null
        );
        DeleteModuleRoleDescriptor descriptor2 = new DeleteModuleRoleDescriptor(
                null,
                List.of(new ModuleCode("CS2103T"), new ModuleCode("MA1522"))
        );
        DeleteModuleRoleDescriptor descriptor3 = new DeleteModuleRoleDescriptor(
                List.of(
                        new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT),
                        new ModuleRolePair(new ModuleCode("CS2103T"), RoleType.TUTOR)
                ),
                List.of(new ModuleCode("CS2103T"), new ModuleCode("MA1522"))
        );

        DeleteModuleRoleDescriptor descriptorEmpty = new DeleteModuleRoleDescriptor(null, null);

        // same object -> returns true
        assertEquals(descriptor1, descriptor1);

        // null -> returns false
        assertNotEquals(descriptor1, null);

        // different types -> returns false
        assertNotEquals(descriptor1, 0);

        // different values -> returns false
        assertNotEquals(descriptor1, descriptor2);

        // compare with empty descriptor -> returns false
        assertNotEquals(descriptor1, descriptorEmpty);

        // differs only in order -> returns false
        DeleteModuleRoleDescriptor descriptor3Reversed = new DeleteModuleRoleDescriptor(
                List.of(
                        new ModuleRolePair(new ModuleCode("CS2103T"), RoleType.TUTOR),
                        new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT)
                ),
                List.of(new ModuleCode("MA1522"), new ModuleCode("CS2103T"))
        );
        assertNotEquals(descriptor3, descriptor3Reversed);
    }

    @Test
    public void setAndGet_multipleOperations_success() {
        DeleteModuleRoleDescriptor descriptor = new DeleteModuleRoleDescriptor(null, null);

        List<ModuleRolePair> expectedModuleRolePairList1 = List.of(
                new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT)
        );
        List<ModuleCode> expectedModuleCodeList1 = List.of(new ModuleCode("CS1101S"));
        DeleteModuleRoleDescriptor expected1 = new DeleteModuleRoleDescriptor(
                expectedModuleRolePairList1,
                expectedModuleCodeList1
        );
        descriptor.setToDeletes(List.of(new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT)));
        descriptor.setToDeleteAnyRoles(List.of(new ModuleCode("CS1101S")));
        assertEquals(expected1, descriptor);
        assertEquals(expectedModuleRolePairList1, descriptor.getToDeletes());
        assertEquals(expectedModuleCodeList1, descriptor.getToDeleteAnyRoles());
    }

    @Test
    public void copyConstructor() {
        DeleteModuleRoleDescriptor descriptor = new DeleteModuleRoleDescriptor(
                List.of(
                        new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT),
                        new ModuleRolePair(new ModuleCode("CS2103T"), RoleType.TUTOR)
                ),
                List.of(new ModuleCode("CS2103T"), new ModuleCode("MA1522"))
        );
        DeleteModuleRoleDescriptor copy = new DeleteModuleRoleDescriptor(descriptor);
        assertEquals(descriptor, copy);
    }
}
