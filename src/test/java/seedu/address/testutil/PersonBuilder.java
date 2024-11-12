package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Guest;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder<T extends PersonBuilder<T>> {
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    @SuppressWarnings("unchecked")
    // T can only be GuestBuilder or VendorBuilder
    // Both are subtypes of PersonBuilder
    // Hence, this narrowing type conversion is safe!
    protected T self() {
        return (T) this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public T withName(String name) {
        this.name = new Name(name);
        return self();
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public T withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return self();
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public T withAddress(String address) {
        this.address = new Address(address);
        return self();
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public T withPhone(String phone) {
        this.phone = new Phone(phone);
        return self();
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public T withEmail(String email) {
        this.email = new Email(email);
        return self();
    }

    /**
     * Builds and returns a {@code Person} object based on the current state of the builder.
     *
     * @return A {@code Person} object, which will either be a {@code Guest} or a {@code Vendor}.
     */
    public Person build() {
        // TODO: FIX THIS STUPID WORKAROUND (By removing dependencies on PersonBuilder)
        return new Guest(name, phone, email, address, tags);
    }
}
