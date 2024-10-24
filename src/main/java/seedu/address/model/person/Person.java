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
public abstract class Person {
    private static int index = 0;
    // Identity fields
    protected final int id;
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Hours hours;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Subject> subjects = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Hours hours, Set<Tag> tags,
                  Set<Subject> subjects) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.hours = hours;
        this.tags.addAll(tags);
        this.subjects.addAll(subjects);
        this.id = ++index;
    }

    /**
     * Alternate constructor for creating Person from addressbook.json
     */
    public Person(int id, Name name, Phone phone, Email email, Address address, Hours hours, Set<Tag> tags,
                  Set<Subject> subjects) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.hours = hours;
        this.tags.addAll(tags);
        this.subjects.addAll(subjects);
        this.id = id;
        index = id++;
    }

    public abstract boolean isTutor();

    public abstract boolean isTutee();

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

    public Hours getHours() {
        return hours;
    }

    public String getRole() {
        return "Person";
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public int getId() {
        return id;
    }

    public static void initialiseIndex(int index) {
        Person.index = index;
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
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && hours.equals(otherPerson.hours)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, hours, tags, subjects);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("hours", hours)
                .add("tags", tags)
                .add("subjects", subjects)
                .toString();
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public Set<Subject> getSubjects() {
        return Collections.unmodifiableSet(subjects);
    }

    /**
     * Checks if this person has a subject with the specified name.
     * The check is case-insensitive.
     *
     * @param subject
     * @return {@code true} if the person has a subject with the given name, {@code false} otherwise.
     */
    public boolean hasSubject(String subject) {
        return subjects.stream()
                .anyMatch(s -> s.subject.equalsIgnoreCase(subject));
    }

}
