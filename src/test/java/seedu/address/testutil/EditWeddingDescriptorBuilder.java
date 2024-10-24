package seedu.address.testutil;

import seedu.address.logic.commands.wedding.EditWeddingCommand.EditWeddingDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * A utility class to help with building EditWeddingDescriptor objects.
 */
public class EditWeddingDescriptorBuilder {

    private EditWeddingDescriptor descriptor;

    public EditWeddingDescriptorBuilder() {
        descriptor = new EditWeddingDescriptor();
    }

    public EditWeddingDescriptorBuilder(EditWeddingDescriptor descriptor) {
        this.descriptor = new EditWeddingDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditWeddingDescriptor} with fields containing {@code person}'s details
     */
    public EditWeddingDescriptorBuilder(Wedding wedding) {
        descriptor = new EditWeddingDescriptor();
        descriptor.setWeddingName(wedding.getWeddingName());
        descriptor.setPartner1(wedding.getPartner1());
        descriptor.setPartner2(wedding.getPartner2());
        descriptor.setAddress(wedding.getAddress());
        descriptor.setDate(wedding.getDate());
    }

    /**
     * Sets the {@code Name} of the {@code EditWeddingDescriptor} that we are building.
     */
    public EditWeddingDescriptorBuilder withName(String name) {
        descriptor.setWeddingName(new WeddingName(name));
        return this;
    }

    /**
     * Sets {@code partner1} of the {@code EditWeddingDescriptor} that we are building.
     */
    public EditWeddingDescriptorBuilder withPartner1(Person partner1) {
        descriptor.setPartner1(partner1);
        return this;
    }

    /**
     * Sets the {@code partner2} of the {@code EditWeddingDescriptor} that we are building.
     */
    public EditWeddingDescriptorBuilder withPartner2(Person partner2) {
        descriptor.setPartner2(partner2);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditWeddingDescriptor} that we are building.
     */
    public EditWeddingDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditWeddingDescriptor} that we are building.
     */
    public EditWeddingDescriptorBuilder withDate(String date) {
        descriptor.setDate(date);
        return this;
    }

    public EditWeddingDescriptor build() {
        return descriptor;
    }
}
