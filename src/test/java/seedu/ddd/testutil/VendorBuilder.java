package seedu.ddd.testutil;

import static seedu.ddd.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_SERVICE_BOB;

import java.util.HashSet;
import java.util.Set;

import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.ContactId;
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
    public static final String DEFAULT_NAME = VALID_NAME_BOB;
    public static final String DEFAULT_PHONE = VALID_PHONE_BOB;
    public static final String DEFAULT_EMAIL = VALID_EMAIL_BOB;
    public static final String DEFAULT_ADDRESS = VALID_ADDRESS_BOB;
    public static final String DEFAULT_SERVICE = VALID_SERVICE_BOB;
    public static final String DEFAULT_ID = VALID_ID_BOB;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Service service;
    private Set<Tag> tags;
    private ContactId contactId;

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
        contactId = new ContactId(DEFAULT_ID);
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
        contactId = contactToCopy.getId();
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

    public Vendor build() {
        return new Vendor(name, phone, email, address, service, tags, contactId);
    }
}
