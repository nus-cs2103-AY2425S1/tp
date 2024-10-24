package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
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
    private final Gender gender;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Subject> subjects = new HashSet<>();
    private final Set<String> classes = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Gender gender, Phone phone, Email email, Address address, Set<Tag> tags,
                  Set<Subject> subjects, Set<String> classes) {
        requireAllNonNull(name, phone, gender, email, address, tags, subjects, classes);
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.subjects.addAll(subjects);
        this.classes.addAll(classes);
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

    public Gender getGender() {
        return this.gender;
    }

    public Set<Subject> getSubjects() {
        return Collections.unmodifiableSet(subjects);
    }

    public Set<String> getClasses() {
        return this.classes;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return getName().equals(otherPerson.getName())
            && getGender().equals(otherPerson.getGender())
            && getPhone().equals(otherPerson.getPhone())
            && getEmail().equals(otherPerson.getEmail())
            && getAddress().equals(otherPerson.getAddress())
            && getTags().equals(otherPerson.getTags())
            && getSubjects().equals(otherPerson.getSubjects())
            && getClasses().equals(otherPerson.getClasses());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, gender, phone, email, address, tags, subjects, classes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("name", getName())
            .add("gender", getGender())
            .add("phone", getPhone())
            .add("email", getEmail())
            .add("address", getAddress())
            .add("tags", getTags())
            .add("subject", getSubjects())
            .add("classes", getClasses())
            .toString();
    }

}
