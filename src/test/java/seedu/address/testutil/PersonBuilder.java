package seedu.address.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfLastVisit;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
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
    public static final String DEFAULT_DATEOFLASTVISIT = "01-01-2024";
    public static final String DEFAULT_EMERGENCY_CONTACT = "97978293";
    public static final String DEFAULT_REMARK = "";

    private Name name;
    private Phone phone;
    private Optional<Email> email;
    private Optional<Address> address;
    private Set<Tag> tags;
    private Optional<DateOfLastVisit> dateOfLastVisit;
    private Optional<EmergencyContact> emergencyContact;
    private Remark remark;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = Optional.of(new Email(DEFAULT_EMAIL));
        address = Optional.of(new Address(DEFAULT_ADDRESS));
        tags = new HashSet<>();
        dateOfLastVisit = Optional.of(new DateOfLastVisit(DEFAULT_DATEOFLASTVISIT));
        emergencyContact = Optional.of(new EmergencyContact(DEFAULT_EMERGENCY_CONTACT));
        remark = new Remark(DEFAULT_REMARK);
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
        dateOfLastVisit = personToCopy.getDateOfLastVisit();
        emergencyContact = personToCopy.getEmergencyContact();
        remark = personToCopy.getRemark();
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
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = Optional.of(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building to {@code Optional.empty}.
     */
    public PersonBuilder withAddress() {
        this.address = Optional.empty();
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
        this.email = Optional.of(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building to {@code Optional.empty}.
     */
    public PersonBuilder withEmail() {
        this.email = Optional.empty();
        return this;
    }

    /**
     * Sets the {@code DateOfLastVisit} of the {@code Person} that we are building.
     */
    public PersonBuilder withDateOfLastVisit(String dateOfLastVisit) {
        this.dateOfLastVisit = Optional.of(new DateOfLastVisit(dateOfLastVisit));
        return this;
    }

    /**
     * Sets the {@code DateOfLastVisit} of the {@code Person} that we are building.
     */
    public PersonBuilder withDateOfLastVisit() {
        this.dateOfLastVisit = Optional.empty();
        return this;
    }

    /**
     * Sets the {@code EmergencyContact} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmergencyContact(String emergencyContact) {
        this.emergencyContact = Optional.of(new EmergencyContact(emergencyContact));
        return this;
    }

    /**
     * Sets the {@code EmergencyContact} of the {@code Person} that we are building to {@code Optional.empty}.
     */
    public PersonBuilder withEmergencyContact() {
        this.emergencyContact = Optional.empty();
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, tags, dateOfLastVisit,
                emergencyContact, remark);
    }

}
