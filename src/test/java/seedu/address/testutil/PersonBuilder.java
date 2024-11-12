package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Interest;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.University;
import seedu.address.model.person.WorkExp;
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
    public static final String DEFAULT_WORKEXP = "";
    public static final String DEFAULT_UNIVERSITY = "NUS";
    public static final String DEFAULT_MAJOR = "Computer Science";
    public static final String DEFAULT_INTEREST = "Swimming";
    public static final String DEFAULT_BIRTHDAY = "06-12-2003";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private WorkExp workExp;
    private Set<Tag> tags;
    private University university;
    private Major major;
    private Set<Interest> interests;
    private Birthday birthday;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        workExp = new WorkExp(DEFAULT_WORKEXP);
        tags = new HashSet<>();
        university = new University(DEFAULT_UNIVERSITY);
        major = new Major(DEFAULT_MAJOR);
        interests = new HashSet<>();
        birthday = new Birthday(DEFAULT_BIRTHDAY);

    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        workExp = personToCopy.getWorkExp();
        tags = new HashSet<>(personToCopy.getTags());
        university = personToCopy.getUniversity();
        major = personToCopy.getMajor();
        interests = new HashSet<>(personToCopy.getInterests());
        birthday = personToCopy.getBirthday();
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
     * Sets the {@code Birthday} of the {@code Person} that we are building.
     */
    public PersonBuilder withBirthday(String birthday) {
        this.birthday = new Birthday(birthday);
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
     * Sets the {@code WorkExp} of the {@code Person} that we are building.
     */
    public PersonBuilder withWorkExp(String workExp) {
        this.workExp = new WorkExp(workExp);
        return this;
    }

    /**
     * Sets the {@code WorkExp} of the {@code Person} being built to {@code null}.
     * This method is useful for testing scenarios where a {@code Person} has no work experience.
     */
    public PersonBuilder withNoWorkExp() {
        this.workExp = null;
        return this;
    }

    /**
     * Sets the {@code University} of the {@code Person} that we are building.
     */
    public PersonBuilder withUniversity(String university) {
        this.university = new University(university);
        return this;
    }

    /**
     * Sets the {@code Major} of the {@code Person} that we are building.
     */
    public PersonBuilder withMajor(String major) {
        this.major = new Major(major);
        return this;
    }

    /**
     * Sets the {@code Interests} of the {@code Person} that we are building.
     * Parses the {@code interests} into a {@code Set<Interest>}.
     */
    public PersonBuilder withInterests(String... interests) {
        this.interests = SampleDataUtil.getInterestSet(interests);
        return this;
    }
    /**
     * Builds and returns a {@code Person} object with the attributes that have been set for this {@code PersonBuilder}.
     */
    public Person build() {
        return new Person(name, phone, email, address, workExp, tags, university, major, interests, birthday);
    }

}
