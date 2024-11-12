package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Github;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Telegram;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_TELEGRAM = "@viswa";
    public static final String DEFAULT_GITHUB = "Amy";
    public static final String DEFAULT_ASSIGNMENT_NAME = "ex01";
    public static final Float DEFAULT_ASSIGNMENT_SCORE = 0f;
    public static final Assignment DEFAULT_ASSIGNMENT =
            new Assignment(DEFAULT_ASSIGNMENT_NAME, DEFAULT_ASSIGNMENT_SCORE);

    private Name name;
    private Email email;
    private Telegram telegram;
    private Set<Tag> tags;
    private Github github;
    private Map<String, Assignment> assignment;
    private Set<Integer> attendance;



    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        email = new Email(DEFAULT_EMAIL);
        telegram = new Telegram(DEFAULT_TELEGRAM);
        tags = new HashSet<>();
        github = new Github(DEFAULT_GITHUB);
        assignment = new HashMap<>();
        assignment.put(DEFAULT_ASSIGNMENT_NAME, DEFAULT_ASSIGNMENT);
        this.attendance = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        email = personToCopy.getEmail();
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
     * Sets the {@code Telegram} of the {@code Person} that we are building.
     */
    public PersonBuilder withTelegram(String telegram) {
        this.telegram = new Telegram(telegram);
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
        this.assignment = new HashMap<>();
        this.assignment.put(assignment, new Assignment(assignment, score));
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
        return new Person(name, email, telegram, tags, github);
    }

    public Person buildWithAssignment() {
        return new Person(name, email, telegram, tags, github, assignment);
    }

    public Person buildWithAttendance() {
        return new Person(name, email, telegram, github, assignment, attendance, tags);
    }

}
