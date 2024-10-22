package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.note.Note;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Sex;
import seedu.address.model.person.StarredStatus;
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
    public static final String DEFAULT_AGE = "24";
    public static final String DEFAULT_SEX = "Female";
    public static final String DEFAULT_STARRED_STATUS = "false";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Age age;
    private Sex sex;
    private Set<Appointment> appointments;
    private Set<Tag> tags;
    private Note note;
    private StarredStatus starredStatus;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        age = new Age(DEFAULT_AGE);
        sex = new Sex(DEFAULT_SEX);
        appointments = new HashSet<>();
        tags = new HashSet<>();
        note = new Note();
        starredStatus = new StarredStatus(DEFAULT_STARRED_STATUS);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        age = personToCopy.getAge();
        sex = personToCopy.getSex();
        appointments = new HashSet<>(personToCopy.getAppointment());
        tags = new HashSet<>(personToCopy.getTags());
        note = personToCopy.getNote();
        starredStatus = personToCopy.getStarredStatus();
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
     * Parses the {@code appointments} into a {@code Set<Appointment>}
     * and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withAppointments(String ... appointments) {
        this.appointments = SampleDataUtil.getAppointmentSet(appointments);
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
     * Sets the {@code Age} of the {@code Person} that we are building.
     */
    public PersonBuilder withAge(String age) {
        this.age = new Age(age);
        return this;
    }

    /**
     * Sets the {@code Sex} of the {@code Person} that we are building.
     */
    public PersonBuilder withSex(String sex) {
        this.sex = new Sex(sex);
        return this;
    }

    /**
     * Sets the {@code Sex} of the {@code Person} that we are building.
     */
    public PersonBuilder withNote(String appointment, String remark, String medication) {
        this.note = new Note();

        if (appointment != null) {
            this.note.addAppointment(appointment);
        }

        if (remark != null) {
            this.note.addRemark(remark);
        }

        if (medication != null) {
            this.note.addMedication(medication);
        }
        return this;
    }

    /**
     * Sets the {@code StarredStatus} of the {@code Person} that we are building.
     */
    public PersonBuilder withStarredStatus(String starredStatus) {
        this.starredStatus = new StarredStatus(starredStatus);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, age, sex, appointments, tags, note, starredStatus);
    }

}
