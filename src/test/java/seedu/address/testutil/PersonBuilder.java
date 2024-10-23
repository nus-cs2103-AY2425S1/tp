package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.GradYear;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RoomNumber;
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
    public static final String DEFAULT_ROOM_NUMBER = "01-0123";
    public static final String DEFAULT_EMERGENCY_NAME = "Bob Bee";
    public static final String DEFAULT_EMERGENCY_PHONE = "98765432";
    public static final String DEFAULT_GRAD_YEAR = "2027";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private RoomNumber roomNumber;
    private EmergencyContact emergencyContact;
    private GradYear gradYear;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        roomNumber = new RoomNumber(DEFAULT_ROOM_NUMBER);
        address = new Address(DEFAULT_ADDRESS);
        emergencyContact = null;
        gradYear = null;

        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        roomNumber = personToCopy.getRoomNumber().orElse(null);
        address = personToCopy.getAddress();
        emergencyContact = personToCopy.getEmergencyContact().orElse(null);
        gradYear = personToCopy.getGradYear().orElse(null);
        tags = new HashSet<>(personToCopy.getTags());
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
     * Sets the {@code RoomNumber} of the {@code Person} that we are building.
     */
    public PersonBuilder withRoomNumber(String roomNumber) {
        this.roomNumber = new RoomNumber(roomNumber);
        return this;
    }

    /**
     * Sets the {@code RoomNumber} of the {@code Person} that we are building to null.
     */
    public PersonBuilder withNoRoomNumber() {
        this.roomNumber = null;
        return this;
    }

    /**
     * Sets the {@code EmergencyContact} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmergencyContact(String name, String phone) {
        this.emergencyContact = new EmergencyContact(new Name(name), new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code EmergencyContact} of the {@code Person} that we are building to null.
     */
    public PersonBuilder withNoEmergencyContact() {
        this.emergencyContact = null;
        return this;
    }

    /**
     * Sets the {@code GradYear} of the {@code Person} that we are building.
     */
    public PersonBuilder withGradYear(String gradYear) {
        this.gradYear = new GradYear(gradYear);
        return this;
    }

    /**
     * Sets the {@code GradYear} of the {@code Person} that we are building to null.
     */
    public PersonBuilder withNoGradYear() {
        this.gradYear = null;
        return this;
    }

    /**
     * Builds a person with all non-null Optional fields.
     */
    public Person buildForEditCommand() {
        if (this.emergencyContact == null) {
            this.emergencyContact = new EmergencyContact(new Name(DEFAULT_EMERGENCY_NAME),
                    new Phone(DEFAULT_EMERGENCY_PHONE));
        }
        if (this.gradYear == null) {
            this.gradYear = new GradYear(DEFAULT_GRAD_YEAR);
        }
        return new Person(name, phone, email, roomNumber, address, emergencyContact, gradYear, tags);
    }

    /**
     * Builds a person.
     */
    public Person build() {
        return new Person(name, phone, email, roomNumber, address, emergencyContact, gradYear, tags);
    }
}
