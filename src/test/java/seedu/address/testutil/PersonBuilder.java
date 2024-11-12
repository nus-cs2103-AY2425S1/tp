package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.DesiredRole;
import seedu.address.model.person.Email;
import seedu.address.model.person.Experience;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Skills;
import seedu.address.model.person.Status;
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
    public static final String DEFAULT_EXPERIENCE = "CTO at Google from 2001-2020";
    public static final String DEFAULT_SKILLS = "Java, Python";
    public static final String DEFAULT_STATUS = "Applied";
    public static final String DEFAULT_NOTE = "Knowledgeable and funny";
    public static final String DEFAULT_DESIREDROLE = "Software Engineer";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Experience experience;
    private Skills skills;
    private Status status;
    private Note note;
    private DesiredRole desiredRole;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        experience = new Experience(DEFAULT_EXPERIENCE);
        skills = new Skills(DEFAULT_SKILLS);
        status = new Status(DEFAULT_STATUS);
        note = new Note(DEFAULT_NOTE);
        desiredRole = new DesiredRole(DEFAULT_DESIREDROLE);
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
        experience = personToCopy.getExperience();
        skills = personToCopy.getSkills();
        status = personToCopy.getStatus();
        note = personToCopy.getNote();
        desiredRole = personToCopy.getDesiredRole();
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
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Experience} of the {@code Person} that we are building.
     */
    public PersonBuilder withExperience(String experience) {
        this.experience = new Experience(experience);
        return this;
    }

    /**
     * Sets the {@code Skills} of the {@code Person} that we are building.
     */
    public PersonBuilder withSkills(String skills) {
        this.skills = new Skills(skills);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Person} that we are building.
     */
    public PersonBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Person} that we are building.
     */
    public PersonBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Sets the {@code DesiredRole} of the {@code Person} that we are building.
     */
    public PersonBuilder withDesiredRole(String desiredRole) {
        this.desiredRole = new DesiredRole(desiredRole);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, desiredRole, skills, experience, status, note, tags);
    }
}
