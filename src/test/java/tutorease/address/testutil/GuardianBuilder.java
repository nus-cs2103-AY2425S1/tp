package tutorease.address.testutil;

import java.util.HashSet;
import java.util.Set;

import tutorease.address.model.person.Address;
import tutorease.address.model.person.Email;
import tutorease.address.model.person.Guardian;
import tutorease.address.model.person.Name;
import tutorease.address.model.person.Person;
import tutorease.address.model.person.Phone;
import tutorease.address.model.person.Role;
import tutorease.address.model.tag.Tag;
import tutorease.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Guardian objects.
 */
public class GuardianBuilder {
    public static final String DEFAULT_NAME = "Bobby Macaroni";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "bobby@gmail.com";
    public static final String DEFAULT_ADDRESS = "158, Tampines Ave 6, #02-1";
    public static final String DEFAULT_ROLE = "Guardian";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Role role;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public GuardianBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        role = new Role(DEFAULT_ROLE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public GuardianBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        role = personToCopy.getRole();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public GuardianBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Person} that we are building.
     */
    public GuardianBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public GuardianBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public GuardianBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public GuardianBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public GuardianBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Guardian build() {
        return new Guardian(name, phone, email, address, role, tags);
    }
}
