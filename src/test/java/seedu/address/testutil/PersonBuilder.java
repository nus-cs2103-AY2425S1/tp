package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 * @param <T> the type of the builder (either PersonBuilder or its subclass)
 */
public class PersonBuilder<T extends PersonBuilder<T>> {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    protected Name name;
    protected Phone phone;
    protected Email email;
    protected Address address;
    protected Set<Tag> tags;

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
     * Returns this builder instance cast to the correct type.
     * @return this builder
     */
    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }

    public Person build() {
        return new Person(name, phone, email, address, tags);
    }

}
