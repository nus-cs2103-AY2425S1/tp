package seedu.internbuddy.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.internbuddy.model.company.Address;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.model.company.Email;
import seedu.internbuddy.model.company.Name;
import seedu.internbuddy.model.company.Phone;
import seedu.internbuddy.model.company.Status;
import seedu.internbuddy.model.tag.Tag;
import seedu.internbuddy.model.util.SampleDataUtilCompany;

/**
 * A utility class to help with building Company objects.
 */
public class CompanyBuilder {

    public static final String DEFAULT_NAME = "Google";
    public static final String DEFAULT_PHONE = "12345678";
    public static final String DEFAULT_EMAIL = "google@gmail.com";
    public static final String DEFAULT_ADDRESS = "70 Pasir Panjang Rd,Mapletree Business City";
    public static final String DEFAULT_STATUS = "INTERESTED";


    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Status status;

    /**
     * Creates a {@code CompanyBuilder} with the default details.
     */
    public CompanyBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        status = new Status(DEFAULT_STATUS);
    }

    /**
     * Initializes the CompanyBuilder with the data of {@code companyToCopy}.
     */
    public CompanyBuilder(Company companyToCopy) {
        name = companyToCopy.getName();
        phone = companyToCopy.getPhone();
        email = companyToCopy.getEmail();
        address = companyToCopy.getAddress();
        tags = new HashSet<>(companyToCopy.getTags());
        status = companyToCopy.getStatus();
    }

    /**
     * Sets the {@code Name} of the {@code Company} that we are building.
     */
    public CompanyBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Company} that we are building.
     */
    public CompanyBuilder withTags(String ... tags) {
        this.tags = SampleDataUtilCompany.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Company} that we are building.
     */
    public CompanyBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Company} that we are building.
     */
    public CompanyBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Company} that we are building.
     */
    public CompanyBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Company} that we are building.
     */
    public CompanyBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }



    public Company build() {
        return new Company(name, phone, email, address, tags, status);
    }

}
