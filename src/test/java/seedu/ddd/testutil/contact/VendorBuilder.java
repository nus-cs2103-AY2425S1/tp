package seedu.ddd.testutil.contact;

import static seedu.ddd.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.ddd.testutil.contact.TypicalContactFields.*;

import java.util.HashSet;
import java.util.Set;

import seedu.ddd.model.common.Name;
import seedu.ddd.model.common.Tag;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.model.contact.vendor.Service;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.util.SampleDataUtil;

/**
 * A utility class to help with building Vendor objects.
 */
public class VendorBuilder {

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Service service;
    private Set<Tag> tags;
    private ContactId contactId;

    /**
     * Initializes the VendorBuilder with the data of {@code contactToCopy}.
     */
    public VendorBuilder(Vendor vendorToCopy) {
        name = new Name(vendorToCopy.getName().fullName);
        phone = new Phone(vendorToCopy.getPhone().value);
        email = new Email(vendorToCopy.getEmail().value);
        address = new Address(vendorToCopy.getAddress().value);
        service = new Service(vendorToCopy.getService().value);
        tags = new HashSet<>(vendorToCopy.getTags());
        contactId = new ContactId(vendorToCopy.getId().contactId);
    }

    /**
     * Creates a {@code VendorBuilder} with the default details.
     */
    public VendorBuilder() {
        name = DEFAULT_VENDOR_NAME;
        phone = DEFAULT_VENDOR_PHONE;
        email = DEFAULT_VENDOR_EMAIL;
        address = DEFAULT_VENDOR_ADDRESS;
        service = DEFAULT_VENDOR_SERVICE;
        tags = new HashSet<>(DEFAULT_VENDOR_TAGS);
        contactId = DEFAULT_VENDOR_ID;
    }

    /**
     * Sets the {@code Name} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Vendor} that we are building.
     */
    public VendorBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Service} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withService(String service) {
        this.service = new Service(service);
        return this;
    }

    /**
     * Sets the {@code ID} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withId(int id) {
        this.contactId = new ContactId(id);
        return this;
    }

    /**
     * Sets the {@code ID} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withId(String id) {
        this.contactId = new ContactId(id);
        return this;
    }

    public Vendor build() {
        requireAllNonNull(name, phone, email, address, service, tags, contactId);
        return new Vendor(name, phone, email, address, service, tags, contactId);
    }
}
