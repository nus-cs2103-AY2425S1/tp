package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * A utility class to help with building {@code EditPersonDescriptor} objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    /**
     * Creates an empty {@code EditPersonDescriptorBuilder}.
     */
    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    /**
     * Initializes the {@code EditPersonDescriptorBuilder} with an existing {@code EditPersonDescriptor}.
     *
     * @param descriptor The descriptor to copy.
     */
    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Initializes the {@code EditPersonDescriptorBuilder} with the data of an existing {@code Person}.
     *
     * @param person The person whose details are to be copied.
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} being built.
     *
     * @param name The name to set.
     * @return The updated {@code EditPersonDescriptorBuilder} instance.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} being built.
     *
     * @param phone The phone number to set.
     * @return The updated {@code EditPersonDescriptorBuilder} instance.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} being built.
     *
     * @param email The email to set.
     * @return The updated {@code EditPersonDescriptorBuilder} instance.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} being built.
     *
     * @param address The address to set.
     * @return The updated {@code EditPersonDescriptorBuilder} instance.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Builds and returns an {@code EditPersonDescriptor} with the specified fields.
     *
     * @return The built {@code EditPersonDescriptor}.
     */
    public EditPersonDescriptor build() {
        return descriptor;
    }
}
