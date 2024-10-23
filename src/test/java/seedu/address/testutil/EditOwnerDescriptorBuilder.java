package seedu.address.testutil;

import seedu.address.logic.commands.EditOwnerCommand.EditOwnerDescriptor;
import seedu.address.model.owner.Address;
import seedu.address.model.owner.Email;
import seedu.address.model.owner.Name;
import seedu.address.model.owner.Owner;
import seedu.address.model.owner.Phone;

/**
 * A utility class to help with building EditOwnerDescriptor objects.
 */
public class EditOwnerDescriptorBuilder {

    private EditOwnerDescriptor descriptor;

    public EditOwnerDescriptorBuilder() {
        descriptor = new EditOwnerDescriptor();
    }

    public EditOwnerDescriptorBuilder(EditOwnerDescriptor descriptor) {
        this.descriptor = new EditOwnerDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditOwnerDescriptor} with fields containing {@code owner}'s details
     */
    public EditOwnerDescriptorBuilder(Owner owner) {
        descriptor = new EditOwnerDescriptor();
        descriptor.setName(owner.getName());
        descriptor.setPhone(owner.getPhone());
        descriptor.setEmail(owner.getEmail());
        descriptor.setAddress(owner.getAddress());
    }

    /**
     * Sets the {@code Name} of the {@code EditOwnerDescriptor} that we are building.
     */
    public EditOwnerDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditOwnerDescriptor} that we are building.
     */
    public EditOwnerDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditOwnerDescriptor} that we are building.
     */
    public EditOwnerDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditOwnerDescriptor} that we are building.
     */
    public EditOwnerDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    public EditOwnerDescriptor build() {
        return descriptor;
    }
}
