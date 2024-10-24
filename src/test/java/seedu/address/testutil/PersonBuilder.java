package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Github;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Telegram;
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
    public static final String DEFAULT_TELEGRAM = "@viswa";
    public static final String DEFAULT_GITHUB = "Amy";
    public static final String DEFAULT_ASSIGNMENT_NAME = "ex01";
    public static final Float DEFAULT_ASSIGNMENT_SCORE = 0f;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Telegram telegram;
    private Set<Tag> tags;
    private Github github;
    private Assignment assignment;
    private Set<Integer> attendance;



    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        telegram = new Telegram(DEFAULT_TELEGRAM);
        tags = new HashSet<>();
        github = new Github(DEFAULT_GITHUB);
        assignment = new Assignment(DEFAULT_ASSIGNMENT_NAME, DEFAULT_ASSIGNMENT_SCORE);
        this.attendance = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        telegram = personToCopy.getTelegram();
        tags = new HashSet<>(personToCopy.getTags());
        github = personToCopy.getGithub();
        assignment = personToCopy.getAssignment();
        attendance = personToCopy.getWeeksPresent();

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
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Telegram} of the {@code Person} that we are building.
     */
    public PersonBuilder withTelegram(String telegram) {
        this.telegram = new Telegram(telegram);
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
     * Sets the {@code GitHub} of the {@code Person} that we are building.
     */
    public PersonBuilder withGithub(String username) {
        this.github = new Github(username);
        return this;
    }

    /**
     * Sets the {@code Assignment} of the {@code Person} that we are building.
     */
    public PersonBuilder witAssignment(String assignment, Float score) {
        this.assignment = new Assignment(assignment, score);
        return this;
    }
    /**
     * Sets the attendance of the {@code Person} being built.
     */
    public PersonBuilder withAttendance(Integer... weeks) {
        this.attendance = new HashSet<>(Arrays.asList(weeks));
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, telegram, tags, github);
    }

    public Person buildWithAssignment() {
        return new Person(name, phone, email, address, telegram, tags, github, assignment);
    }

    public Person buildWithAttendance() {
        return new Person(name, phone, address, email, telegram, github, assignment, attendance, tags);
    }

}
