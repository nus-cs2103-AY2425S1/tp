package seedu.ddd.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Name;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.model.contact.vendor.Service;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.tag.Tag;
import seedu.ddd.model.util.SampleDataUtil;

/**
 * A utility class to help with building Vendor objects.
 */
public class VendorBuilder {
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_SERVICE = "Catering";
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Service service;
    private Set<Tag> tags;

    /**
     * Creates a {@code VendorBuilder} with the default details.
     */
    public VendorBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        service = new Service(DEFAULT_SERVICE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the VendorBuilder with the data of {@code contactToCopy}.
     */
    public VendorBuilder(Vendor contactToCopy) {
        name = contactToCopy.getName();
        phone = contactToCopy.getPhone();
        email = contactToCopy.getEmail();
        address = contactToCopy.getAddress();
        service = contactToCopy.getService();
        tags = new HashSet<>(contactToCopy.getTags());
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
    public Vendor build() {
        return new Vendor(name, phone, email, address, service, tags);
    }
}
