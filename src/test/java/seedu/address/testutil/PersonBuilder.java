package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.Address;
import seedu.address.model.person.Archive;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355258";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ROLE = "client";

    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111, S120300";
    public static final String DEFAULT_ARCHIVE = "false";

    private Name name;
    private Phone phone;
    private Email email;
    private Role role;
    private Address address;
    private Archive archive;
    private Set<Tag> tags;
    private List<Delivery> deliveryList;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        role = new Role(DEFAULT_ROLE);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        archive = new Archive(DEFAULT_ARCHIVE);
        deliveryList = new ArrayList<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        role = personToCopy.getRole();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        archive = personToCopy.getArchive();
        deliveryList = new ArrayList<>(personToCopy.getUnmodifiableDeliveryList());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Adds a {@code Delivery} into the {@code DeliveryList} of the {@code Person} that we are building.
     */
    public PersonBuilder withDelivery(Delivery delivery) {
        this.deliveryList.add(delivery);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Archive} of the {@code Person} that we are building.
     */
    public PersonBuilder withArchive(String archive) {
        this.archive = new Archive(archive);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, role, address, tags, archive);
    }

}
