package seedu.address.testutil;

import static seedu.address.model.person.EmergencyContact.NO_NAME;
import static seedu.address.model.person.EmergencyContact.NO_NUMBER;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PriorityLevel;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final int DEFAULT_PRIORITY_LEVEL = 3;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private EmergencyContact emergencyContact;
    private Set<Tag> tags;
    private PriorityLevel priorityLevel;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        emergencyContact = new EmergencyContact(new Name(NO_NAME), new Phone(NO_NUMBER));
        tags = new HashSet<>();
        priorityLevel = new PriorityLevel(DEFAULT_PRIORITY_LEVEL);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        emergencyContact = personToCopy.getEmergencyContact();
        tags = new HashSet<>(personToCopy.getTags());
        priorityLevel = personToCopy.getPriorityLevel();
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
     * Sets the {@code EmergencyContact} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmergencyContact(String emergencyContactName, String emergencyContactNumber) {
        this.emergencyContact = new EmergencyContact(new Name(emergencyContactName), new Phone(emergencyContactNumber));
        return this;
    }

    /**
     * Sets the {@code PriorityLevel} of the {@code Person} that we are building.
     *
     * @param priorityLevel The priority level to be assigned to the person, represented as an integer.
     *                      Must be a valid priority level (typically within a predefined range, e.g., 1-3).
     * @return The current instance of {@code PersonBuilder} with the updated priority level.
     */
    public PersonBuilder withPriorityLevel(int priorityLevel) {
        this.priorityLevel = new PriorityLevel(priorityLevel);
        return this;
    }


    public Person build() {
        return new Person(name, phone, email, address, emergencyContact, tags, priorityLevel);
    }

}
