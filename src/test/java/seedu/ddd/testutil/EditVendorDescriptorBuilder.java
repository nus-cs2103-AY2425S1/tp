package seedu.ddd.testutil;

import seedu.ddd.logic.commands.EditCommand.EditVendorDescriptor;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.contact.vendor.Service;

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
        descriptor = new EditVendorDescriptor();
        descriptor.setName(vendor.getName());
        descriptor.setPhone(vendor.getPhone());
        descriptor.setEmail(vendor.getEmail());
        descriptor.setAddress(vendor.getAddress());
        ((EditVendorDescriptor) descriptor).setService(vendor.getService());
        descriptor.setTags(vendor.getTags());
        descriptor.setId(vendor.getId());
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
        ((EditVendorDescriptor) descriptor).setService(new Service(service));
        return this;
    }

    @Override
    public EditVendorDescriptorBuilder withTags(String... tags) {
        return (EditVendorDescriptorBuilder) super.withTags(tags);
    }

    @Override
    public EditVendorDescriptor build() {
        return (EditVendorDescriptor) super.build();
    }

}
