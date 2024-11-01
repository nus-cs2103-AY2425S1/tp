package seedu.address.logic.commands.edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.edit.AddModuleRoleOperation.AddModuleRoleDescriptor;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleRolePair;
import seedu.address.model.person.RoleType;

public class AddModuleRoleDescriptorTest {
    @Test
    public void equals() {

        AddModuleRoleDescriptor descriptor1 = new AddModuleRoleDescriptor(
                List.of(
                        new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT),
                        new ModuleRolePair(new ModuleCode("CS2103T"), RoleType.TUTOR)
                )
        );
        AddModuleRoleDescriptor descriptor2 = new AddModuleRoleDescriptor(
                List.of(
                        new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT),
                        new ModuleRolePair(new ModuleCode("MA1522"), RoleType.TUTOR)
                )
        );
        AddModuleRoleDescriptor descriptorEmpty = new AddModuleRoleDescriptor((List<ModuleRolePair>) null);

        // same object -> returns true
        assertEquals(descriptor1, descriptor1);

        // null -> returns false
        assertNotEquals(descriptor1, null);

        // different types -> returns false
        assertNotEquals(descriptor1, 0);

        // different values -> returns false
        assertNotEquals(descriptor1, descriptor2);

        // exactly one descriptor is empty -> returns false
        assertNotEquals(descriptor1, descriptorEmpty);

        // differs only in order -> returns false
        AddModuleRoleDescriptor descriptor1Reversed = new AddModuleRoleDescriptor(
                List.of(
                        new ModuleRolePair(new ModuleCode("CS2103T"), RoleType.TUTOR),
                        new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT)
                )
        );
        assertNotEquals(descriptor1, descriptor1Reversed);
    }

    @Test
    public void setAndGet_multipleOperations_success() {
        AddModuleRoleDescriptor descriptor = new AddModuleRoleDescriptor((List<ModuleRolePair>) null);

        List<ModuleRolePair> expectedList1 = List.of(
                new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT)
        );
        AddModuleRoleDescriptor expected1 = new AddModuleRoleDescriptor(expectedList1);
        descriptor.setToAdds(List.of(new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT)));
        assertEquals(expected1, descriptor);
        assertEquals(expectedList1, descriptor.getToAdds());

        List<ModuleRolePair> expectedList2 = List.of(
                new ModuleRolePair(new ModuleCode("CS2103T"), RoleType.TUTOR)
        );
        AddModuleRoleDescriptor expected2 = new AddModuleRoleDescriptor(expectedList2);
        descriptor.setToAdds(List.of(new ModuleRolePair(new ModuleCode("CS2103T"), RoleType.TUTOR)));
        assertEquals(expected2, descriptor);
        assertEquals(expectedList2, descriptor.getToAdds());
    }

    @Test
    public void copyConstructor() {
        AddModuleRoleDescriptor descriptor = new AddModuleRoleDescriptor(
                List.of(
                        new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT),
                        new ModuleRolePair(new ModuleCode("CS2103T"), RoleType.TUTOR)
                )
        );
        AddModuleRoleDescriptor copy = new AddModuleRoleDescriptor(descriptor);
        assertEquals(descriptor, copy);
    }
}
