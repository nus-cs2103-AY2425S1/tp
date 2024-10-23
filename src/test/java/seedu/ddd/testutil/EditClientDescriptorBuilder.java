package seedu.ddd.testutil;

import seedu.ddd.logic.commands.EditCommand.EditClientDescriptor;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.client.Date;

/**
 * A utility class to help with building EditClientDescriptor objects.
 */
public class EditClientDescriptorBuilder extends EditContactDescriptorBuilder {

    /**
     * Sets the descriptor to an {@code EditClientDescritor}.
     */
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
        EditClientDescriptor editClientDescriptor = new EditClientDescriptor();
        editClientDescriptor.setName(client.getName());
        editClientDescriptor.setPhone(client.getPhone());
        editClientDescriptor.setEmail(client.getEmail());
        editClientDescriptor.setAddress(client.getAddress());
        editClientDescriptor.setDate(client.getDate());
        editClientDescriptor.setTags(client.getTags());
        descriptor = editClientDescriptor;
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
        EditClientDescriptor editClientDescriptor = (EditClientDescriptor) descriptor;
        editClientDescriptor.setDate(new Date(date));
        return this;
    }

    @Override
    public EditClientDescriptorBuilder withTags(String... tags) {
        return (EditClientDescriptorBuilder) super.withTags(tags);
    }

    @Override
    public EditClientDescriptorBuilder withId(String id) {
        return (EditClientDescriptorBuilder) super.withId(id);
    }

    @Override
    public EditClientDescriptor build() {
        return (EditClientDescriptor) super.build();
    }

}
