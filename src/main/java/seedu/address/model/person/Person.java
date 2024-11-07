package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Person {
    private static int index;
    // Identity fields
    protected final int id;
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Hours hours;
    private final Set<Subject> subjects = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Hours hours, Set<Subject> subjects) {
        requireAllNonNull(name, phone, email, address);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.hours = hours;
        this.subjects.addAll(subjects);
        this.id = ++index;
    }

    /**
     * Alternate constructor for creating Person from addressbook.json
     */
    public Person(int id, Name name, Phone phone, Email email, Address address, Hours hours, Set<Subject> subjects) {
        requireAllNonNull(name, phone, email, address);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.hours = hours;
        this.subjects.addAll(subjects);
        this.id = id;
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
    };

    public int getId() {
        return id;
    }

    public static void initialiseIndex(int index) {
        Person.index = index;
    }

    public static int getNextIndex() {
        return index++;
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
                && otherPerson.getName().equals(getName()) && otherPerson.getPhone().equals(getPhone())
                && otherPerson.isTutee() == this.isTutee();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, hours, subjects);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("hours", hours)
                .add("subjects", subjects)
                .toString();
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
    public boolean hasSubject(Subject subject) {
        return subjects.stream()
                .anyMatch(s -> s.subject.equalsIgnoreCase(subject.getSubject()));
    }

    /**
     * Adds a subject to the person's list of subjects.
     *
     * @param subject
     */
    public void setSubject(Subject subject) {
        if (subject == null) {
            throw new NullPointerException();
        }
        subjects.add(subject);
    }

}
