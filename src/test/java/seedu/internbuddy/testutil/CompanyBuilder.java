package seedu.internbuddy.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.company.Address;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.model.company.Email;
import seedu.internbuddy.model.company.Phone;
import seedu.internbuddy.model.company.Status;
import seedu.internbuddy.model.company.StatusType;
import seedu.internbuddy.model.name.Name;
import seedu.internbuddy.model.tag.Tag;
import seedu.internbuddy.model.util.SampleDataUtil;

/**
 * A utility class to help with building company objects.
 */
public class CompanyBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_STATUS = "INTERESTED";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Status status;
    private Set<Tag> tags;
    private List<Application> applications;

    /**
     * Creates a {@code companyBuilder} with the default details.
     */
    public CompanyBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        status = new Status(DEFAULT_STATUS);
        tags = new HashSet<>();
        status = new Status(DEFAULT_STATUS);
        applications = new ArrayList<>();
    }

    /**
     * Initializes the companyBuilder with the data of {@code companyToCopy}.
     */
    public CompanyBuilder(Company companyToCopy) {
        name = companyToCopy.getName();
        phone = companyToCopy.getPhone();
        email = companyToCopy.getEmail();
        address = companyToCopy.getAddress();
        tags = new HashSet<>(companyToCopy.getTags());
        status = companyToCopy.getStatus();
        applications = companyToCopy.getApplications();
    }

    /**
     * Sets the {@code Name} of the {@code company} that we are building.
     */
    public CompanyBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code company} that we are building.
     */
    public CompanyBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code applications} into a {@code List<Application>} and set it to the {@code company}
     * that we are building.
     */
    public CompanyBuilder withApplications(Application... applications) {
        this.applications = SampleDataUtil.getApplicationList(applications);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code company} that we are building.
     */
    public CompanyBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Address} to no address.
     */
    public CompanyBuilder withNoAddress() {
        this.address = Address.NO_ADDRESS;
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code company} that we are building.
     */
    public CompanyBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Phone} to no phone.
     */
    public CompanyBuilder withNoPhone() {
        this.phone = Phone.NO_PHONE;
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code company} that we are building.
     */
    public CompanyBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code company} that we are building.
     */
    public CompanyBuilder withStatus(String status) {

        switch (status) {

        case "INTERESTED":
            this.status = new Status(StatusType.INTERESTED);
            break;

        case "CLOSED":
            this.status = new Status(StatusType.CLOSED);
            break;

        case "APPLIED":
            this.status = new Status(StatusType.APPLIED);
            break;

        default:
            System.out.println("Invalid status type");
        }
        return this;

    }

    public CompanyBuilder withIsFavourite(Boolean isFavourite) {
        return this;
    }

    public Company build() {
        return new Company(name, phone, email, address, tags, status, applications);
    }
}
