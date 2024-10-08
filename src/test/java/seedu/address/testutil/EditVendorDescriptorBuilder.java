package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditVendorDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.vendor.Address;
import seedu.address.model.vendor.Email;
import seedu.address.model.vendor.Name;
import seedu.address.model.vendor.Phone;
import seedu.address.model.vendor.Vendor;

/**
 * A utility class to help with building EditVendorDescriptor objects.
 */
public class EditVendorDescriptorBuilder {

    private EditVendorDescriptor descriptor;

    public EditVendorDescriptorBuilder() {
        descriptor = new EditVendorDescriptor();
    }

    public EditVendorDescriptorBuilder(EditVendorDescriptor descriptor) {
        this.descriptor = new EditVendorDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditVendorDescriptor} with fields containing
     * {@code vendor}'s details
     */
    public EditVendorDescriptorBuilder(Vendor vendor) {
        descriptor = new EditVendorDescriptor();
        descriptor.setName(vendor.getName());
        descriptor.setPhone(vendor.getPhone());
        descriptor.setEmail(vendor.getEmail());
        descriptor.setAddress(vendor.getAddress());
        descriptor.setTags(vendor.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditVendorDescriptor} that we are
     * building.
     */
    public EditVendorDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditVendorDescriptor} that we are
     * building.
     */
    public EditVendorDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditVendorDescriptor} that we are
     * building.
     */
    public EditVendorDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditVendorDescriptor} that we are
     * building.
     */
    public EditVendorDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the
     * {@code EditVendorDescriptor}
     * that we are building.
     */
    public EditVendorDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditVendorDescriptor build() {
        return descriptor;
    }
}
