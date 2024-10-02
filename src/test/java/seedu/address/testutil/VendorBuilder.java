package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Vendor;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Vendor objects.
 */
public class VendorBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_COMPANY = "The Wedding People";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Company company;

    /**
     * Creates a {@code VendorBuilder} with the default details.
     */
    public VendorBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        company = new Company(DEFAULT_COMPANY);
    }

    /**
     * Initializes the VendorBuilder with the data of {@code guestToCopy}.
     */
    public VendorBuilder(Vendor vendorToCopy) {
        name = vendorToCopy.getName();
        phone = vendorToCopy.getPhone();
        email = vendorToCopy.getEmail();
        address = vendorToCopy.getAddress();
        tags = new HashSet<>(vendorToCopy.getTags());
        company = vendorToCopy.getCompany();
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
    public VendorBuilder withTags(String... tags) {
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
     * Sets the {@code Company} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withCompany(String company) {
        this.company = new Company(company);
        return this;
    }

    public Vendor build() {
        return new Vendor(name, phone, email, address, tags, company);
    }

}
