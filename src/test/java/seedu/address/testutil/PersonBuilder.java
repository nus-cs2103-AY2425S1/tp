package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.common.Name;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.skill.Skill;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ROLE = "Senior software engineer";

    private Name name;
    private Phone phone;
    private Email email;
    private Role role;
    private Set<Skill> skills;
    private String match;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        role = new Role(DEFAULT_ROLE);
        skills = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        role = personToCopy.getRole();
        skills = new HashSet<>(personToCopy.getSkills());
        match = personToCopy.getMatch().orElse(null);
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code skill} into a {@code Set<Skill>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withSkills(String... skills) {
        this.skills = SampleDataUtil.getSkillSet(skills);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Person} that we are building.
     */
    public PersonBuilder withRole(String role) {
        this.role = new Role(role);
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
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withMatch(String match) {
        this.match = match;
        return this;
    }

    /**
     * Builds a Person with the initialized attributes.
     *
     * @return Persons object.
     */
    public Person build() {
        if (this.match == null) {
            return new Person(name, phone, email, role, skills);
        } else {
            return new Person(name, phone, email, role, skills, match);
        }
    }

}
