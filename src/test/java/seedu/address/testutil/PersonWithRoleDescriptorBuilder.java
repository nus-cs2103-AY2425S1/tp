package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.RoleCommand.PersonWithRoleDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.role.Role;

/**
 * A utility class to help with building PersonWithRoleDescriptor objects.
 */
public class PersonWithRoleDescriptorBuilder {

    private PersonWithRoleDescriptor descriptor;

    public PersonWithRoleDescriptorBuilder() {
        descriptor = new PersonWithRoleDescriptor();
    }

    public PersonWithRoleDescriptorBuilder(PersonWithRoleDescriptor descriptor) {
        this.descriptor = new PersonWithRoleDescriptor(descriptor);
    }

    /**
     * Returns an {@code PersonWithRoleDescriptor} with fields containing {@code person}'s details.
     */
    public PersonWithRoleDescriptorBuilder(Person person) {
        descriptor = new PersonWithRoleDescriptor();
        descriptor.setRole(person.getRole());
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and sets it to the {@code PersonWithRoleDescriptor}
     * that we are building.
     */
    public PersonWithRoleDescriptorBuilder withRole(String role) {
        descriptor.setRole(new Role(role));
        return this;
    }

    public PersonWithRoleDescriptor build() {
        return descriptor;
    }
}
