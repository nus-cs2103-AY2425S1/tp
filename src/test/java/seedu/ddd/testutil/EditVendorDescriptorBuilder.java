package seedu.ddd.testutil;

import seedu.ddd.logic.commands.EditContactCommand.EditVendorDescriptor;
import seedu.ddd.model.contact.vendor.Service;
import seedu.ddd.model.contact.vendor.Vendor;

/**
 * A utility class to help with building EditVendorDescriptor objects.
 */
public class EditVendorDescriptorBuilder extends EditContactDescriptorBuilder {

    public EditVendorDescriptorBuilder() {
        descriptor = new EditVendorDescriptor();
    }

    public EditVendorDescriptorBuilder(EditVendorDescriptor descriptor) {
        this.descriptor = new EditVendorDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditVendorDescriptor} with fields containing {@code vendor}'s details
     */
    public EditVendorDescriptorBuilder(Vendor vendor) {
        EditVendorDescriptor editVendorDescriptor = new EditVendorDescriptor();
        editVendorDescriptor.setName(vendor.getName());
        editVendorDescriptor.setPhone(vendor.getPhone());
        editVendorDescriptor.setEmail(vendor.getEmail());
        editVendorDescriptor.setAddress(vendor.getAddress());
        editVendorDescriptor.setService(vendor.getService());
        editVendorDescriptor.setTags(vendor.getTags());
        editVendorDescriptor.setId(vendor.getId());
        descriptor = editVendorDescriptor;
    }

    @Override
    public EditVendorDescriptorBuilder withName(String name) {
        return (EditVendorDescriptorBuilder) super.withName(name);
    }

    @Override
    public EditVendorDescriptorBuilder withPhone(String phone) {
        return (EditVendorDescriptorBuilder) super.withPhone(phone);
    }

    @Override
    public EditVendorDescriptorBuilder withEmail(String email) {
        return (EditVendorDescriptorBuilder) super.withEmail(email);
    }

    @Override
    public EditVendorDescriptorBuilder withAddress(String address) {
        return (EditVendorDescriptorBuilder) super.withAddress(address);
    }

    /**
     * Sets the {@code Date} of the {@code EditVendorDescriptor} that we are building.
     */
    public EditVendorDescriptorBuilder withService(String service) {
        EditVendorDescriptor editVendorDescriptor = (EditVendorDescriptor) descriptor;
        editVendorDescriptor.setService(new Service(service));
        return this;
    }

    @Override
    public EditVendorDescriptorBuilder withTags(String... tags) {
        return (EditVendorDescriptorBuilder) super.withTags(tags);
    }

    @Override
    public EditVendorDescriptorBuilder withId(String id) {
        return (EditVendorDescriptorBuilder) super.withId(id);
    }

    @Override
    public EditVendorDescriptor build() {
        return (EditVendorDescriptor) super.build();
    }

}
