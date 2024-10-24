package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Github github;

    // Data fields
    private final Address address;

    private final Telegram telegram;
    private final Set<Tag> tags = new HashSet<>();
    private Assignment assignment;
    private Set<Integer> weeksPresent;

    /**
     * Every field must be present and not null.
     */
    public Person(
            Name name,
            Phone phone,
            Email email,
            Address address,
            Telegram telegram,
            Set<Tag> tags,
            Github github) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.telegram = telegram;
        this.tags.addAll(tags);
        this.github = github;
        this.weeksPresent = new HashSet<>();

    }

    /**
     * Contains an additional assignment field.
     * Every field must be present and not null.
     */
    public Person(
            Name name,
            Phone phone,
            Email email,
            Address address,
            Telegram telegram,
            Set<Tag> tags,
            Github github,
            Assignment assignment) {
        requireAllNonNull(name, phone, email, address, telegram, tags, github);
        this.github = github;
        this.telegram = telegram;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.assignment = assignment;
        this.weeksPresent = new HashSet<>();
    }

    /**
     * Constructs a new Person object
     *
     * @param name the name of the person.
     * @param phone the phone number of the person.
     * @param address the address of the person.
     * @param email the email of the person.
     * @param telegram the telegram ID of the person.
     * @param github the github username of the person.
     * @param assignment the assignment of the person.
     * @param weeksPresent the weeks present.
     * @param tags the tags for that person.
     */
    public Person(Name name,
                  Phone phone,
                  Address address,
                  Email email,
                  Telegram telegram,
                  Github github,
                  Assignment assignment,
                  Set<Integer> weeksPresent,
                  Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, telegram, tags, github);
        this.github = github;
        this.telegram = telegram;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.assignment = assignment;
        this.weeksPresent = weeksPresent != null ? new HashSet<>(weeksPresent) : new HashSet<>();
    }



    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Telegram getTelegram() {
        return telegram;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Github getGithub() {
        return github;
    }

    public Set<Integer> getWeeksPresent() {
        return this.weeksPresent;
    }

    /**
     * Compares the {@code name} of this object against another Person object.
     * Comparison is done based on String::compareTo method.
     */
    public int compareName(Person anotherPerson) {
        requireNonNull(anotherPerson);
        return this.name.compareTo(anotherPerson.name);
    }

    /**
     * Compares the {@code github} of this object against another Person object.
     * Comparison is done based on String::compareTo method.
     */
    public int compareGithub(Person anotherPerson) {
        requireNonNull(anotherPerson);
        return this.github.compareTo(anotherPerson.github);
    }

    /**
     * Compares the {@code telegram} of this object against another Person object.
     * Comparison is done based on String::compareTo method.
     */
    public int compareTelegram(Person anotherPerson) {
        requireNonNull(anotherPerson);
        return this.telegram.compareTo(anotherPerson.telegram);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields. This defines a stronger
     * notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person otherPerson)) {
            return false;
        }

        return
                name.equals(otherPerson.name)
                        && phone.equals(otherPerson.phone)
                        && email.equals(otherPerson.email)
                        && address.equals(otherPerson.address)
                        && telegram.equals(otherPerson.telegram)
                        && tags.equals(otherPerson.tags)
                        && github.equals(otherPerson.github);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("telegram", telegram)
                .add("tags", tags)
                .add("github", github)
                .add("assignment", assignment)
                .add("weeks present", weeksPresent)
                .toString();
    }


}
