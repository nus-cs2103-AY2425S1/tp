package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.doctor.Doctor;
import seedu.address.model.doctor.Speciality;
import seedu.address.model.patient.DateOfBirth;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
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

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private String speciality;
    private String dateOfBirth;
    private String gender;
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

        if (personToCopy instanceof Doctor) {
            speciality = ((Doctor) personToCopy).getSpeciality().value;
            dateOfBirth = null;
            gender = null;
        } else if (personToCopy instanceof Patient) {
            speciality = null;
            dateOfBirth = ((Patient) personToCopy).getDateOfBirth().toString();
            gender = ((Patient) personToCopy).getGender().value;
        } else {
            speciality = null;
            dateOfBirth = null;
            gender = null;
        }
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
     * Sets the {@code Speciality} of the {@code Doctor} that we are building.
     */
    public PersonBuilder withSpeciality(String speciality) {
        this.speciality = speciality;
        return this;
    }

    /**
     * Sets the {@code DateOfBirth} of the {@code Patient} that we are building.
     */
    public PersonBuilder withDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Patient} that we are building.
     */
    public PersonBuilder withGender(String gender) {
        this.gender = gender;
        return this;
    }


    /**
     * Builds and returns a {@code Person} object based on the current state of this {@code PersonBuilder}.
     * @return a {@code Person}, {@code Doctor}, or {@code Patient} object based on the provided details
     */
    public Person build() {
        if (speciality != null) {
            return new Doctor(name, phone, email, address, new Speciality(speciality), tags);
        } else if (dateOfBirth != null && gender != null) {
            return new Patient(name, phone, email, address, new DateOfBirth(dateOfBirth), new Gender(gender), tags);
        } else {
            return new Person(name, phone, email, address, tags);
        }
    }

}
