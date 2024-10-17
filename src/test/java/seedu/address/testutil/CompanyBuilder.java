package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.company.Company;
import seedu.address.model.person.company.Industry;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Company objects.
 */
public class CompanyBuilder {

    public static final String DEFAULT_NAME = "TechCorp";
    public static final String DEFAULT_INDUSTRY = "Technology";
    public static final String DEFAULT_PHONE = "91234567";
    public static final String DEFAULT_EMAIL = "contact@techcorp.com";
    public static final String DEFAULT_ADDRESS = "1 Infinite Loop, Cupertino, CA";

    private Name name;
    private Industry industry;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    /**
     * Creates a {@code CompanyBuilder} with the default details.
     */
    public CompanyBuilder() {
        name = new Name(DEFAULT_NAME);
        industry = new Industry(DEFAULT_INDUSTRY);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the CompanyBuilder with the data of {@code companyToCopy}.
     */
    public CompanyBuilder(Company companyToCopy) {
        name = companyToCopy.getName();
        industry = companyToCopy.getIndustry();
        phone = companyToCopy.getPhone();
        email = companyToCopy.getEmail();
        address = companyToCopy.getAddress();
        tags = new HashSet<>(companyToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Company} that we are building.
     */
    public CompanyBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Industry} of the {@code Company} that we are building.
     */
    public CompanyBuilder withIndustry(String industry) {
        this.industry = new Industry(industry);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Company} that we are building.
     */
    public CompanyBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
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

    public Company build() {
        return new Company(name, industry, phone, email, address, tags);
    }
}
