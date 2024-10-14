package seedu.ddd.testutil;

import seedu.ddd.logic.commands.EditCommand.EditClientDescriptor;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.client.Date;

/**
 * A utility class to help with building EditClientDescriptor objects.
 */
public class EditClientDescriptorBuilder extends EditContactDescriptorBuilder {

    public EditClientDescriptorBuilder() {
        super();
        descriptor = new EditClientDescriptor();
    }

    public EditClientDescriptorBuilder(EditClientDescriptor descriptor) {
        this.descriptor = new EditClientDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditClientDescriptor} with fields containing {@code client}'s details
     */
    public EditClientDescriptorBuilder(Client client) {
        descriptor = new EditClientDescriptor();
        descriptor.setName(client.getName());
        descriptor.setPhone(client.getPhone());
        descriptor.setEmail(client.getEmail());
        descriptor.setAddress(client.getAddress());
        ((EditClientDescriptor) descriptor).setDate(client.getDate());
        descriptor.setTags(client.getTags());
        descriptor.setId(client.getId());
    }

    @Override
    public EditClientDescriptorBuilder withName(String name) {
        return (EditClientDescriptorBuilder) super.withName(name);
    }

    @Override
    public EditClientDescriptorBuilder withPhone(String phone) {
        return (EditClientDescriptorBuilder) super.withPhone(phone);
    }

    @Override
    public EditClientDescriptorBuilder withEmail(String email) {
        return (EditClientDescriptorBuilder) super.withEmail(email);
    }

    @Override
    public EditClientDescriptorBuilder withAddress(String address) {
        return (EditClientDescriptorBuilder) super.withAddress(address);
    }

    /**
     * Sets the {@code Date} of the {@code EditClientDescriptor} that we are building.
     */
    public EditClientDescriptorBuilder withDate(String date) {
        ((EditClientDescriptor) descriptor).setDate(new Date(date));
        return this;
    }

    @Override
    public EditClientDescriptorBuilder withTags(String... tags) {
        return (EditClientDescriptorBuilder) super.withTags(tags);
    }

    @Override
    public EditClientDescriptor build() {
        return (EditClientDescriptor) super.build();
    }

}
